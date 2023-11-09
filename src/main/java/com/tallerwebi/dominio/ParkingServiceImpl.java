package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.helpers.Alarm;
import com.tallerwebi.infraestructura.ParkingPlaceRepository;
import com.tallerwebi.infraestructura.ParkingRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.infraestructura.VehicleRepository;
import com.tallerwebi.model.*;
import com.tallerwebi.presentacion.dto.ParkingPlaceResponseDTO;
import com.tallerwebi.presentacion.dto.ParkingRegisterDTO;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;

@Transactional
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
    public List<ParkingPlaceResponseDTO> getParkingPlaces() {
        List<ParkingPlace> parkingPlacesList = parkingPlaceRepository.findAll();
        List<ParkingPlaceResponseDTO> parkingPlaceResponseDTOS = new ArrayList<>();
        for (ParkingPlace parkingPlace:parkingPlacesList) {
            ParkingPlaceResponseDTO parkingPlaceResponseDTO = new ParkingPlaceResponseDTO(parkingPlace.getClass().getSimpleName(), parkingPlace.getId(), parkingPlace.getName(),
                    parkingPlace.getGeolocation(), parkingPlace.getFeePerHour(), parkingPlace.getFeeFraction(), parkingPlace.getFractionTime());
            if(parkingPlace instanceof Garage){
                parkingPlaceResponseDTO.setUserId(((Garage) parkingPlace).getUser().getId());
                parkingPlaceResponseDTO.setNumberOfCars(((Garage) parkingPlace).getNumberOfCars());
                Hibernate.initialize(((Garage) parkingPlace).getPatents());
                parkingPlaceResponseDTO.setPatents(((Garage) parkingPlace).getPatents());
            }
            parkingPlaceResponseDTOS.add(parkingPlaceResponseDTO);
        }
        return parkingPlaceResponseDTOS;
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
                switch (parkingRegisterDTO.getAlarmType()){
                    case NORMAL:
                        createAlarm(parkingRegisterDTO.getAlarmDate());
                        break;
                    case AMOUNT_HS:
                        createAlarmWithAmountHrs(parkingRegisterDTO.getAmmountHrsAlarm());
                        break;
                    case AMOUNT_DESIRED:
                        createAlarmWithAmountDesired(parkingRegisterDTO.getAmountDesired(), parkingRegisterDTO.getParkingPlaceId());
                        break;
                    default:
                        break;
                }
            } catch (InterruptedException | AlarmNotNullException e) {
                throw new ParkingRegisterException(e.getMessage());
            }
        }
        parkingRepository.save(parking);
        userRepository.save(user);
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
        PointSale pointSale = (PointSale) parkingPlaceRepository.findById(parkingRegisterDTO.getParkingPlaceId());
        Ticket ticket = pointSale.generateTicket(parkingRegisterDTO);
        parking.setTicket(ticket);
    }

    private void createAlarm(Date dateTime) throws InterruptedException, AlarmNotNullException {
        if (dateTime == null) throw new AlarmNotNullException();
        alarm.createAlarm(ZonedDateTime.ofInstant(dateTime.toInstant(), ZoneId.of("America/Argentina/Buenos_Aires")));
    }
    private void createAlarmWithAmountHrs(int amountHrsAlarm) throws InterruptedException, AlarmNotNullException {
        if (amountHrsAlarm == 0) throw new AlarmNotNullException();
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime newDateTime = dateTime.plusHours(amountHrsAlarm);
        ZonedDateTime zonedDateTime = newDateTime.atZone(ZoneId.of("America/Argentina/Buenos_Aires"));
        alarm.createAlarm(zonedDateTime);
         }
    private void createAlarmWithAmountDesired(float amountDesired, Long parkingPlaceId) throws InterruptedException, AlarmNotNullException {
        if (parkingPlaceId == null) throw new ParkingNotFoundException();
        if (amountDesired == 0) throw new AlarmNotNullException();
        PointSale pointSale = (PointSale) parkingPlaceRepository.findById(parkingPlaceId);
        if(pointSale == null) throw  new ParkingNotFoundException();
        float hours = amountDesired / pointSale.getFeePerHour();
        int addedHours = Math.round(hours);
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime newDateTime = dateTime.plusHours(addedHours);
        ZonedDateTime zonedDateTime = newDateTime.atZone(ZoneId.of("America/Argentina/Buenos_Aires"));
        alarm.createAlarm(zonedDateTime);
    }
}
