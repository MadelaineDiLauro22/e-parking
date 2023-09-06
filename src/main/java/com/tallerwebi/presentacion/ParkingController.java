package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ParkingService;
import com.tallerwebi.model.Usuario;
import com.tallerwebi.model.Vehiculo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("mobile/parking")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping
    public ModelAndView getParkingRegister(@RequestParam("id") Long userId) {
        List<Vehiculo> list = parkingService.getUserCarsList(userId);
        ModelMap model = new ModelMap();
        model.put("vehicleList", list);

        return new ModelAndView("parking-register", model);
    }

}
