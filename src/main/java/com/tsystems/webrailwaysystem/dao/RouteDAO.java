package com.tsystems.webrailwaysystem.dao;

import com.tsystems.webrailwaysystem.entities.RouteEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contract: DAO execute operations with database for RouteEntity
 *
 * Date: 16.09.13
 */
@Repository("routeDAO")
public class RouteDAO extends AbstractDAO {

    public List<RouteEntity> getAll() {
        String queryStr = "FROM RouteEntity ORDER BY routeNumber ASC";
        return this.getSessionFactory().getCurrentSession().createQuery(queryStr).list();
    }

    /**
     * Contract: unique list of routes id used in the shedule
     *
     * @return
     */
    public List<Integer> getAllUsedRoutesId() {
        String queryStr = "SELECT sheduleItem.route.id" +
                " FROM SheduleItemEntity AS sheduleItem" +
                " GROUP BY sheduleItem.route.id";
        return this.getSessionFactory().getCurrentSession().createQuery(queryStr).list();
    }

}
