package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ParkingNotFoundException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.infraestructura.NotificationRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.infraestructura.VehicleRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Notification;
import com.tallerwebi.model.Parking;
import com.tallerwebi.model.Vehicle;
import com.tallerwebi.presentacion.dto.ProfileResponseDTO;
import com.tallerwebi.presentacion.dto.VehicleRegisterDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProfileServiceImpl implements ProfileService{

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public ProfileServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository, NotificationRepository notificationRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public ProfileResponseDTO getVehiclesAndParkingsByMobileUser(Long userId) {
        MobileUser user = (MobileUser) userRepository.findUserById(userId);

        if (user == null) throw new UserNotFoundException();

        return new ProfileResponseDTO(user.getVehicles(), user.getParkings());
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
    public List<Notification> getAllNotificationsByMobileUser(Long idUser) {
        MobileUser user = (MobileUser) userRepository.findUserById(idUser);

        if (user == null) throw new UserNotFoundException();

        List<Notification> notifications = notificationRepository.findAllByUser(user);

        return notifications;
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
}
