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
public class StationDAO extends AbstractDAO<StationEntity> {

    /**
     * Contract: get list of StationEntity that include routeEntity
     *
     * @param routeId station list for which need to get
     * @return list of station for routeEntity
     */
    public List<StationEntity> getAllByRouteId(int routeId) {
        String queryStr = "FROM StationEntity AS station WHERE station.route.id = :routeId";
        Query query = this.getSessionFactory().getCurrentSession().createQuery(queryStr);
        query.setParameter("routeId", routeId);
        return query.list();
    }

}
