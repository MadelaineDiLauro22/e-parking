package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.GarageService;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.dominio.excepcion.VehicleNotFoundException;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Vehicle;
import com.tallerwebi.presentacion.dto.VehicleIngressDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("web/api/admin")
public class GarageRestController {

    private final GarageService garageService;

    public GarageRestController(GarageService garageService) {
        this.garageService = garageService;
    }

    @GetMapping("check/{patent}")
    public VehicleIngressDTO checkPatent(@PathVariable String patent) {
        try {
            //TODO: arreglar Transacción con el vehiculo y el usuario
            //MobileUser user = garageService.getUserByPatent(patent);
            Vehicle vehicle = garageService.getVehicleByPatent(patent);

            //if (user == null) throw new UserNotFoundException();
            if (vehicle == null) throw new VehicleNotFoundException(patent);

            VehicleIngressDTO dto = new VehicleIngressDTO();

            dto.setPatent(patent);
            dto.setBrand(vehicle.getBrand());
            dto.setColor(vehicle.getColor());
            dto.setModel(vehicle.getModel());
            //dto.setUserName(user.getName());
            //dto.setUserEmail(user.getEmail());
            dto.setUserEmail("test@unlam.edu.ar");

            return dto;
        } catch (RuntimeException e) {
            VehicleIngressDTO dto = new VehicleIngressDTO();
            return dto;
        }
    }

}
