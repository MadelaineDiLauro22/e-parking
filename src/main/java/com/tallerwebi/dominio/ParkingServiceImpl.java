package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.dominio.excepcion.VehicleNotFoundException;
import com.tallerwebi.infraestructura.ParkingRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.infraestructura.VehicleRepository;
import com.tallerwebi.model.Geolocation;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Parking;
import com.tallerwebi.model.Vehicle;
import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    private final ParkingRepository parkingRepository;

    public ParkingServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository, ParkingRepository parkingRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.parkingRepository = parkingRepository;
    }

    @Override
    public List<Vehicle> getUserCarsList(Long idUsuario) {
        MobileUser user = (MobileUser) userRepository.findUserById(idUsuario);
        if (user == null) throw new UserNotFoundException();

        return vehicleRepository.findVehiclesByUser(user);
    }

    @Override
    public void registerParking(ParkingRegisterDTO parkingRegisterDTO, Long idUser) {
        MobileUser user = (MobileUser) userRepository.findUserById(idUser);
        Vehicle vehicle = vehicleRepository.findVehicleByPatent(parkingRegisterDTO.getVehicle());

        if (user == null) throw new UserNotFoundException();
        if (vehicle == null) throw new VehicleNotFoundException();

        Parking parking = new Parking(
                parkingRegisterDTO.getParkingType(),
                parkingRegisterDTO.getVehiclePic(),
                parkingRegisterDTO.getTicketPic(),
                new Geolocation(parkingRegisterDTO.getLat(), parkingRegisterDTO.getLn()),
                Date.from(Instant.now()));

        parking.setMobileUser(user);
        parking.setVehicle(vehicle);
        user.registerParking(parking);

        userRepository.save(user);
        parkingRepository.save(parking);
    }
}
