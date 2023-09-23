package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ProfileService;
import com.tallerwebi.presentacion.dto.VehicleRegisterDTO;
import org.springframework.stereotype.Controller;
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

    //TODO: add constructor when Profile Service is done

    @GetMapping
    public ModelAndView getProfileView() {
        //TODO: implement method
        return null;
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
