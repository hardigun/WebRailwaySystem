package com.tsystems.webrailwaysystem.controllers;

import com.tsystems.webrailwaysystem.entities.Message;
import com.tsystems.webrailwaysystem.entities.StationInfoEntity;
import com.tsystems.webrailwaysystem.enums.EMessageType;
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

    public String view = "";

    public Model uiModel = null;

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
            return new ModelAndView("station-info/add", "stationInfoEntity", stationInfo);
        }

        /* if exception happens it helps save object state for user view */
        this.view = "station-info/add";
        this.uiModel = uiModel;
        this.uiModel.addAttribute("stationInfoEntity", stationInfo);

        this.stationInfoService.addStationInfo(stationInfo);
        uiModel.addAttribute("message", new Message("Successfully added", EMessageType.SUCCESS));
        return new ModelAndView("station-info/add", "stationInfoEntity", new StationInfoEntity());
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listStationInfo(Model uiModel) {
        uiModel.addAttribute("stationInfoList", this.stationInfoService.getAllStationInfo());
        return "station-info/list";
    }

}
