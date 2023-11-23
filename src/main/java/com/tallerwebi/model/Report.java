package com.tallerwebi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "REPORT")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "report_type")
    private ReportType reportType;

    private String description;

    private Date createdAt;

    private boolean isActive;

    private ReportStatus reportStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id")
    private Garage garage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MobileUser user;

    public Report(ReportType reportType, String description, Garage garage, MobileUser user) {
        this.reportType = reportType;
        this.description = description;
        this.garage = garage;
        this.user = user;
        this.createdAt = Date.from(Instant.now());
        this.isActive = true;
        this.reportStatus = ReportStatus.IN_PROCESS;
    }

    public Report() {
    }

    public Long getId() {
        return id;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public Garage getParkingPlace() {
        return garage;
    }

    public MobileUser getUser() {
        return user;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setReportStatus(ReportStatus status) {
        this.reportStatus = status;
    }
}
