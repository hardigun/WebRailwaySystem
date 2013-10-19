package com.tsystems.webrailwaysystem.dao;

import com.tsystems.webrailwaysystem.entities.PassengerEntity;
import com.tsystems.webrailwaysystem.entities.SheduleItemEntity;
import com.tsystems.webrailwaysystem.entities.TicketEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contract: DAO execute operation with database for TicketEntity
 *
 * Date: 24.09.13
 */
@Repository("ticketDAO")
public class TicketDAO extends AbstractDAO<TicketEntity> {

    /**
     * Contract: get all tickets for SheduleItem
     *
     * @param passengersList if exist then additional condition is some passengers
     * @param sheduleItem for which tickets info need
     * @return ticket list that associated with SheduleItem
     */
    public List<TicketEntity> getByPassengerAndSheduleItem(List<PassengerEntity> passengersList,
                                                           SheduleItemEntity sheduleItem) {
        String queryStr = "FROM TicketEntity " +
                " WHERE sheduleItem = :shedule_item";
        StringBuffer queryBuf = new StringBuffer(queryStr);
        for(int i = 0; i < passengersList.size(); i++) {
            if(i == 0) {
                queryBuf.append(" AND (passenger = :passenger_").append(i);
            } else {
                queryBuf.append(" OR passenger = :passenger_").append(i);
            }
            if(i == (passengersList.size() - 1)) {
                queryBuf.append(")");
            }
        }
        Query query = this.getSessionFactory().getCurrentSession().createQuery(queryBuf.toString());
        query.setParameter("shedule_item", sheduleItem);
        for(int i = 0; i < passengersList.size(); i++) {
            query.setParameter("passenger_" + i, passengersList.get(i));
        }
        return query.list();
    }

}
