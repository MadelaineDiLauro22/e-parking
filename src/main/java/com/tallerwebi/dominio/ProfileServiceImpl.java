package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CantRegisterVehicleException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.infraestructura.ParkingRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.infraestructura.VehicleRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Vehicle;
import com.tallerwebi.presentacion.dto.ProfileResponseDTO;
import com.tallerwebi.presentacion.dto.VehicleRegisterDTO;

public class ProfileServiceImpl implements ProfileService{

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final ParkingRepository parkingRepository;

    public ProfileServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository, ParkingRepository parkingRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.parkingRepository = parkingRepository;
    }

    @Override
    public ProfileResponseDTO getMobileUser(Long userId) {
        return null;
    }

    @Override
    public void registerVehicle(VehicleRegisterDTO request, Long userId) {
        MobileUser user = (MobileUser) userRepository.findUserById(userId);
        //TODO: no verifica que llegue el usuario
        Vehicle vehicle = new Vehicle(
                request.getPatent(),
                request.getBrand(),
                request.getModel(),
                request.getColor());
        vehicle.setUser(user);
        vehicleRepository.save(vehicle);

    }
}
