package com.example.spa_case.controller;

import com.example.spa_case.model.enums.ELock;
import com.example.spa_case.model.enums.ERole;
import com.example.spa_case.model.enums.EStatusCustomer;
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
        ModelAndView view = new ModelAndView("/user");
        view.addObject("statusCustomer", EStatusCustomer.values());
        view.addObject("role", ERole.values());
        view.addObject("lock", ELock.values());
        return view;
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
}