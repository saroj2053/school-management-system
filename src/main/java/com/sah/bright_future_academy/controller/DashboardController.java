package com.sah.bright_future_academy.controller;

import com.sah.bright_future_academy.model.Person;
import com.sah.bright_future_academy.repo.PersonRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/dashboard")
    public String displayDashboardPage(Model model, Authentication auth, HttpSession session) {
        Person person = personRepository.readByEmail(auth.getName());
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", auth.getAuthorities().toString());
        if(person.getBrightFutureClass() != null && person.getBrightFutureClass().getName() != null) {
            model.addAttribute("enrolledClass", person.getBrightFutureClass().getName());
        }
        session.setAttribute("loggedInPerson", person);
        return "dashboard.html";
    }
}
