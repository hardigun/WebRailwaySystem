package com.tsystems.webrailwaysystem.dao;

import com.tsystems.webrailwaysystem.entities.TrainEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Contract: DAO execute operations with database for TrainEntity
 *
 * Date: 10.09.13
 */
@Repository("trainDAO")
public class TrainDAO extends AbstractDAO {

    public List<TrainEntity> getAll() {
        String queryStr = "FROM TrainEntity ORDER BY trainNumber ASC";
        return this.getSessionFactory().getCurrentSession().createQuery(queryStr).list();
    }

    /**
     * Contract: unique list of trains id used in the shedule
     *
     * @return
     */
    public List<Integer> getAllUsedTrainsId() {
        String queryStr = "SELECT sheduleItem.train.id" +
                " FROM SheduleItemEntity AS sheduleItem" +
                " GROUP BY sheduleItem.train.id";
        return this.getSessionFactory().getCurrentSession().createQuery(queryStr).list();
    }

}
