package com.tallerwebi.presentacion;

import com.tallerwebi.presentacion.dto.VehicleIngressDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("web/admin")
public class GarageController {

    public GarageController() {
    }

    @RequestMapping("/enter")
    public ModelAndView enterVehicle() {

        ModelMap model = new ModelMap();
        model.put("vehicleIngressDTO", new VehicleIngressDTO());
        return new ModelAndView("enter-vehicle", model);
    }

    @PostMapping(value = "/enter/register")
    public ModelAndView registerVehicle(@ModelAttribute("vehicleIngressDTO") VehicleIngressDTO vehicleIngressDTO) {
        try{
            //TO DO:
            //garageService.registerVehicle(vehicleIngressDTO);

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
            //TO DO:
            //garageService.egressVehicle(vehiclePatent);

            ModelMap model = new ModelMap();
            model.put("success", true);
            return new ModelAndView("redirect:/web/admin/egress", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }




}
