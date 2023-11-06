package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.GarageService;
import com.tallerwebi.model.Garage;
import com.tallerwebi.presentacion.dto.VehicleIngressDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("web/admin")
public class GarageController {

    private final GarageService garageService;
    private final HttpSession session;

    public GarageController(GarageService garageService, HttpSession session) {
        this.garageService = garageService;
        this.session = session;
    }

    @GetMapping
    public ModelAndView getHomeGarage(){
        ModelMap model = new ModelMap();
        Garage garage = garageService.getGarageByAdminUserId((Long) session.getAttribute("id"));
        model.put("garage", garage);
        return new ModelAndView("home-garage", model);
    }

    @RequestMapping("/enter")
    public ModelAndView enterVehicle() {
        ModelMap model = new ModelMap();
        model.put("vehicleIngressDTO", new VehicleIngressDTO());

        return new ModelAndView("garage-enter-vehicle", model);
    }

    @RequestMapping("vehicle-remove")
    public ModelAndView removeVehicle(){
        ModelMap model = new ModelMap();
        //model.put("vehicleEgressDTO", new VehicleIngressDTO());

        return new ModelAndView("vehicle-remove", model);
    }

    @PostMapping(value = "/enter/register")
    public ModelAndView validationRegisterVehicle(@RequestParam("vehicleIngressDTO") VehicleIngressDTO vehicleIngressDTO, @RequestParam("otp") String enteredOTP) {
        try {
            String storedOTP = /*garageService.getStoredOTPForUser(vehicleIngressDTO.getEmail())*/"";

            if (enteredOTP.equals(storedOTP)) {
                garageService.registerVehicle(vehicleIngressDTO, (Long) session.getAttribute("id"));
                ModelMap model = new ModelMap();
                model.put("success", true);

                return new ModelAndView("redirect:/web/admin/enter", model);
            } else {
                return new ModelAndView("redirect:/error?errorMessage=El código OTP ingresado es incorrecto.");
            }
        } catch (Exception e) {
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }

    @PostMapping(value = "/egress")
    public ModelAndView egressVehicle(@RequestParam(name = "vehiclePatent") String vehiclePatent) {
        try{
            garageService.egressVehicle(vehiclePatent, (Long) session.getAttribute("id"));

            ModelMap model = new ModelMap();
            model.put("success", true);

            return new ModelAndView("redirect:/web/admin/egress", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }

    @PostMapping(value = "/enter/send-otp")
    public ModelAndView sendOtp(@ModelAttribute("vehicleIngressDTO") VehicleIngressDTO vehicleIngressDTO) {
        try{
            garageService.sendOtp(vehicleIngressDTO.getUserEmail());

            ModelMap model = new ModelMap();
            model.put("vehiclesIngressDto", vehicleIngressDTO);
            model.put("success", true);

            return new ModelAndView("redirect:/web/admin/otp-validate", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }
}
