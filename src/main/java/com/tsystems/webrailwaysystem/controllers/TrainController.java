package com.tsystems.webrailwaysystem.controllers;

import com.tsystems.webrailwaysystem.entities.StationInfoEntity;
import com.tsystems.webrailwaysystem.entities.TrainEntity;
import com.tsystems.webrailwaysystem.exceptions.RailwaySystemException;
import com.tsystems.webrailwaysystem.services.TrainService;
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

/**
 * Date: 11.10.13
 */

@Controller
@RequestMapping("/train")
public class TrainController {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    @Autowired
    private TrainService trainService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addTrain() {
        return new ModelAndView("train/add", "trainEntity", new TrainEntity());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addTrain(@ModelAttribute @Valid TrainEntity train, BindingResult bindingResult, Model uiModel) {
        if(bindingResult.hasErrors()) {
            LOGGER.debug("Errors in the adding TrainEntity " + train);
            return new ModelAndView("train/add", "trainEntity", train);
        }

        try {
            this.trainService.addTrain(train);
            uiModel.addAttribute("resultMessage", "Success");
        } catch (RailwaySystemException exc) {
            LOGGER.debug("Error while adding TrainEntity");
            uiModel.addAttribute("resultMessage", exc.getMessage());
        }

        return new ModelAndView("train/add", "trainEntity", new TrainEntity());
    }

    @RequestMapping(value = "/list-of-unused", method = RequestMethod.GET)
    public String listUnusedTrains(Model uiModel) {
        uiModel.addAttribute("unusedTrainsList", this.trainService.getUnusedTrains());
        return "train/list-of-unused";
    }

}
