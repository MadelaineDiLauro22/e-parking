package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.GarageNotFoundException;
import com.tallerwebi.dominio.excepcion.ParkingNotFoundException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.infraestructura.*;
import com.tallerwebi.model.*;
import com.tallerwebi.presentacion.dto.ProfileResponseDTO;
import com.tallerwebi.presentacion.dto.VehicleRegisterDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService{

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final ParkingPlaceRepository parkingPlaceRepository;
    private final ReportRepository reportRepository;

    public ProfileServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository, NotificationRepository notificationRepository, ParkingPlaceRepository parkingPlaceRepository, ReportRepository reportRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
        this.parkingPlaceRepository = parkingPlaceRepository;
        this.reportRepository = reportRepository;
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

    @Override
    public void registerReport(Long adminId, String userEmail, String description) {
        MobileUser user = userRepository.findUserByMail(userEmail);
        Garage garage = (Garage) parkingPlaceRepository.findById(adminId);

        if (user == null) throw new UserNotFoundException();
        if (garage == null) throw new GarageNotFoundException();

        Report report = new Report(ReportType.FRAUD, description, garage, user);
        user.addReport(report);
        garage.addReport(report);

        parkingPlaceRepository.save(garage);
        userRepository.save(user);
        reportRepository.save(report);
    }

    @Override
    public List<Report> getReportsByUser(Long userId) {
        MobileUser user = userRepository.findUserById(userId);

        if (user == null) throw new UserNotFoundException();

        return reportRepository.getReportByUser(user);
    }


}
