package com.tsystems.webrailwaysystem.controllers;

import com.tsystems.webrailwaysystem.entities.*;
import com.tsystems.webrailwaysystem.enums.EMessageType;
import com.tsystems.webrailwaysystem.exceptions.RailwaySystemException;
import com.tsystems.webrailwaysystem.filters.TicketsFilter;
import com.tsystems.webrailwaysystem.services.SheduleService;
import com.tsystems.webrailwaysystem.services.TicketService;
import com.tsystems.webrailwaysystem.services.UserService;
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

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView showPassengers() {
        return new ModelAndView("ticket/show", "ticketsFilter", new TicketsFilter());
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public ModelAndView showPassengers(@PathVariable("id") int sheduleItemId, Model uiModel) {
        TicketsFilter ticketsFilter = new TicketsFilter();
        SheduleItemEntity sheduleItem = new SheduleItemEntity();
        sheduleItem.setId(sheduleItemId);
        ticketsFilter.setSheduleItem(sheduleItem);
        uiModel.addAttribute("ticketsList", this.ticketService.searchByParams(ticketsFilter));
        return new ModelAndView("ticket/show", "ticketsFilter", ticketsFilter);
    }

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    public ModelAndView showPassenger(@ModelAttribute TicketsFilter ticketsFilter, Model uiModel) {
        uiModel.addAttribute("ticketsList", this.ticketService.searchByParams(ticketsFilter));
        return new ModelAndView("ticket/show", "ticketsFilter", ticketsFilter);
    }

    @RequestMapping(value = "/buy/{id}", method = RequestMethod.GET)
    public ModelAndView buyTicket(@PathVariable("id") int sheduleItemId, Model uiModel, HttpServletRequest request) {
        SheduleItemEntity sheduleItem = new SheduleItemEntity();
        sheduleItem.setId(sheduleItemId);
        try {
            sheduleItem = this.sheduleService.checkTrainSeatsAndDate(sheduleItem);
        } catch(RailwaySystemException exc) {
            LOGGER.debug("Errors while buying ticket: " + exc.getMessage());
            uiModel.addAttribute("message", new Message(exc.getMessage(), EMessageType.ERROR));
        }
        uiModel.addAttribute("sheduleItem", sheduleItem);

        PassengerEntity passenger = new PassengerEntity();
        /* if user register then fill passenger info form with data from user account */
        UserEntity user = null;
        try {
            user = this.userService.getByLogin(request.getUserPrincipal().getName());
        } catch(Exception exc) {
            exc.printStackTrace();
            LOGGER.warn(exc);
        }
        if(user != null) {
            passenger.setName(user.getName());
            passenger.setSurname(user.getSurname());
            passenger.setBirthday(user.getBirthday());
        }
        /*---------*/

        return new ModelAndView("ticket/buy", "passengerEntity", passenger);
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
            uiModel.addAttribute("message", new Message(exc.getMessage(), EMessageType.ERROR));
        }
        uiModel.addAttribute("sheduleItem", sheduleItem);
        if(uiModel.containsAttribute("message")) {
            return new ModelAndView("ticket/buy", "passengerEntity", new PassengerEntity());
        }

        if(bindingResult.hasErrors()) {
            LOGGER.debug("Errors in the adding PassengerEntity");
            return new ModelAndView("ticket/buy", "passengerEntity", passenger);
        }

        try {
            TicketEntity ticket = this.ticketService.buyExecute(passenger, sheduleItem);
            uiModel.addAttribute("message", new Message("Successfully buy ticket! " + ticket.toString(), EMessageType.SUCCESS));
        } catch(RailwaySystemException exc) {
            LOGGER.debug("Errors while buying ticket: " + exc.getMessage());
            uiModel.addAttribute("message", new Message(exc.getMessage(), EMessageType.ERROR));
            return new ModelAndView("ticket/buy", "passengerEntity", passenger);
        }

        return new ModelAndView("ticket/buy", "passengerEntity", new PassengerEntity());
    }

}
