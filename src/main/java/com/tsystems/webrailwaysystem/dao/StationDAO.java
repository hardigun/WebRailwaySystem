package com.tsystems.webrailwaysystem.dao;

import com.tsystems.webrailwaysystem.entities.RouteEntity;
import com.tsystems.webrailwaysystem.entities.StationEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contract: DAO execute operation with database for StationEntity
 *
 * Date: 17.09.13
 */
@Repository("stationDAO")
public class StationDAO extends AbstractDAO {

    /**
     * Contract: get list of StationEntity that include routeEntity
     *
     * @param routeEntity station list for which need to get
     * @return list of station for routeEntity
     */
    public List<StationEntity> getAllByRoute(RouteEntity routeEntity) {
        String queryStr = "FROM StationEntity AS station WHERE station.route = :route";
        Query query = this.getSessionFactory().getCurrentSession().createQuery(queryStr);
        query.setParameter("route", routeEntity);
        return query.list();
    }

}
