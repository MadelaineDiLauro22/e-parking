package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.infraestructura.NotificationRepository;
import com.tallerwebi.infraestructura.ParkingRepository;
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

@Service
public class ProfileServiceImpl implements ProfileService{

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final ParkingRepository parkingRepository;
    private final NotificationRepository notificationRepository;

    public ProfileServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository, ParkingRepository parkingRepository, NotificationRepository notificationRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.parkingRepository = parkingRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public ProfileResponseDTO getVehiclesAndParkingsByMobileUser(Long userId) {
        MobileUser user = (MobileUser) userRepository.findUserById(userId);

        if (user == null) throw new UserNotFoundException();

        List<Vehicle> vehicles = vehicleRepository.findVehiclesByUser(user);
        List<Parking> parkings = parkingRepository.findParkingsByUser(user);

        return new ProfileResponseDTO(vehicles, parkings);
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
    public List<Notification> findAllNotificationsByUser(Long idUser) {
        MobileUser user = (MobileUser) userRepository.findUserById(idUser);

        if (user == null) throw new UserNotFoundException();

        List<Notification> notifications = notificationRepository.findAllByUser(user);

        //if(notifications.isEmpty()) throw NotificationsNotFound();

        return notifications;
    }

    @Override
    public List<Notification> findAllNotificationsByUserAndNotRead(Long idUser) {
        MobileUser user = (MobileUser) userRepository.findUserById(idUser);

        if (user == null) throw new UserNotFoundException();

        List<Notification> notifications = notificationRepository.findAllByUserAndNotRead(user);

        //if(notifications.isEmpty()) throw NotificationsNotFound();

        return notifications;
    }
}
