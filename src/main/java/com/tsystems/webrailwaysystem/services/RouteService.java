package com.tsystems.webrailwaysystem.services;

import com.tsystems.webrailwaysystem.dao.RouteDAO;
import com.tsystems.webrailwaysystem.dao.StationDAO;
import com.tsystems.webrailwaysystem.entities.RouteEntity;
import com.tsystems.webrailwaysystem.entities.StationEntity;
import com.tsystems.webrailwaysystem.exceptions.RailwaySystemException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Contract: execute operations with RouteEntity
 *
 * Date: 21.09.13
 */
@Service("routeService")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW)
public class RouteService {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    @Autowired
    private RouteDAO routeDAO;

    @Autowired
    private StationDAO stationDAO;

    /**
     * Contract: add new route to database. Add stations in the route to database also.
     *
     * @param route that need to add
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void addRoute(RouteEntity route) throws RailwaySystemException {
        try {
            this.routeDAO.save(route);
            for(StationEntity station : route.getStationsList()) {
                station.setRoute(route);
                this.stationDAO.save(station);
            }
        } catch(Exception exc) {
            exc.printStackTrace();
            LOGGER.warn(exc);
            throw new RailwaySystemException();
        }
    }

    @Transactional(readOnly = true)
    public RouteEntity getRoute(int id) {
        return (RouteEntity) this.routeDAO.getById(RouteEntity.class, id);
    }

    /**
     * Contract: return all routes from database
     *
     * @return list of RouteEntity
     */
    @Transactional(readOnly = true)
    public List<RouteEntity> getAllRoutes() {
        return this.routeDAO.getAll();
    }

    /**
     * Contract: compare all routes in the database and routes that include SheduleItems
     *
     * @return routes that exist in the database but not include in any SheduleItems
     */
    @Transactional(readOnly = true)
    public List<RouteEntity> getUnusedRoutes() {
        List<RouteEntity> returnRoutesList = new ArrayList<RouteEntity>();
        List<Integer> usedRoutesIdList = this.routeDAO.getAllUsedRoutesId();
        List<RouteEntity> allRoutes = this.routeDAO.getAll();
        for(RouteEntity route : allRoutes) {
            if(!usedRoutesIdList.contains(route.getId())) {
                returnRoutesList.add(route);
            }
        }
        return returnRoutesList;
    }

}
