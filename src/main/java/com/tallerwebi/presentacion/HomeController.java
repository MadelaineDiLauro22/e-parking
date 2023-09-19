package com.tallerwebi.presentacion;

import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/mobile/home")
public class HomeController {

    private final HttpSession session;

    public HomeController(HttpSession session) {
        this.session = session;
    }

    @GetMapping
    public ModelAndView getHomeRegister() {
        ModelMap model = new ModelMap();

        return new ModelAndView("home", model);
    }
}
