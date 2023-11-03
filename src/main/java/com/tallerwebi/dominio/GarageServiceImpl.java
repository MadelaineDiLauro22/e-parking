package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.VehicleAlreadyParkException;
import com.tallerwebi.dominio.excepcion.VehicleNotFoundException;
import com.tallerwebi.infraestructura.ParkingPlaceRepository;
import com.tallerwebi.infraestructura.ParkingRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.infraestructura.VehicleRepository;
import com.tallerwebi.model.*;
import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;
import com.tallerwebi.presentacion.dto.VehicleIngressDTO;
import org.springframework.stereotype.Service;

import javax.servlet.ServletSecurityElement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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
        Parking parking = createNewParking(vehicleIngressDTO, garageAdminUserId);
        parkingRepository.save(parking);
    }

    @Override
    public void egressVehicle(String vehiclePatent, Long garageAdminUserId) {
        removeFromGarage(vehiclePatent, garageAdminUserId);
        Vehicle vehicle = vehicleRepository.findVehicleByPatent(vehiclePatent);
        MobileUser user = vehicle.getUser();
        Garage garage = getGarageByAdminUserId(garageAdminUserId);
        ParkingRegisterDTO parkingRegisterDTO = new ParkingRegisterDTO(ParkingType.GARAGE, vehiclePatent, null, null, garage.getGeolocation().getLat(), garage.getGeolocation().getLn(), garage.getId());

        List<Parking> parkingList = user.getParkings();

        Parking latestParking = parkingList.get(0);

        for (Parking parking : parkingList) {
            if (parking.getDateArrival().after(latestParking.getDateArrival())) {
                latestParking = parking;
            }
        }
        latestParking.setDateExit(Date.from(Instant.now()));
        garage.generateTicket(parkingRegisterDTO);
        parkingRepository.save(latestParking);
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

    private Parking createNewParking(VehicleIngressDTO vehicleIngressDTO, Long garageAdminUserID){
        Garage garage = getGarageByAdminUserId(garageAdminUserID);
        Vehicle vehicle = vehicleRepository.findVehicleByPatent(vehicleIngressDTO.getPatent());
        MobileUser user = vehicle.getUser();
        Parking parking = new Parking(ParkingType.GARAGE, null, null, garage.getGeolocation(), Date.from(Instant.now()));
        return parking;
    }
}
