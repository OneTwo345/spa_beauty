package com.example.spa_case.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping(value="/")
public class HomeController {
    @GetMapping("/dashboard")
    public ModelAndView home() {
        return new ModelAndView("/dashboard");
    }
    @GetMapping("/user")
    public ModelAndView user() {
        return new ModelAndView("/user");
    }

    @GetMapping("/service")
    public ModelAndView product() {
        return new ModelAndView("/service");
    }

    @GetMapping("/combo")
    public ModelAndView combo() {
        return new ModelAndView("/combo");
    }
    @GetMapping("/bill")
    public ModelAndView bill() {
        return new ModelAndView("/bill");
    }
}
