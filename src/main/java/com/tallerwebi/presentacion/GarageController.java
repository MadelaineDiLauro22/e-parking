package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.GarageService;
import com.tallerwebi.presentacion.dto.VehicleIngressDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("web/admin")
public class GarageController {

    private GarageService garageService;
    private final HttpSession session;

    public GarageController(GarageService garageService, HttpSession session) {
        this.garageService = garageService;
        this.session = session;
    }

    @RequestMapping("/enter")
    public ModelAndView enterVehicle() {

        ModelMap model = new ModelMap();
        model.put("vehicleIngressDTO", new VehicleIngressDTO());
        return new ModelAndView("garage-enter-vehicle", model);
    }

    @PostMapping(value = "/enter/register")
    public ModelAndView registerVehicle(@ModelAttribute("vehicleIngressDTO") VehicleIngressDTO vehicleIngressDTO) {
        try{
            garageService.registerVehicle(vehicleIngressDTO, (Long) session.getAttribute("id"));

            ModelMap model = new ModelMap();
            model.put("success", true);
            return new ModelAndView("redirect:/web/admin/enter", model);
        }
        catch (Exception e){
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




}
