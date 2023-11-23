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

    public EditReportDTO() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
