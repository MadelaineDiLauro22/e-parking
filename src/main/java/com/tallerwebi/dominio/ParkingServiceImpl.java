package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.dominio.excepcion.VehicleNotFoundException;
import com.tallerwebi.infraestructura.ParkingPlaceRepository;
import com.tallerwebi.infraestructura.ParkingRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.infraestructura.VehicleRepository;
import com.tallerwebi.model.*;
import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final ParkingRepository parkingRepository;
    private final ParkingPlaceRepository parkingPlaceRepository;

    public ParkingServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository, ParkingRepository parkingRepository, ParkingPlaceRepository parkingPlaceRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.parkingRepository = parkingRepository;
        this.parkingPlaceRepository = parkingPlaceRepository;
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
        MobileUser user = (MobileUser) userRepository.findUserById(idUser);
        Vehicle vehicle = vehicleRepository.findVehicleByPatent(parkingRegisterDTO.getVehicle());

        if (user == null) throw new UserNotFoundException();
        if (vehicle == null) throw new VehicleNotFoundException();

        Parking parking = new Parking(
                parkingRegisterDTO.getParkingType(),
                parkingRegisterDTO.getVehiclePic(),
                parkingRegisterDTO.getTicketPic(),
                new Geolocation(parkingRegisterDTO.getLat(), parkingRegisterDTO.getLn()),
                parkingRegisterDTO.getParkingDate()
                );

        parking.setMobileUser(user);
        parking.setVehicle(vehicle);

        if (parking.getParkingType().equals(ParkingType.GARAGE)){
            //TO DO: para cuando se agrega la entidad Garage
        }
        else if (parking.getParkingType().equals(ParkingType.POINT_SALE)){
            //TO DO: no tiene que ser new PointSale, sino buscar pointsale por id
            PointSale pointSale = (PointSale) parkingPlaceRepository.findById(parkingRegisterDTO.getParkingPlace().getId());
            Ticket ticket = pointSale.generateTicket(parkingRegisterDTO);
            parking.setTicket(ticket);
        }

        user.registerParking(parking);

        userRepository.save(user);
        parkingRepository.save(parking);
    }
}
