package com.tsystems.webrailwaysystem.controllers;

import com.tsystems.webrailwaysystem.entities.*;
import com.tsystems.webrailwaysystem.enums.EMessageType;
import com.tsystems.webrailwaysystem.exceptions.*;
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

    public String view = "";

    public Model uiModel = null;

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

        /* if exception happens it helps save object state for user view */
        this.view = "ticket/show";
        this.uiModel = uiModel;
        this.uiModel.addAttribute("ticketsFilter", ticketsFilter);

        uiModel.addAttribute("ticketsList", this.ticketService.searchByParams(ticketsFilter));
        return new ModelAndView("ticket/show", "ticketsFilter", ticketsFilter);
    }

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    public ModelAndView showPassenger(@ModelAttribute TicketsFilter ticketsFilter, Model uiModel) {
        /* if exception happens it helps save object state for user view */
        this.view = "ticket/show";
        this.uiModel = uiModel;
        this.uiModel.addAttribute("ticketsFilter", ticketsFilter);

        uiModel.addAttribute("ticketsList", this.ticketService.searchByParams(ticketsFilter));
        return new ModelAndView("ticket/show", "ticketsFilter", ticketsFilter);
    }

    @RequestMapping(value = "/buy/{id}", method = RequestMethod.GET)
    public ModelAndView buyTicket(@PathVariable("id") int sheduleItemId, Model uiModel, HttpServletRequest request)
            throws NoAvailableSeatsException, ExpiredTimeToBuyException, UserNotFoundException {

        PassengerEntity passenger = new PassengerEntity();
        /* if user register then fill passenger info form with data from user account */
        if(!request.getUserPrincipal().getName().isEmpty()) {
            UserEntity user = this.userService.getByLogin(request.getUserPrincipal().getName());
            passenger.setName(user.getName());
            passenger.setSurname(user.getSurname());
            passenger.setBirthday(user.getBirthday());
        }

        SheduleItemEntity sheduleItem = new SheduleItemEntity();
        sheduleItem.setId(sheduleItemId);

        /* if exception happens it helps save object state for user view */
        this.view = "ticket/buy";
        this.uiModel = uiModel;
        this.uiModel.addAttribute("passengerEntity", passenger);
        this.uiModel.addAttribute("sheduleItem", sheduleItem);

        sheduleItem = this.sheduleService.checkTrainSeatsAndDate(sheduleItem);
        uiModel.addAttribute("sheduleItem", sheduleItem);

        return new ModelAndView("ticket/buy", "passengerEntity", passenger);
    }

    @RequestMapping(value = "/buy/{id}", method = RequestMethod.POST)
    public ModelAndView buyTicket(@PathVariable("id") int sheduleItemId, @ModelAttribute @Valid PassengerEntity passenger,
                                  BindingResult bindingResult, Model uiModel)
            throws NoAvailableSeatsException, ExpiredTimeToBuyException, PassengerAlreadyRegisterException {

        SheduleItemEntity sheduleItem = new SheduleItemEntity();
        sheduleItem.setId(sheduleItemId);

        /* if exception happens it helps save object state for user view */
        this.view = "ticket/buy";
        this.uiModel = uiModel;
        this.uiModel.addAttribute("passengerEntity", passenger);
        this.uiModel.addAttribute("sheduleItem", sheduleItem);

        sheduleItem = this.sheduleService.checkTrainSeatsAndDate(sheduleItem);

        if(bindingResult.hasErrors()) {
            LOGGER.debug("Errors in the adding PassengerEntity");
            return new ModelAndView("ticket/buy", "passengerEntity", passenger);
        }

        TicketEntity ticket = this.ticketService.buyExecute(passenger, sheduleItem);
        uiModel.addAttribute("message", new Message("Successfully buy ticket! " + ticket.toString(), EMessageType.SUCCESS));
        return new ModelAndView("ticket/buy", "passengerEntity", new PassengerEntity());
    }

}
