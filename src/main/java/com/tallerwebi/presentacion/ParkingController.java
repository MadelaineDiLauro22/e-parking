package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ParkingService;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.dominio.excepcion.VehicleNotFoundException;
import com.tallerwebi.model.ParkingPlace;
import com.tallerwebi.model.Vehicle;
import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
            List<ParkingPlace> parkingPlaceList = parkingService.getParkingPlaces();
            ModelMap model = new ModelMap();
            model.put("vehicleList", list);
            model.put("parkingRegister", new ParkingRegisterDTO());
            model.put("parkingPlaces", parkingPlaceList);

            return new ModelAndView("parking-register", model);
        } catch (UserNotFoundException | VehicleNotFoundException e) {
            ModelMap model = new ModelMap();
            model.put("error", e.getMessage());

            return new ModelAndView("redirect:/mobile/home", model);
        }
    }

    @PostMapping(value = "/register")
    public ModelAndView registerParking(@ModelAttribute("parkingRegister") ParkingRegisterDTO parkingRegisterDTO) {
        try {
            parkingService.registerParking(parkingRegisterDTO, (Long)session.getAttribute("id"));
            ModelMap model = new ModelMap();
            model.put("success", true);

            return new ModelAndView("redirect:/mobile/home", model);
        } catch (UserNotFoundException | VehicleNotFoundException e) {
            ModelMap model = new ModelMap();
            model.put("error", e.getMessage());

            return new ModelAndView("parking-register", model);
        }
    }

}
