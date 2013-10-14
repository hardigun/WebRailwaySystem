package com.tsystems.webrailwaysystem.converters;

import com.tsystems.webrailwaysystem.entities.RouteEntity;
import com.tsystems.webrailwaysystem.services.RouteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;



/**
 * Date: 13.10.13
 */
public class StringIdToRouteConverter implements Converter<String, RouteEntity> {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    @Autowired
    private RouteService routeService;

    @Override
    public RouteEntity convert(String id) {
        RouteEntity route = new RouteEntity();
        try {
            route = this.routeService.getRoute(Integer.parseInt(id));
        } catch(Exception exc) {
            exc.printStackTrace();
            LOGGER.warn(exc);
        }
        return route;
    }

}
