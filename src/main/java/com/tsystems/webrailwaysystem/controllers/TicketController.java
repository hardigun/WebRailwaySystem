package com.tsystems.webrailwaysystem.controllers;

import com.tsystems.webrailwaysystem.entities.PassengerEntity;
import com.tsystems.webrailwaysystem.entities.SheduleItemEntity;
import com.tsystems.webrailwaysystem.entities.TicketEntity;
import com.tsystems.webrailwaysystem.exceptions.RailwaySystemException;
import com.tsystems.webrailwaysystem.services.SheduleService;
import com.tsystems.webrailwaysystem.services.TicketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Date: 13.10.13
 */
@Controller
@RequestMapping("/ticket")
public class TicketController {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    @Autowired
    private SheduleService sheduleService;

    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = "/buy/{id}", method = RequestMethod.GET)
    public ModelAndView buyTicket(@PathVariable("id") int sheduleItemId, Model uiModel) {
        SheduleItemEntity sheduleItem = new SheduleItemEntity();
        sheduleItem.setId(sheduleItemId);
        try {
            sheduleItem = this.sheduleService.checkTrainSeatsAndDate(sheduleItem);
        } catch(RailwaySystemException exc) {
            LOGGER.debug("Errors while buying ticket: " + exc.getMessage());
            uiModel.addAttribute("resultMessage", exc.getMessage());
        }
        uiModel.addAttribute("sheduleItem", sheduleItem);
        return new ModelAndView("ticket/buy", "passengerEntity", new PassengerEntity());
    }

    @RequestMapping(value = "/buy/{id}", method = RequestMethod.POST)
    public ModelAndView buyTicket(@PathVariable("id") int sheduleItemId, @ModelAttribute @Valid PassengerEntity passenger,
                                  BindingResult bindingResult, Model uiModel) {
        SheduleItemEntity sheduleItem = new SheduleItemEntity();
        sheduleItem.setId(sheduleItemId);
        try {
            sheduleItem = this.sheduleService.checkTrainSeatsAndDate(sheduleItem);
        } catch(RailwaySystemException exc) {
            LOGGER.debug("Errors while buying ticket: " + exc.getMessage());
            uiModel.addAttribute("resultMessage", exc.getMessage());
        }
        uiModel.addAttribute("sheduleItem", sheduleItem);
        if(uiModel.containsAttribute("resultMessage")) {
            return new ModelAndView("ticket/buy", "passengerEntity", new PassengerEntity());
        }

        if(bindingResult.hasErrors()) {
            LOGGER.debug("Errors in the adding PassengerEntity");
            return new ModelAndView("ticket/buy", "passengerEntity", passenger);
        }

        try {
            TicketEntity ticket = this.ticketService.buyExecute(passenger, sheduleItem);
            uiModel.addAttribute("resultMessage", "Successfully ticket buy! " + ticket.toString());
        } catch(RailwaySystemException exc) {
            LOGGER.debug("Errors while buying ticket: " + exc.getMessage());
            uiModel.addAttribute("resultMessage", exc.getMessage());
            return new ModelAndView("ticket/buy", "passengerEntity", passenger);
        }

        return new ModelAndView("ticket/buy", "passengerEntity", new PassengerEntity());
    }

}
