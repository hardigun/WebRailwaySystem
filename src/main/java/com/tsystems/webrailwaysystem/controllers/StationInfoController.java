package com.tsystems.webrailwaysystem.controllers;

import com.tsystems.webrailwaysystem.entities.StationInfoEntity;
import com.tsystems.webrailwaysystem.exceptions.RailwaySystemException;
import com.tsystems.webrailwaysystem.services.StationInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * Date: 10.10.13
 */

@Controller
@RequestMapping("/station-info")
public class StationInfoController {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    @Autowired
    private StationInfoService stationInfoService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addStationInfo() {
        return new ModelAndView("station-info/add", "stationInfoEntity", new StationInfoEntity());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addStationInfo(@ModelAttribute @Valid StationInfoEntity stationInfo, BindingResult bindingResult,
                                       Model uiModel) {
        if(bindingResult.hasErrors()) {
            LOGGER.debug("Errors in the adding StationInfoEntity " + stationInfo);
            return new ModelAndView("station-info/add", "stationInfoEntity", stationInfo);
        }

        try {
            this.stationInfoService.addStationInfo(stationInfo);
            uiModel.addAttribute("resultMessage", "Successfully added");
        } catch(RailwaySystemException exc) {
            LOGGER.debug("Error while adding StationInfoEntity");
            uiModel.addAttribute("resultMessage", exc.getMessage());
        }

        return new ModelAndView("station-info/add", "stationInfoEntity", new StationInfoEntity());
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listStationInfo(Model uiModel) {
        uiModel.addAttribute("stationInfoList", this.stationInfoService.getAllStationInfo());
        return "station-info/list";
    }

}
