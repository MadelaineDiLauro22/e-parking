package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CantRegisterVehicleException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.model.Notification;
import com.tallerwebi.presentacion.dto.ProfileResponseDTO;
import com.tallerwebi.presentacion.dto.VehicleRegisterDTO;

import java.util.List;

public interface ProfileService {

    ProfileResponseDTO getVehiclesAndParkingsByMobileUser(Long userId) throws UserNotFoundException;

    void registerVehicle(VehicleRegisterDTO request, Long userId) throws UserNotFoundException;
    List<Notification> getAllNotificationsByMobileUser(Long idUser);

}
