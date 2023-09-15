package com.example.spa_case.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping
public class HomeController {
    @GetMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("/index");
    }

    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        return new ModelAndView("/dashboard");
    }

    @GetMapping("/user")
    public ModelAndView user() {
        return new ModelAndView("/user");
    }

    @GetMapping("/product")
    public ModelAndView product() {
        return new ModelAndView("/product");
    }

    @GetMapping("/combo")
    public ModelAndView combo() {
        return new ModelAndView("/combo");
    }

    @GetMapping("/bill")
    public ModelAndView bill() {
        return new ModelAndView("/bill");
    }

    @GetMapping("/service")
    public ModelAndView service() {
        return new ModelAndView("/service");
    }

    @GetMapping("/about")
    public ModelAndView view() {
        return new ModelAndView("/about");
    }

    @GetMapping("/appointment")
    public ModelAndView appointment() {
        return new ModelAndView("/appointment");
    }

    @GetMapping("/contact")
    public ModelAndView contact() {
        return new ModelAndView("/contact");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @GetMapping("/loginUp")
    public ModelAndView loginUp() {
        return new ModelAndView("/loginUp");
    }

    @GetMapping("/opening")
    public ModelAndView opening() {
        return new ModelAndView("/opening");
    }

    @GetMapping("/price")
    public ModelAndView price() {
        return new ModelAndView("/price");
    }

    @GetMapping("/team")
    public ModelAndView team() {
        return new ModelAndView("/team");
    }

    @GetMapping("/testimonial")
    public ModelAndView testimonial() {
        return new ModelAndView("/testimonial");
    }

}
