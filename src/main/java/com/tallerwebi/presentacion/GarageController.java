package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.GarageService;
import com.tallerwebi.model.Garage;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Vehicle;
import com.tallerwebi.presentacion.dto.OTPDTO;
import com.tallerwebi.presentacion.dto.VehicleIngressDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    public ModelAndView getHomeGarage() {
        ModelMap model = new ModelMap();
        Garage garage = garageService.getGarageByAdminUserId((Long) session.getAttribute("id"));
        List<Vehicle> vehicleList = garageService.getRegisteredVehicles((Long) session.getAttribute("id"));
        int cantVehicles = vehicleList.size();
        int capacity = garage.getNumberOfCars() - cantVehicles;
        if (garage != null) {
            model.put("garage", garage);
            model.put("capacity", capacity);
            model.put("vehicles", vehicleList);
            model.put("cantVehicles", cantVehicles);
        } else {
            model.put("error", "No se ha encontrado un garage");
        }
        return new ModelAndView("home-garage", model);
    }

    @RequestMapping("/enter")
    public ModelAndView enterVehicle() {
        ModelMap model = new ModelMap();
        model.put("vehicleIngressDTO", new VehicleIngressDTO());

        return new ModelAndView("garage-enter-vehicle", model);
    }

    @RequestMapping("vehicle-remove")
    public ModelAndView removeVehicle() {
        ModelMap model = new ModelMap();

        return new ModelAndView("vehicle-remove", model);
    }

    //TODO: Por ahora lo cambio a GET porque sino no ejecuta el request desde el navegador, debería ser POST y el navegador tirar un request con fetch
    @GetMapping(value = "/egress")
    public ModelAndView egressVehicle(@RequestParam(name = "patent") String patent) {
        try {
            //TODO: egressVehicle tira un error
            garageService.egressVehicle(patent, (Long) session.getAttribute("id"));

            ModelMap model = new ModelMap();
            model.put("success", true);

            return new ModelAndView("redirect:/web/admin/", model);
        } catch (Exception e) {
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }

    @PostMapping(value = "/enter/send-otp")
    public ModelAndView sendOtp(@ModelAttribute("vehicleIngressDTO") VehicleIngressDTO vehicleIngressDTO) {
        try {
            garageService.sendOtp(vehicleIngressDTO.getUserEmail(), (Long) session.getAttribute("id"));

            ModelMap model = new ModelMap();
            model.put("vehiclesIngressDto", vehicleIngressDTO);
            model.put("success", true);

            return new ModelAndView(
                    String.format("redirect:/web/admin/enter/otp-validate?patent=%s&brand=%s&model=%s&color=%s&userName=%s&userEmail=%s",
                            vehicleIngressDTO.getPatent(),
                            vehicleIngressDTO.getBrand(),
                            vehicleIngressDTO.getModel(),
                            vehicleIngressDTO.getColor(),
                            vehicleIngressDTO.getUserName(),
                            vehicleIngressDTO.getUserEmail()
                    ),
                    model);
        } catch (Exception e) {
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }

    @RequestMapping(value = "/enter/otp-validate")
    public ModelAndView otpValidate(
            @RequestParam String patent,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String userEmail
    ) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("otp", new OTPDTO(
                patent,
                brand,
                model,
                color,
                userName,
                userEmail));

        return new ModelAndView("otp-validate", modelMap);
    }


    @RequestMapping("/view")
    public ModelAndView viewGarageVehicle(@RequestParam(name = "patent") String patent) {
        ModelMap model = new ModelMap();
        Vehicle vehicle = garageService.getVehicleByPatent(patent);

        model.put("vehicle", vehicle);
        return new ModelAndView("garage-vehicle", model);
    }

    @PostMapping(value = "/enter/register")
    public ModelAndView validationRegisterVehicle(@ModelAttribute("vehicleIngressDTO") VehicleIngressDTO vehicleIngressDTO, @ModelAttribute("otp") OTPDTO otpDTO) {
        try {
            garageService.registerVehicle(vehicleIngressDTO, otpDTO, (Long) session.getAttribute("id"));
            ModelMap model = new ModelMap();
            model.put("success", true);

            return new ModelAndView("redirect:/web/admin/", model);
        } catch (Exception e) {
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }
}
