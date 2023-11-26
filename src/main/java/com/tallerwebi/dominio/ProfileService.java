package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CantRegisterVehicleException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.model.Notification;
import com.tallerwebi.model.Parking;
import com.tallerwebi.model.Report;
import com.tallerwebi.presentacion.dto.ProfileResponseDTO;
import com.tallerwebi.presentacion.dto.VehicleRegisterDTO;

import java.util.List;

public interface ProfileService {

    ProfileResponseDTO getVehiclesAndParkingsByMobileUser(Long userId) throws UserNotFoundException;
    void registerVehicle(VehicleRegisterDTO request, Long userId) throws UserNotFoundException;
    List<Notification> getAllNotificationsByMobileUser(Long idUser);
    Parking getParkingById(Long userId, Long parkingId);
    void registerReport(Long adminId, String userEmail, String description);
    List<Report> getReportsByUser(Long userId);
    void removeVehicle(String patent);
}
