package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.ReportRepository;
import com.tallerwebi.model.Report;

import java.util.List;

public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
    @Override
    public List<Report> getAllReports() {
        return reportRepository.getAllReports();
    }
}
