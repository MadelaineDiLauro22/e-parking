package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ParkingService;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.model.Vehicle;
import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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
        try {
            List<Vehicle> list = parkingService.getUserCarsList((Long) session.getAttribute("id"));
            ModelMap model = new ModelMap();
            model.put("vehicleList", list);
            model.put("parkingRegister", new ParkingRegisterDTO());

            return new ModelAndView("parking-register", model);
        } catch (UserNotFoundException e) {
            ModelMap model = new ModelMap();
            model.put("error", e.getMessage());

            return new ModelAndView("parking-register", model);
        }
    }

    @PostMapping
    @RequestMapping("/register")
    public ModelAndView registerParking(@ModelAttribute("parkingRegister") ParkingRegisterDTO parkingRegisterDTO) {
        boolean response = parkingService.registerParking(parkingRegisterDTO);

        ModelMap model = new ModelMap();
        model.put("success", response);

        if (response) {
            return new ModelAndView("redirect:/home", model);
        } else {
            return new ModelAndView("parking-register", model);
        }


    }

}
