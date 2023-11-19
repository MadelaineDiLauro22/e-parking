package com.tallerwebi.infraestructura;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Report;

import java.util.List;

public interface ReportRepository {

    List<Report> getReportByUser(MobileUser mobileUser);

    List<Report> getAllReports();

    void save(Report report);
}
