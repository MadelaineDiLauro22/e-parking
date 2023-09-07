package com.tallerwebi.presentacion;

import com.tallerwebi.model.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HttpSession session;

    @GetMapping
    public ModelAndView getHomeRegister() {
        ModelMap model = new ModelMap();
        String email = (String) session.getAttribute("email");
        model.put("email", email);

        return new ModelAndView("home", model);
    }
}
