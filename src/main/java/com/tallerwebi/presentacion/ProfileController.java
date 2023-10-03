package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ProfileService;
import com.tallerwebi.presentacion.dto.ProfileResponseDTO;
import com.tallerwebi.presentacion.dto.VehicleRegisterDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("mobile/profile")
public class ProfileController {

    private ProfileService profileService;
    private HttpSession httpSession;

    public ProfileController(ProfileService profileService, HttpSession httpSession){
        this.profileService = profileService;
        this.httpSession = httpSession;
    }

    @GetMapping
    public ModelAndView getProfileView() {
        ProfileResponseDTO vehiclesAndParkings = profileService.getVehiclesAndParkingsByMobileUser((Long) httpSession.getAttribute("id"));
        ModelMap model = new ModelMap();
        model.put("vehicles", vehiclesAndParkings.getVehicles());
        model.put("parkings", vehiclesAndParkings.getParkings());
        return new ModelAndView("profile", model);
    }

    @GetMapping("vehicle")
    public ModelAndView getRegisterVehicleView() {
        //TODO: implement method
        return null;
    }

    @PostMapping("vehicle/register")
    public ModelAndView registerVehicle(@ModelAttribute("vehicleRegister") VehicleRegisterDTO request) {
        //TODO: implement method
        return null;
    }
}
