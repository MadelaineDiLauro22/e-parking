package com.tallerwebi.presentacion.dto;

import com.tallerwebi.model.ReportType;

import javax.persistence.Column;
import java.util.Date;

public class ReportDTO {
    @Column(name = "report_type")
    private ReportType reportType;
    private String description;
    private String userEmail;
    private Long garageId;

    public ReportDTO(ReportType reportType, String description, String userEmail, Long garageId) {
        this.reportType = reportType;
        this.description = description;
        this.userEmail = userEmail;
        this.garageId = garageId;
    }

    public ReportDTO() {
    }

    public ReportType getReportType() {
        return reportType;
    }

    public String getDescription() {
        return description;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public Long getGarageId() {
        return garageId;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setGarageId(Long garageId) {
        this.garageId = garageId;
    }
}
