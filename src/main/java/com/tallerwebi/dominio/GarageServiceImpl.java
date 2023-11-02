package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.ParkingPlaceRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.infraestructura.VehicleRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Vehicle;
import com.tallerwebi.presentacion.dto.VehicleIngressDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GarageServiceImpl implements GarageService {

    private final UserRepository userRepository;

    private final VehicleRepository vehicleRepository;
    private final ParkingPlaceRepository parkingPlaceRepository;

    public GarageServiceImpl(UserRepository userRepository, VehicleRepository vehicleRepository, ParkingPlaceRepository parkingPlaceRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.parkingPlaceRepository = parkingPlaceRepository;
    }

    @Override
    public void registerVehicle(VehicleIngressDTO vehicleIngressDTO, Long userId) {

    }

    @Override
    public void egressVehicle(String vehiclePatent, Long userId) {

    }

    @Override
    public List<Vehicle> getRegisteredVehicles() {
        return null;
    }

    @Override
    public MobileUser getUserByPatent(String patent) {
        return null;
    }

    @Override
    public Vehicle getVehicleByPatent(String patent) {
        return null;
    }
}
