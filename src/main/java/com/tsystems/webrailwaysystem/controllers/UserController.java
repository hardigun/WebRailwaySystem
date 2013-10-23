package com.tsystems.webrailwaysystem.controllers;

import com.tsystems.webrailwaysystem.dao.UserDAO;
import com.tsystems.webrailwaysystem.entities.Message;
import com.tsystems.webrailwaysystem.entities.UserEntity;
import com.tsystems.webrailwaysystem.enums.EMessageType;
import com.tsystems.webrailwaysystem.enums.EUserRoles;
import com.tsystems.webrailwaysystem.exceptions.EncryptionGenerationException;
import com.tsystems.webrailwaysystem.exceptions.RailwaySystemException;
import com.tsystems.webrailwaysystem.exceptions.UserAlreadyRegisterException;
import com.tsystems.webrailwaysystem.exceptions.UserNotFoundException;
import com.tsystems.webrailwaysystem.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Date: 19.10.13
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    public String view = "";

    public Model uiModel = null;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/show/{login}", method = RequestMethod.GET)
    public String showUser(@PathVariable String login, Model uiModel, HttpServletRequest request)
            throws UserNotFoundException {

        if(!request.isUserInRole("ROLE_ADMIN") && !request.getUserPrincipal().getName().equals(login)) {
            return "redirect:/auth/denied";
        }
        UserEntity user = this.userService.getByLogin(login);
        uiModel.addAttribute("userEntity", user);
        return "user/show";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addUser(Model uiModel) {
        uiModel.addAttribute("userRoles", new EUserRoles[] { EUserRoles.CLIENT, EUserRoles.MANAGER });
        return new ModelAndView("user/add", "userEntity", new UserEntity());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute @Valid UserEntity user, BindingResult bindingResult,
                                @RequestParam(value = "isupdate", required = false) boolean isupdate,
                                Model uiModel, HttpServletRequest request)
            throws UserAlreadyRegisterException, EncryptionGenerationException {

        if(isupdate) {
            uiModel.addAttribute("isupdate", true);
        }
        uiModel.addAttribute("userRoles", new EUserRoles[] { EUserRoles.CLIENT, EUserRoles.MANAGER });
        if(bindingResult.hasErrors()) {
            return new ModelAndView("user/add", "userEntity", user);
        }

        /* if exception happens it helps save object state for user view */
        this.view = "user/add";
        this.uiModel = uiModel;
        this.uiModel.addAttribute("userEntity", user);

        if(isupdate) {
            this.userService.updateUser(user);
        } else {
            this.userService.addUser(user);
        }

        uiModel.addAttribute("message", new Message("Operation execute success!", EMessageType.SUCCESS));
        if(isupdate) {
            if(request.isUserInRole("ROLE_ADMIN")) {
                uiModel.addAttribute("usersList", this.userService.getAll());
                return new ModelAndView("user/list");
            } else {
                uiModel.addAttribute("userEntity", user);
                return new ModelAndView("user/show");
            }
        }
        return new ModelAndView("user/add", "userEntity", new UserEntity());
    }

    @RequestMapping(value = "/edit/{userId}", method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable int userId, Model uiModel, HttpServletRequest request) {
        UserEntity user = this.userService.getUser(userId);
        if(!request.isUserInRole("ROLE_ADMIN") &&
                (user != null && !request.getUserPrincipal().getName().equals(user.getLogin()))) {
            return new ModelAndView("redirect:/auth/denied");
        }
        uiModel.addAttribute("userRoles", new EUserRoles[] { EUserRoles.CLIENT, EUserRoles.MANAGER });
        uiModel.addAttribute("isupdate", true);
        return new ModelAndView("user/add", "userEntity", user);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listUsers(Model uiModel) {
        uiModel.addAttribute("usersList", this.userService.getAll());
        return "user/list";
    }

}
