package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.AlarmNotNullException;
import com.tallerwebi.dominio.excepcion.ParkingRegisterException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.dominio.excepcion.VehicleNotFoundException;
import com.tallerwebi.helpers.Alarm;
import com.tallerwebi.infraestructura.ParkingPlaceRepository;
import com.tallerwebi.infraestructura.ParkingRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.infraestructura.VehicleRepository;
import com.tallerwebi.model.*;
import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final ParkingRepository parkingRepository;
    private final ParkingPlaceRepository parkingPlaceRepository;
    private final Alarm alarm;

    public ParkingServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository, ParkingRepository parkingRepository, ParkingPlaceRepository parkingPlaceRepository, Alarm alarm) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.parkingRepository = parkingRepository;
        this.parkingPlaceRepository = parkingPlaceRepository;
        this.alarm = alarm;
    }

    @Override
    public List<Vehicle> getUserCarsList(Long idUsuario) {
        MobileUser user = (MobileUser) userRepository.findUserById(idUsuario);

        if (user == null) throw new UserNotFoundException();

        List<Vehicle> vehicles = vehicleRepository.findVehiclesByUser(user);

        if(vehicles.isEmpty()) throw new VehicleNotFoundException();

        return vehicles;
    }

    @Override
    public List<ParkingPlace> getParkingPlaces() {
        return parkingPlaceRepository.findAll();
    }

    @Override
    public void registerParking(ParkingRegisterDTO parkingRegisterDTO, Long idUser) {
        MobileUser user = userRepository.findUserById(idUser);
        Vehicle vehicle = vehicleRepository.findVehicleByPatent(parkingRegisterDTO.getVehicle());

        if (user == null) throw new UserNotFoundException();
        if (vehicle == null) throw new VehicleNotFoundException();

        Parking parking = createNewParking(parkingRegisterDTO, user, vehicle);

        if (parking.getParkingType().equals(ParkingType.GARAGE)){
            //TO DO: para cuando se agrega la entidad Garage
        }
        else if (parking.getParkingType().equals(ParkingType.POINT_SALE)){
            createPointSaleTicket(parking, parkingRegisterDTO);
        }

        user.registerParking(parking);

        if (parkingRegisterDTO.isEnableAlarm()) {
            try {
                createAlarm(parkingRegisterDTO.getAlarmDate());
            } catch (InterruptedException | AlarmNotNullException e) {
                throw new ParkingRegisterException(e.getMessage());
            }
        }

        userRepository.save(user);
        parkingRepository.save(parking);
    }

    private Parking createNewParking(ParkingRegisterDTO parkingRegisterDTO, MobileUser user, Vehicle vehicle) {
        Parking parking = new Parking(
                parkingRegisterDTO.getParkingType(),
                parkingRegisterDTO.getVehiclePic(),
                parkingRegisterDTO.getTicketPic(),
                new Geolocation(parkingRegisterDTO.getLat(), parkingRegisterDTO.getLn()),
                parkingRegisterDTO.getParkingDate()
        );

        parking.setMobileUser(user);
        parking.setVehicle(vehicle);
        return parking;
    }

    private void createPointSaleTicket(Parking parking, ParkingRegisterDTO parkingRegisterDTO) {
        PointSale pointSale = (PointSale) parkingPlaceRepository.findById(parkingRegisterDTO.getParkingPlace().getId());
        Ticket ticket = pointSale.generateTicket(parkingRegisterDTO);
        parking.setTicket(ticket);
    }

    private void createAlarm(Date dateTime) throws InterruptedException, AlarmNotNullException {
        if (dateTime == null) throw new AlarmNotNullException();
        alarm.createAlarm(ZonedDateTime.ofInstant(dateTime.toInstant(), ZoneId.of("America/Argentina/Buenos_Aires")));
    }
}
