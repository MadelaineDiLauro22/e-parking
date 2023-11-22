package com.tallerwebi.dominio;

import com.tallerwebi.helpers.NotificationService;
import com.tallerwebi.infraestructura.NotificationRepository;
import com.tallerwebi.infraestructura.ReportRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Notification;
import com.tallerwebi.model.NotificationType;
import com.tallerwebi.model.Report;
import com.tallerwebi.presentacion.dto.EditReportDTO;
import com.tallerwebi.presentacion.dto.NotificationRequestDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public ReportServiceImpl(ReportRepository reportRepository, NotificationRepository notificationRepository,UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
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
    public void registerReport(Report report) {
        reportRepository.save(report);
    }

    @Override
    public List<Report> getUserReport(MobileUser user) {
        return reportRepository.getReportByUser(user);
    }
}
