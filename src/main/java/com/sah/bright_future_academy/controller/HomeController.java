package com.sah.bright_future_academy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value={"", "/","/home"}) // out of three mappings, we can use anyone of them to access home.html resource
    public String displayHomePage(Model model) {
        model.addAttribute("institutionName", "Bright Future Academy");
        model.addAttribute("currentPage", "home");
        return "home.html";
    }


}
