package com.tallerwebi.dominio;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Report;
import com.tallerwebi.presentacion.dto.EditReportDTO;

import java.util.List;

public interface ReportService {
     List<Report> getAllReports();
     void editReport(EditReportDTO editReportDTO);

     void registerReport(Report report);

     List<Report> getUserReport(MobileUser user);
}
