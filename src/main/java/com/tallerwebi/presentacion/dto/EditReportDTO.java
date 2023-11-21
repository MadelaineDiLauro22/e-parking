package com.tallerwebi.presentacion.dto;

import com.tallerwebi.model.ReportStatus;

public class EditReportDTO {
    private Long id;
    private boolean isActive;

    private ReportStatus reportStatus;
    private Long userId;

    public EditReportDTO(boolean isActive, ReportStatus reportStatus, Long id, Long userId){
        this.isActive = isActive;
        this.reportStatus = reportStatus;
        this.id = id;
        this.userId = userId;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }
}
