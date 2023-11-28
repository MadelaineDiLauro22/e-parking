package com.tallerwebi.dominio;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Report;
import com.tallerwebi.presentacion.dto.EditReportDTO;
import com.tallerwebi.presentacion.dto.ReportDTO;

import java.util.List;

public interface ReportService {
     List<Report> getAllReports();
     void editReport(EditReportDTO editReportDTO);
     void registerReport(ReportDTO reportDTO);
     List<Report> getUserReport(Long userId);
     Report findReportById(Long id);
}
