package com.example.spa_case.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping(value="/")
public class ClientController {
    private final ModelAndView modelAndView = new ModelAndView();

    @GetMapping("index.html")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("index");

        return view;
    }
    @GetMapping("about.html")
    public ModelAndView about() {
        ModelAndView view = new ModelAndView("about");

        return view;
    }
    @GetMapping("appointment.html")
    public ModelAndView appointment() {
        ModelAndView view = new ModelAndView("appointment");

        return view;
    }
    @GetMapping("contact.html")
    public ModelAndView contact() {
        ModelAndView view = new ModelAndView("contact");

        return view;
    }

    @GetMapping("opening.html")
    public ModelAndView opening() {
        ModelAndView view = new ModelAndView("opening");

        return view;
    }
    @GetMapping("service.html")
    public ModelAndView service() {
        ModelAndView view = new ModelAndView("service");

        return view;
    }
    @GetMapping("team.html")
    public ModelAndView team() {
        ModelAndView view = new ModelAndView("team");

        return view;
    }
    @GetMapping("testimonial.html")
    public ModelAndView testimonial() {
        ModelAndView view = new ModelAndView("testimonial");

        return view;
    }
//    @GetMapping("loginUp.html")
//    public ModelAndView loginUp() {
//        ModelAndView view = new ModelAndView("register");
//
//        return view;
//    }
}
