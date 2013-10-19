package com.tsystems.webrailwaysystem.dao;

import com.tsystems.webrailwaysystem.entities.PassengerEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contract: DAO execute operations with database for PassengerEntity
 *
 * Date: 24.09.13
 */
@Repository("passengerDAO")
public class PassengerDAO extends AbstractDAO<PassengerEntity> {

    /**
     * Contract: get all passengers with same name, surname and birthday;
     * if all three fields exist in the PassengerEntity then condition will be strict
     * otherwise return all passengers satisfy to one of the fields
     *
     * @param passenger with fields for search
     * @return satisfy list of PassengerEntity
     */
    public List<PassengerEntity> getAllByCredentials(PassengerEntity passenger) {
        StringBuffer queryBuf = new StringBuffer("FROM PassengerEntity");
        StringBuffer queryWhereCondBuf = new StringBuffer();

        if(passenger.getName() != null && !passenger.getName().trim().isEmpty()) {
            queryWhereCondBuf.append(" WHERE name = :passenger_name");
        }

        if(passenger.getSurname() != null && !passenger.getSurname().trim().isEmpty()) {
            if(queryWhereCondBuf.length() == 0) {
                queryWhereCondBuf.append(" WHERE surname = :passenger_surname");
            } else {
                queryWhereCondBuf.append(" AND surname = :passenger_surname");
            }
        }

        if(passenger.getBirthday() != null) {
            if(queryWhereCondBuf.length() == 0) {
                queryWhereCondBuf.append(" WHERE birthday = :passenger_birthday");
            } else {
                queryWhereCondBuf.append(" AND birthday = :passenger_birthday");
            }
        }
        queryBuf.append(queryWhereCondBuf);
        queryBuf.append(" ORDER BY surname ASC, name ASC");

        Query query = this.getSessionFactory().getCurrentSession().createQuery(queryBuf.toString());

        if(passenger.getName() != null && !passenger.getName().trim().isEmpty()) {
            query.setParameter("passenger_name", passenger.getName());
        }
        if(passenger.getSurname() != null && !passenger.getSurname().trim().isEmpty()) {
            query.setParameter("passenger_surname", passenger.getSurname());
        }
        if(passenger.getBirthday() != null) {
            query.setParameter("passenger_birthday", passenger.getBirthday());
        }

        return query.list();
    }

}
