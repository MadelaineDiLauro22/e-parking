package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.helpers.EmailService;
import com.tallerwebi.helpers.NotificationService;
import com.tallerwebi.infraestructura.*;
import com.tallerwebi.model.*;
import com.tallerwebi.presentacion.dto.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Transactional
@Service
public class GarageServiceImpl implements GarageService {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingPlaceRepository parkingPlaceRepository;
    private final ParkingRepository parkingRepository;
    private final EmailService emailService;
    private final OTPRepository otpRepository;
    private final NotificationService notificationService;

    @Value("${otp.expiration.time}")
    private int otpExpirationTime;

    public GarageServiceImpl(UserRepository userRepository, VehicleRepository vehicleRepository, ParkingPlaceRepository parkingPlaceRepository, ParkingRepository parkingRepository, EmailService emailService, OTPRepository otpRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.parkingPlaceRepository = parkingPlaceRepository;
        this.parkingRepository = parkingRepository;
        this.emailService = emailService;
        this.otpRepository = otpRepository;
        this.notificationService = notificationService;
    }

    @Override
    public void registerExistingVehicleInSystem(VehicleIngressDTO vehicleIngressDTO, OTPDTO otpDto, Long garageAdminUserId) {
        MobileUser user = userRepository.findUserById(garageAdminUserId);
        if (user == null) throw new UserNotFoundException();
        Garage garage = (Garage) parkingPlaceRepository.findGarageByUser(user);
        if(garage == null) throw new GarageNotFoundException();
        if (otpRepository.exists(vehicleIngressDTO.getUserEmail(), garage.getId(), otpDto.getOtpKey())) {
            addToGarage(vehicleIngressDTO, garage);
            Parking parking = createNewParking(vehicleIngressDTO, garageAdminUserId);
            parkingRepository.save(parking);
            notificationService.registerAndSendNotification(new NotificationRequestDTO(
                    "Gracias por usar el servicio de eParking",
                    String.format("Bienvenido a %s, que tenga una excelente estadía!", garage.getName()),
                    NotificationType.MESSAGE,
                    parking.getMobileUser().getId()
            ));
        } else {
            throw new OTPNotFoundException("No se encontro el OTP.");
        }
    }

    @Override
    public void registerNotExistingVehicleInSystem(VehicleIngressDTO vehicleIngressDTO, Long garageAdminUserId) {
        MobileUser user = userRepository.findUserById(garageAdminUserId);
        if (user == null) throw new UserNotFoundException();
        Garage garage = (Garage) parkingPlaceRepository.findGarageByUser(user);
        if(garage == null) throw new GarageNotFoundException();
        addToGarage(vehicleIngressDTO, garage);
        createNewVehicle(vehicleIngressDTO);
    }

    private void createNewVehicle(VehicleIngressDTO vehicleIngressDTO) {
        Vehicle vehicle = new Vehicle(vehicleIngressDTO.getPatent(), vehicleIngressDTO.getBrand(), vehicleIngressDTO.getModel(), vehicleIngressDTO.getColor());
        vehicleRepository.save(vehicle);
    }

    @Override
    public void sendOtp(String mail, Long idGarage) throws MailException, MessagingException {
        int min = 100000;
        int max = 999999;
        int randomNum = ThreadLocalRandom.current().nextInt(min, max);

        OTP otp = new OTP(String.valueOf(randomNum), mail, idGarage, otpExpirationTime);

        String link = String.format("http://localhost:8080/eparking/mobile/reports?mail=%s&idGarage=%d", mail, idGarage);

        String messageWithStyles = "<div style=\"background-color: rgb(20, 20, 20); display: block;\">\n" +
                "<div style=\"width: 100%; height: 2em; background-color: #FEBC3D;\"></div>\n" +
                "    <div style=\"text-align: center; justify-content: center;\">\n" +
                "        <img src=\"https://i.imgur.com/P8FBUXF.png\" style=\"width: 200px; height: 230px\">\n" +
                "    </div>\n" +
                "<h1 style=\"text-align: center; justify-content: center; color: antiquewhite;\">Código: " + String.valueOf(randomNum) + "</h1>\n" +
                "<p style=\"text-align: center; justify-content: center; color: antiquewhite;\">Estan queriendo ingresar tu vehiculo, si no sos vos, hace la denuncia: <a href=\"" + link + "\" style=\"color: #D13639;\">AQUI</a></p>\n" +
                "<div style=\"width: 100%; height: 2em; background-color: #FEBC3D;\"></div>\n" +
                "</div>";

        otpRepository.save(otp);
        emailService.sendMimeMessage(mail, "Clave de ingreso:", messageWithStyles);
    }


    @Override
    public void egressVehicle(String vehiclePatent, Long garageAdminUserId) {
        Garage garage = getGarageByAdminUserId(garageAdminUserId);
        Vehicle vehicle = vehicleRepository.findVehicleByPatent(vehiclePatent);

        if(vehicle.getUser() != null){
            MobileUser user = vehicle.getUser();

            ParkingRegisterDTO parkingRegisterDTO = new ParkingRegisterDTO(ParkingType.GARAGE, vehiclePatent, null, null, garage.getGeolocation().getLat(), garage.getGeolocation().getLn(), garage.getId(), Date.from(Instant.now()));

            List<Parking> parkingList = user.getParkings();

            Parking latestParking = parkingList.get(0);

            for (Parking parking : parkingList) {
                if (parking.getDateArrival().after(latestParking.getDateArrival())) {
                    latestParking = parking;
                }
            }

            latestParking.setDateExit(Date.from(Instant.now()));
            Ticket ticket = garage.generateTicket(parkingRegisterDTO);
            latestParking.setTicket(ticket);

            parkingRepository.save(latestParking);
            notificationService.registerAndSendNotification(new NotificationRequestDTO(
                    "Gracias por usar el servicio de eParking",
                    String.format("Saliendo de %s, gracias por venir!", garage.getName()),
                    NotificationType.MESSAGE,
                    user.getId()
            ));
        } else{
            vehicleRepository.deleteByPatent(vehiclePatent);
        }

        removeFromGarage(vehiclePatent, garage);
    }

    @Override
    public List<Vehicle> getRegisteredVehicles(Long garageAdminUserId) {
        MobileUser user = userRepository.findUserById(garageAdminUserId);
        Garage garage = (Garage) parkingPlaceRepository.findGarageByUser(user);
        List<String> patents = new ArrayList<>(garage.getPatents());

        return vehicleRepository.findVehiclesByPatents(patents);
    }

    @Override
    public MobileUser getUserByPatent(String patent) {
        Vehicle vehicle = vehicleRepository.findVehicleByPatent(patent);
        MobileUser user = vehicle.getUser();
        Hibernate.initialize(user);
        return user;
    }

    @Override
    public Vehicle getVehicleByPatent(String patent) {
        return vehicleRepository.findVehicleByPatent(patent);
    }

    @Override
    public boolean vehicleExistsInGarage(String patent, Long garageAdminUserId) {
        Garage garage = getGarageByAdminUserId(garageAdminUserId);
        return garage.getPatents().contains(patent);
    }

    @Override
    public boolean vehicleExistsInSystem(String patent) {
        return vehicleRepository.findVehicleByPatent(patent) != null;
    }

    @Override
    public Garage getGarageByAdminUserId(Long garageAdminUserId) {
        return (Garage) parkingPlaceRepository.findGarageByUser(userRepository.findUserById(garageAdminUserId));
    }

    @Override
    public ParkingEgressDTO EstimateEgressVehicle(Parking parking, Long garageAdminUserId) {
        Garage garage = this.getGarageByAdminUserId(garageAdminUserId);
        long expendSeconds = Instant.now().getEpochSecond() - parking.getDateArrival().toInstant().getEpochSecond();
        long expendHours = expendSeconds / 3600;
        long fractionSecondsTime = garage.getFractionTime() * 60;
        double expendPrice;

        if (expendSeconds > fractionSecondsTime) {
            if (expendHours < 1) expendHours = 1;
            expendPrice = garage.getFeePerHour() * expendHours;
        } else {
            expendPrice = garage.getFeeFraction();
        }

        return new ParkingEgressDTO(
                parking.getDateArrival(),
                expendHours,
                expendPrice
        );
    }

    private void addToGarage(VehicleIngressDTO vehicleIngressDTO, Garage garage) {
        if (!garage.addVehicle(vehicleIngressDTO.getPatent())) {
            throw new VehicleAlreadyParkException();
        }
    }

    private void removeFromGarage(String vehiclePatent, Garage garage) {
        if (!garage.removeVehicle(vehiclePatent)) {
            throw new VehicleNotFoundException();
        }
    }

    private Parking createNewParking(VehicleIngressDTO vehicleIngressDTO, Long garageAdminUserID) {
        Garage garage = getGarageByAdminUserId(garageAdminUserID);
        Vehicle vehicle = vehicleRepository.findVehicleByPatent(vehicleIngressDTO.getPatent());
        MobileUser user = vehicle.getUser();
        Date date = Date.from(Instant.now());
        Parking parking = new Parking(ParkingType.GARAGE, null, null, garage.getGeolocation(), date);
        parking.setMobileUser(user);
        parking.setVehicle(vehicle);

        parking.setName(garage.getName());
        return parking;
    }
}
