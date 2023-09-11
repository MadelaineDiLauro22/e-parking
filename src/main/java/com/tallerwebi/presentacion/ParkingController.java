package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ParkingService;
import com.tallerwebi.model.Vehicle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("mobile/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final HttpSession session;

    public ParkingController(ParkingService parkingService, HttpSession session) {
        this.parkingService = parkingService;
        this.session = session;
    }

    @GetMapping
    public ModelAndView getParkingRegister() {
        List<Vehicle> list = parkingService.getUserCarsList((Long)session.getAttribute("id"));
        ModelMap model = new ModelMap();
        model.put("vehicleList", list);

        return new ModelAndView("parking-register", model);
    }

}
