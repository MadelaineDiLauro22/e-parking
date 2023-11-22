package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.GarageNotFoundException;
import com.tallerwebi.dominio.excepcion.UserNotFoundException;
import com.tallerwebi.infraestructura.NotificationRepository;
import com.tallerwebi.infraestructura.ParkingPlaceRepository;
import com.tallerwebi.infraestructura.ReportRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.model.*;
import com.tallerwebi.presentacion.dto.EditReportDTO;
import com.tallerwebi.presentacion.dto.ReportDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final ParkingPlaceRepository parkingPlaceRepository;

    public ReportServiceImpl(ReportRepository reportRepository, NotificationRepository notificationRepository, UserRepository userRepository, ParkingPlaceRepository parkingPlaceRepository) {
        this.reportRepository = reportRepository;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.parkingPlaceRepository = parkingPlaceRepository;
    }
    @Override
    public List<Report> getAllReports() {
        return reportRepository.getAllReports();
    }

    @Override
    public void editReport(EditReportDTO editReportDTO) {
        Report report = reportRepository.getReportById(editReportDTO.getId());
        MobileUser user = userRepository.findUserById(editReportDTO.getUserId());

        report.setReportStatus(editReportDTO.getReportStatus());
        report.setActive(editReportDTO.getIsActive());

        reportRepository.save(report);
        notificationRepository.save(new Notification("Su denuncia fue revisada por un administrador","Vea las novedades acerca del estado de su denuncia", Date.from(Instant.now()),user));
    }

    @Override
    public void registerReport(ReportDTO reportDTO) {
        Garage garage = (Garage) parkingPlaceRepository.findById(reportDTO.getGarageId());
        if(garage == null) throw new GarageNotFoundException();
        MobileUser user = userRepository.findUserByMail(reportDTO.getUserEmail());
        if(user == null) throw new UserNotFoundException();

        Report report = new Report(reportDTO.getReportType(), reportDTO.getDescription(), garage, user);
        report.setActive(true);
        report.setReportStatus(ReportStatus.IN_PROCESS);

        reportRepository.save(report);
    }

    @Override
    public List<Report> getUserReport(Long userId) {
        MobileUser user = userRepository.findUserById(userId);
        return reportRepository.getReportByUser(user);
    }

    @Override
    public Report findReportById(Long id) {
        return reportRepository.getReportById(id);
    }
}
