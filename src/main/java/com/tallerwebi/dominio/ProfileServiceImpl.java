package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.GarageNotFoundException;
import com.tallerwebi.dominio.excepcion.ParkingNotFoundException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.dominio.excepcion.VehicleNotFoundException;
import com.tallerwebi.infraestructura.*;
import com.tallerwebi.model.*;
import com.tallerwebi.presentacion.dto.ProfileResponseDTO;
import com.tallerwebi.presentacion.dto.VehicleRegisterDTO;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class ProfileServiceImpl implements ProfileService{

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingRepository parkingRepository;
    private final ParkingPlaceRepository parkingPlaceRepository;
    private final ReportRepository reportRepository;

    public ProfileServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository, ParkingRepository parkingRepository, ParkingPlaceRepository parkingPlaceRepository, ReportRepository reportRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.parkingRepository = parkingRepository;
        this.parkingPlaceRepository = parkingPlaceRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public ProfileResponseDTO getVehiclesAndParkingsByMobileUser(Long userId) {
        MobileUser user = (MobileUser) userRepository.findUserById(userId);

        if (user == null) throw new UserNotFoundException();

        Set<Vehicle> listVehicle = user.getVehicles().stream()
                .filter(Vehicle::isIsActive).collect(Collectors.toSet());

        List<Parking> listParking = parkingRepository.findParkingsByUser(user);

        for (Parking parking: listParking) {
            if (parking.getTicket() != null){
                Hibernate.initialize(parking.getTicket());
                Hibernate.initialize(parking.getTicket().getParking_place());
            }
        }

        Collections.sort(listParking);

        return new ProfileResponseDTO(listVehicle, listParking);
    }

    @Override
    public void registerVehicle(VehicleRegisterDTO request, Long userId) {
        MobileUser user = (MobileUser) userRepository.findUserById(userId);

        if (user == null) throw new UserNotFoundException();

        Vehicle vehicle = new Vehicle(
                request.getPatent(),
                request.getBrand(),
                request.getModel(),
                request.getColor());
        vehicle.setUser(user);
        vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public List<Notification> getAllNotificationsByMobileUser(Long idUser) {
        MobileUser user = (MobileUser) userRepository.findUserById(idUser);

        if (user == null) throw new UserNotFoundException();

        Hibernate.initialize(user.getNotifications());

        return user.getNotifications();
    }

    @Override
    public Parking getParkingById(Long userId, Long parkingId) {
        MobileUser user = userRepository.findUserById(userId);

        if (user == null) throw new UserNotFoundException();

        Parking parkingFound = null;
        for (Parking parking: user.getParkings()) {
            if(parking.getId() == parkingId){
                parkingFound = parking;
            }
        };
        if(parkingFound == null) throw new ParkingNotFoundException();
        return parkingFound;
    }

    @Override
    public void removeVehicle(String patent) {
        Vehicle vehicle = vehicleRepository.findVehicleByPatent(patent);
        if (vehicle == null) throw new VehicleNotFoundException();

        vehicleRepository.disableVehicleByPatent(patent);
    }

}
