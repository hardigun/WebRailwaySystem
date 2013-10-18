package com.tsystems.webrailwaysystem.controllers;

import com.tsystems.webrailwaysystem.entities.PassengerEntity;
import com.tsystems.webrailwaysystem.entities.SheduleItemEntity;
import com.tsystems.webrailwaysystem.entities.StationEntity;
import com.tsystems.webrailwaysystem.exceptions.RailwaySystemException;
import com.tsystems.webrailwaysystem.filters.SheduleFilter;
import com.tsystems.webrailwaysystem.filters.StationsFilter;
import com.tsystems.webrailwaysystem.filters.TicketsFilter;
import com.tsystems.webrailwaysystem.services.*;
import com.tsystems.webrailwaysystem.utils.RouteFactoryMethod;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Date: 12.10.13
 */
@Controller
@RequestMapping("/shedule")
public class SheduleItemController {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    @Autowired
    private StationInfoService stationInfoService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private SheduleService sheduleService;

    @Autowired
    private ValidationService validationService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addSheduleItem(Model uiModel) {
        uiModel.addAttribute("routeList", this.routeService.getAllRoutes());
        uiModel.addAttribute("trainList", this.trainService.getAllTrains());
        return new ModelAndView("shedule/add", "sheduleItemEntity", new SheduleItemEntity());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addSheduleItem(@ModelAttribute @Valid SheduleItemEntity sheduleItem, BindingResult bindingResult,
                                       Model uiModel) {
        uiModel.addAttribute("routeList", this.routeService.getAllRoutes());
        uiModel.addAttribute("trainList", this.trainService.getAllTrains());

        if(bindingResult.hasErrors()) {
            return new ModelAndView("shedule/add", "sheduleItemEntity", sheduleItem);
        }

        try {
            this.sheduleService.addSheduleItem(sheduleItem);
            uiModel.addAttribute("resultMessage", "Success");
        } catch (RailwaySystemException exc) {
            LOGGER.debug("Error while adding SheduleItemEntity");
            uiModel.addAttribute("resultMessage", exc.getMessage());
        }
        return new ModelAndView("shedule/add", "sheduleItemEntity", new SheduleItemEntity());
    }

    @RequestMapping(value = "/search/by-station", method = RequestMethod.GET)
    public ModelAndView sheduleByStation(Model uiModel) {
        uiModel.addAttribute("stationInfoList", this.stationInfoService.getAllStationInfo());
        return new ModelAndView("shedule/search/by-station", "sheduleFilter", new SheduleFilter());
    }

    @RequestMapping(value = "/search/by-station", method = RequestMethod.POST)
    public ModelAndView sheduleByStation(@ModelAttribute SheduleFilter sheduleFilter, BindingResult bindingResult,
                                         Model uiModel) {
        uiModel.addAttribute("stationInfoList", this.stationInfoService.getAllStationInfo());
        uiModel.addAttribute("sheduleItemsList", this.sheduleService.searchByStation(sheduleFilter));
        return new ModelAndView("shedule/search/by-station", "sheduleFilter", sheduleFilter);
    }

    @RequestMapping(value = "/search/between-stations", method = RequestMethod.GET)
    public ModelAndView searchBetweenStations(Model uiModel) {
        uiModel.addAttribute("stationInfoList", this.stationInfoService.getAllStationInfo());
        return new ModelAndView("shedule/search/between-stations", "stationsFilter", new StationsFilter());
    }

    @RequestMapping(value = "/search/between-stations", method = RequestMethod.POST)
    public ModelAndView searchBetweenStations(@ModelAttribute StationsFilter stationsFilter, BindingResult bindingResult,
                                         Model uiModel) {
        uiModel.addAttribute("stationInfoList", this.stationInfoService.getAllStationInfo());
        uiModel.addAttribute("sheduleItemsList", this.sheduleService.searchBetweenStations(stationsFilter));
        return new ModelAndView("shedule/search/between-stations", "stationsFilter", stationsFilter);
    }

    @RequestMapping(value = "/show/trains-by-routes", method = RequestMethod.GET)
    public String showTrainsByRoutes(Model uiModel) {
        uiModel.addAttribute("sheduleItemsByRoutesMap", this.sheduleService.getAllSheduleItemsGroupByRoutes());
        return "shedule/show/trains-by-routes";
    }

}
