package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.VehicleAlreadyParkException;
import com.tallerwebi.dominio.excepcion.VehicleNotFoundException;
import com.tallerwebi.infraestructura.ParkingPlaceRepository;
import com.tallerwebi.infraestructura.ParkingRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.infraestructura.VehicleRepository;
import com.tallerwebi.model.*;
import com.tallerwebi.presentacion.dto.VehicleIngressDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class GarageServiceImpl implements GarageService {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingPlaceRepository parkingPlaceRepository;
    private final ParkingRepository parkingRepository;

    public GarageServiceImpl(UserRepository userRepository, VehicleRepository vehicleRepository, ParkingPlaceRepository parkingPlaceRepository, ParkingRepository parkingRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.parkingPlaceRepository = parkingPlaceRepository;
        this.parkingRepository = parkingRepository;
    }

    @Override
    public void registerVehicle(VehicleIngressDTO vehicleIngressDTO, Long garageAdminUserId) {
        addToGarage(vehicleIngressDTO, garageAdminUserId);
    }

    @Override
    public void egressVehicle(String vehiclePatent, Long garageAdminUserId) {
        removeFromGarage(vehiclePatent, garageAdminUserId);
    }

    @Override
    public List<Vehicle> getRegisteredVehicles(Long garageAdminUserId) {
        MobileUser user = userRepository.findUserById(garageAdminUserId);
        Garage garage = (Garage) parkingPlaceRepository.findGarageByUser(user);
        List<String> patents = new ArrayList<>(garage.getPatents());

        return vehicleRepository.findVehiclesByPatents(patents);
    }

    @Override
    public MobileUser getUserByPatent(String patent) {
        Vehicle vehicle = vehicleRepository.findVehicleByPatent(patent);
        return vehicle.getUser();
    }

    @Override
    public Vehicle getVehicleByPatent(String patent) {
        return vehicleRepository.findVehicleByPatent(patent);
    }

    private Garage getGarageByAdminUserId(Long garageAdminUserId) {
        return (Garage) parkingPlaceRepository.findGarageByUser(userRepository.findUserById(garageAdminUserId));
    }

    private void addToGarage(VehicleIngressDTO vehicleIngressDTO, Long garageAdminUserId) {
        Garage garage = getGarageByAdminUserId(garageAdminUserId);
        if (!garage.addVehicle(vehicleIngressDTO.getPatent())) {
            throw new VehicleAlreadyParkException();
        }
    }

    private void removeFromGarage(String vehiclePatent, Long garageAdminUserId) {
        Garage garage = getGarageByAdminUserId(garageAdminUserId);
        if(!garage.removeVehicle(vehiclePatent)){
            throw new VehicleNotFoundException();
        }
    }
}
