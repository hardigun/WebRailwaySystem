package com.tsystems.webrailwaysystem.services;

import com.tsystems.webrailwaysystem.dao.PassengerDAO;
import com.tsystems.webrailwaysystem.dao.TicketDAO;
import com.tsystems.webrailwaysystem.entities.PassengerEntity;
import com.tsystems.webrailwaysystem.entities.SheduleItemEntity;
import com.tsystems.webrailwaysystem.entities.TicketEntity;
import com.tsystems.webrailwaysystem.exceptions.ExpiredTimeToBuyException;
import com.tsystems.webrailwaysystem.exceptions.NoAvailableSeatsException;
import com.tsystems.webrailwaysystem.exceptions.PassengerAlreadyRegisterException;
import com.tsystems.webrailwaysystem.exceptions.RailwaySystemException;
import com.tsystems.webrailwaysystem.filters.TicketsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Contract: service make operations with TicketEntity
 *
 * Date: 24.09.13
 */
@Service("ticketService")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW)
public class TicketService {

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private PassengerDAO passengerDAO;

    @Autowired
    private SheduleService sheduleService;

    /**
     * Contract: if filter include ticket than call ticketDAO.getById
     *           if filter include passenger than call passengerDAO.getAllByCredentials
     *           if filter include SheduleItem than call his.ticketDAO.getByPassengerAndSheduleItem
     *
     * @param filter include ticket, passenger and SheduleItems fields that used for search
     * @return list of TicketEntity that fill filter params
     */
    public List<TicketEntity> searchByParams(TicketsFilter filter) {
        List<TicketEntity> ticketsList = new ArrayList<TicketEntity>();
        if(filter.getTicket() != null) {
            TicketEntity ticketEntity = (TicketEntity) this.ticketDAO.getById(TicketEntity.class, filter.getTicket().getId());
            ticketsList.add(ticketEntity);
        } else {
            List<PassengerEntity> passengersList = new ArrayList<PassengerEntity>();
            if(filter.getPassenger() != null) {
                passengersList = this.passengerDAO.getAllByCredentials(filter.getPassenger());
                for(PassengerEntity passenger : passengersList) {
                    ticketsList.addAll(passenger.getTicketList());
                }
            }
            if(filter.getSheduleItem() != null) {
                ticketsList = this.ticketDAO.getByPassengerAndSheduleItem(passengersList, filter.getSheduleItem());
            }
        }
        return ticketsList;
    }

    /**
     * Contact: check conditions that in the train exist free seats and exist time before arrival
     * if all ok than create passenger and ticket
     *
     * @param passenger name, surname and birthday
     * @param inSheduleItem SheduleItem that passenger want to buy ticket for
     * @return saved in the database ticket or throws exception
     * @throws NoAvailableSeatsException
     * @throws ExpiredTimeToBuyException
     * @throws PassengerAlreadyRegisterException
     */
    public TicketEntity buyExecute(PassengerEntity passenger, SheduleItemEntity inSheduleItem)
            throws NoAvailableSeatsException, ExpiredTimeToBuyException, PassengerAlreadyRegisterException {

        /*check that exist free seats and 10 minutes before departure*/
        SheduleItemEntity sheduleItem = this.sheduleService.checkTrainSeatsAndDate(inSheduleItem);

        List<PassengerEntity> passengersList = this.passengerDAO.getAllByCredentials(passenger);
        /*check that passengersList it's list of passengers from previous lines*/
        if(passengersList.size() > 0) {
            List<TicketEntity> ticketsList = this.ticketDAO.getByPassengerAndSheduleItem(passengersList, sheduleItem);
            if(ticketsList.size() > 0) {
                throw new PassengerAlreadyRegisterException();
            }
        }

        TicketEntity ticket = new TicketEntity();
        ticket.setPassenger(passenger);
        ticket.setSheduleItem(sheduleItem);

        this.passengerDAO.save(passenger);
        this.ticketDAO.save(ticket);

        return ticket;
    }

    /**
     * Contract: set confirm status on ticket
     *
     * @param inTicket ticket for set confirm
     * @throws RailwaySystemException if in the database no tickets as inTicket
     */
    public void confirmSale(TicketEntity inTicket) throws RailwaySystemException {
        TicketEntity ticket = (TicketEntity) this.ticketDAO.getById(TicketEntity.class, inTicket.getId());
        if(ticket == null) {
            throw new RailwaySystemException();
        }
        ticket.setSaleConfirmed(true);
        this.ticketDAO.save(ticket);
    }

}
