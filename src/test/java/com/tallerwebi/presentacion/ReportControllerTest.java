package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ReportService;
import com.tallerwebi.model.Report;
import com.tallerwebi.presentacion.dto.ReportDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportControllerTest {
    @Mock
    private ReportsController reportsController;
    @Mock
    private HttpSession mockHttpSession;
    @Mock
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reportsController = new ReportsController(reportService, mockHttpSession);
    }

    @Test
    public void shouldGetReportRegisterView(){
        ModelAndView page = reportsController.getReportView("test@unlam.edu.ar", 1);
        assertEquals ("register-report", page.getViewName());
    }

    @Test
    public void shouldRegisterReport_ThenRedirectToHome(){
        ReportDTO reportDTO = new ReportDTO();
        ModelAndView page = reportsController.registerReport(reportDTO);
        Mockito.verify(reportService).registerReport(reportDTO);

        assertEquals ("redirect:/mobile/home", page.getViewName());
        assertTrue((boolean) page.getModel().get("success"));
    }

    @Test
    public void shouldGetReportsByUserView(){
        List<Report> reports = new ArrayList<>();
        Mockito.when(reportService.getUserReport(getUserId()))
                .thenReturn(reports);

        ModelAndView page = reportsController.getReportsByUser();
        List<Report> userReports = (List<Report>)page.getModel().get("reports");

        assertEquals ("report-list", page.getViewName());
        assertEquals(reports, userReports);
    }

    private Long getUserId(){
        return 1L;
    }
}
