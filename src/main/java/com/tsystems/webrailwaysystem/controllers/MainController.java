package com.tsystems.webrailwaysystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Date: 19.10.13
 */
@Controller
public class MainController {

    @RequestMapping("/")
    public String mainPage() {
        return "index";
    }

}
