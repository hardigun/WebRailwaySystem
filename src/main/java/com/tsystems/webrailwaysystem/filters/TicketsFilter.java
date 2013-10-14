package com.tsystems.webrailwaysystem.filters;

import com.tsystems.webrailwaysystem.entities.PassengerEntity;
import com.tsystems.webrailwaysystem.entities.SheduleItemEntity;
import com.tsystems.webrailwaysystem.entities.TicketEntity;

/**
 * Contract: used for send from client to server tickets search params
 *
 * Date: 26.09.13
 */
public class TicketsFilter {

    private PassengerEntity passenger;
    private TicketEntity ticket;
    private SheduleItemEntity sheduleItem;

    public PassengerEntity getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerEntity passenger) {
        this.passenger = passenger;
    }

    public TicketEntity getTicket() {
        return ticket;
    }

    public void setTicket(TicketEntity ticket) {
        this.ticket = ticket;
    }

    public SheduleItemEntity getSheduleItem() {
        return sheduleItem;
    }

    public void setSheduleItem(SheduleItemEntity sheduleItem) {
        this.sheduleItem = sheduleItem;
    }
}
