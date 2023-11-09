package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ProfileService;
import com.tallerwebi.dominio.excepcion.NotificationServiceException;
import com.tallerwebi.dominio.excepcion.ParkingNotFoundException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.model.Notification;
import com.tallerwebi.model.Parking;
import com.tallerwebi.presentacion.dto.ProfileResponseDTO;
import com.tallerwebi.presentacion.dto.VehicleRegisterDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("mobile/profile")
public class ProfileController {

    private ProfileService profileService;
    private HttpSession httpSession;

    public ProfileController(ProfileService profileService, HttpSession httpSession){
        this.profileService = profileService;
        this.httpSession = httpSession;
    }

    @GetMapping
    public ModelAndView getProfileView() {

        try{
            ProfileResponseDTO vehiclesAndParkings = profileService.getVehiclesAndParkingsByMobileUser((Long) httpSession.getAttribute("id"));
            ModelMap model = new ModelMap();
            model.put("vehicles", vehiclesAndParkings.getVehicles());
            model.put("parkings", vehiclesAndParkings.getParkings());
            return new ModelAndView("profile", model);
        }
        catch (UserNotFoundException e){
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }

    @GetMapping("/notifications")
    public ModelAndView getNotifications() {
        try {
            List<Notification> notifications = profileService.getAllNotificationsByMobileUser((Long) httpSession.getAttribute("id"));
            ModelMap model = new ModelMap();
            model.put("notifications", notifications);
            return new ModelAndView("notifications-view", model);
        } catch (UserNotFoundException e){
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }

    @GetMapping("vehicle")
    public ModelAndView getRegisterVehicleView() {
        //TODO: implement method
        return null;
    }

    @PostMapping("vehicle/register")
    public ModelAndView registerVehicle(@ModelAttribute("vehicleRegister") VehicleRegisterDTO request) {
        //TODO: implement method
        return null;
    }

    @GetMapping("/parking")
    public ModelAndView getParkingDetail(@RequestParam(name = "id") Long parkingId){
        try{
            ModelMap model = new ModelMap();
            Parking parking = profileService.getParkingById((Long) httpSession.getAttribute("id"), parkingId);
            model.put("parking", parking);

            String vehiclePicture = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(parking.getVehiclePicture());
            model.put("vehiclePicture", vehiclePicture);

            String ticketPicture = "data:image/jpeg;base64," + Base64Utils.encodeToString(parking.getTicketPicture());
            model.put("ticketPicture", ticketPicture);

            return new ModelAndView("parking_detail-view", model);
        } catch (UserNotFoundException | ParkingNotFoundException e){
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }
}
