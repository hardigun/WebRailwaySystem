package com.tsystems.webrailwaysystem.services;

import com.tsystems.webrailwaysystem.dao.StationDAO;
import com.tsystems.webrailwaysystem.entities.RouteEntity;
import com.tsystems.webrailwaysystem.entities.StationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Contract: execute operations with StationEntity
 *
 * Date: 30.09.13
 */
@Service("stationService")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW)
public class StationService {

    @Autowired
    private StationDAO stationDAO;

    /**
     * Contract: return stations that include route
     *
     * @param routeId
     * @return
     */
    @Transactional(readOnly = true)
    public List<StationEntity> getStationsByRouteId(int routeId) {
        return stationDAO.getAllByRouteId(routeId);
    }

}
