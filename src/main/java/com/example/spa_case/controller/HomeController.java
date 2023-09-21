package com.example.spa_case.controller;

import com.example.spa_case.model.User;
import com.example.spa_case.model.enums.ELock;
import com.example.spa_case.model.enums.ERole;
import com.example.spa_case.model.enums.EStatusCustomer;
import com.example.spa_case.service.userService.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.spa_case.service.productService.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping(value="/")
public class HomeController {
    private final ProductService productService;
    private final UserService userService;
     private final ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/")
    public ModelAndView getHome() {
        modelAndView.setViewName("index");
        ModelAndView modelAndView = Login();
        modelAndView.addObject("someKey", "someValue");
        return modelAndView;
    }

    @GetMapping("/price")
    public ModelAndView price() {
        modelAndView.setViewName("price");
        ModelAndView modelAndView = Login();
        modelAndView.addObject("someKey", "someValue");
        return modelAndView;
    }

    public ModelAndView Login(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // Tìm người dùng theo username
            Optional<User> user = userService.findByNameIgnoreCaseOrEmailIgnoreCaseOrPhone(username);

            if (user.isPresent()) {
                modelAndView.addObject("loggedIn", true);
                modelAndView.addObject("user", user.get());
            } else {
                modelAndView.addObject("loggedIn", false);
            }
        } else {
            modelAndView.addObject("loggedIn", false);
        }

        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        return "redirect:/login?logout";
    }
    @GetMapping("/access-denied")
    public String error403(){
        return "403";
    }


    @GetMapping("/dashboard")
    public ModelAndView home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
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
        ModelAndView view = new ModelAndView("/combo");
        view.addObject("products", productService.findAll());
        return view;
    }
    @GetMapping("/bill")
    public ModelAndView bill() {
        return new ModelAndView("/bill");
    }
}