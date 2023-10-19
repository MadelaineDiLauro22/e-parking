package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ParkingServiceImpl;
import com.tallerwebi.model.ParkingPlace;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("mobile/home")
public class HomeController {

    private final HttpSession session;
    private final ParkingServiceImpl parkingService;

    public HomeController(HttpSession session, ParkingServiceImpl parkingService) {
        this.session = session;
        this.parkingService = parkingService;
    }

    @GetMapping
    public ModelAndView getHomeRegister() {
        ModelMap model = new ModelMap();
        model.put("parkingPlaces", parkingService.getParkingPlaces());

        return new ModelAndView("home", model);
    }
}
