package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final HttpSession session;

    public HomeController(HttpSession session) {
        this.session = session;
    }

    @GetMapping
    public ModelAndView getHomeRegister() {
        ModelMap model = new ModelMap();
        Long id = (Long) session.getAttribute("id");
        model.put("id", id);

        return new ModelAndView("home", model);
    }
}
