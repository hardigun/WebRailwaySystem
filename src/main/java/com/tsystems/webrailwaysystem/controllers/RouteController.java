package com.tsystems.webrailwaysystem.controllers;

import com.tsystems.webrailwaysystem.entities.*;
import com.tsystems.webrailwaysystem.enums.EMessageType;
import com.tsystems.webrailwaysystem.exceptions.RailwaySystemException;
import com.tsystems.webrailwaysystem.services.RouteService;
import com.tsystems.webrailwaysystem.services.StationInfoService;
import com.tsystems.webrailwaysystem.services.ValidationService;
import com.tsystems.webrailwaysystem.utils.RouteFactoryMethod;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.util.*;

/**
 * Date: 11.10.13
 */
@Controller
@RequestMapping("/route")
public class RouteController {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    public String view = "";

    public Model uiModel = null;

    @Autowired
    private RouteService routeService;

    @Autowired
    private StationInfoService stationInfoService;

    @Autowired
    private ValidationService validationService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addRoute(Model uiModel) {
        List<StationInfoEntity> stationInfoList = this.stationInfoService.getAllStationInfo();
        uiModel.addAttribute("stationInfoList", stationInfoList);
        return new ModelAndView("route/add", "routeEntity", new RouteEntity());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addRoute(@ModelAttribute RouteEntity route, HttpServletRequest request, Model uiModel)
            throws Exception {

        List<StationInfoEntity> stationInfoList = this.stationInfoService.getAllStationInfo();
        uiModel.addAttribute("stationInfoList", stationInfoList);

        /* add stations to route from request params */
        String[] stationsInfoArray = (String[]) request.getParameterMap().get("stationsinfo[]");
        String[] timeOffsetArray = (String[]) request.getParameterMap().get("timeoffsets[]");
        RouteEntity routeFromFactory = RouteFactoryMethod.getRouteWithStationsFromIdArrays(
                route, stationsInfoArray, timeOffsetArray, this.stationInfoService
        );

        /* validate route and stations */
        String errorMessage = this.validationService.validateRoute(routeFromFactory, uiModel);

        Map<StationEntity, String> stationsErrorsMap = new HashMap<StationEntity, String>();
        for(StationEntity station : routeFromFactory.getStationsList()) {
            String stationErrorMessage = this.validationService.validateStation(station);
            if(stationErrorMessage.length() > 0) {
                stationsErrorsMap.put(station, stationErrorMessage);
            }
        }
        uiModel.addAttribute("stationsErrors", stationsErrorsMap);;

        if(errorMessage.length() > 0 || stationsErrorsMap.size() > 0) {
            return new ModelAndView("route/add", "routeEntity", routeFromFactory);
        }

        /* if exception happens it helps save object state for user view */
        this.view = "route/add";
        this.uiModel = uiModel;
        this.uiModel.addAttribute("routeEntity", routeFromFactory);

        this.routeService.addRoute(routeFromFactory);
        uiModel.addAttribute("message", new Message("Successfully added", EMessageType.SUCCESS));
        return new ModelAndView("route/add", "routeEntity", new RouteEntity());
    }

    @RequestMapping(value = "/list-of-unused", method = RequestMethod.GET)
    public String listUnusedRoutes(Model uiModel) {
        uiModel.addAttribute("unusedRoutesList", this.routeService.getUnusedRoutes());
        return "route/list-of-unused";
    }

}
