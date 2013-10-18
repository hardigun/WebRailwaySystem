package com.tsystems.webrailwaysystem.controllers;

import com.tsystems.webrailwaysystem.entities.RouteEntity;
import com.tsystems.webrailwaysystem.entities.SheduleItemEntity;
import com.tsystems.webrailwaysystem.entities.StationEntity;
import com.tsystems.webrailwaysystem.entities.TicketEntity;
import com.tsystems.webrailwaysystem.exceptions.RailwaySystemException;
import com.tsystems.webrailwaysystem.services.SheduleService;
import com.tsystems.webrailwaysystem.services.StationService;
import com.tsystems.webrailwaysystem.services.TicketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Date: 16.10.13
 */
@Controller
@RequestMapping("/rest")
public class RESTController {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    @Autowired
    private SheduleService sheduleService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private StationService stationService;

    @RequestMapping(value = "/get/trains-by-routes", method = RequestMethod.GET)
    @ResponseBody
    public Map<RouteEntity, List<SheduleItemEntity>> getTrainsByRoutes() {
        return this.sheduleService.getAllSheduleItemsGroupByRoutes();
    }

    @RequestMapping(value = "/get/trains-by-routes/{routeId}", method = RequestMethod.GET)
    @ResponseBody
    public List<SheduleItemEntity> getTrainsByRoutes(@PathVariable int routeId) {
        return this.sheduleService.getSheduleItemsByRouteId(routeId);
    }

    @RequestMapping(value = "/ticket/confirm", method = RequestMethod.POST)
    @ResponseBody
    public List<TicketEntity> confirmTickets(HttpServletRequest request) {
        String[] ticketsIdArray = (String[]) request.getParameterMap().get("tickets[]");
        List<TicketEntity> ticketsList = new ArrayList<TicketEntity>();
        for(int i = 0; i < ticketsIdArray.length; i++) {
            try {
                ticketsList.add(this.ticketService.confirmSale(Integer.parseInt(ticketsIdArray[i])));
            } catch(Exception exc) {
                LOGGER.debug("Incorrect ticket id - " + ticketsIdArray[i]);
            }
        }
        return ticketsList;
    }

    @RequestMapping(value = "/get/route-stations/{routeId}", method = RequestMethod.GET)
    @ResponseBody
    public List<StationEntity> getRouteStations(@PathVariable int routeId) {
        return this.stationService.getStationsByRouteId(routeId);
    }

}
