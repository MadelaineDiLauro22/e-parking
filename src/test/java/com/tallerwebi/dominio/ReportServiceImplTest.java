package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.ParkingPlaceRepository;
import com.tallerwebi.infraestructura.ReportRepository;
import com.tallerwebi.infraestructura.UserRepository;
import com.tallerwebi.model.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportServiceImplTest {
    private ReportService reportService;
    @Mock
    private ReportRepository reportRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ParkingPlaceRepository parkingPlaceRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reportService = new ReportServiceImpl(reportRepository);
    }

    @Test
    public void shouldGetReportList(){
        List<Report> reports = new ArrayList<>();
        Report report = new Report();
        reports.add(report);

        Mockito.when(reportRepository.getAllReports())
                .thenReturn(reports);

        List<Report> reportsList = reportService.getAllReports();

        assertEquals(reports,reportsList);
    }

}
