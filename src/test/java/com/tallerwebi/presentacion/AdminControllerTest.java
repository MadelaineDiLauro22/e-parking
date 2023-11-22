package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.GarageServiceImpl;
import com.tallerwebi.dominio.ReportService;
import com.tallerwebi.model.Notification;
import com.tallerwebi.model.Report;
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

public class AdminControllerTest {
    private AdminController adminController;
    @Mock
    private ReportService reportService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        adminController = new AdminController(reportService);
    }

    @Test
    public void shouldGetAdminAllReportsView(){
        List<Report> reports = new ArrayList<>();
        Mockito.when(reportService.getAllReports()).thenReturn(reports);

        ModelAndView page = adminController.getAllReports();
        assertEquals ("admin-report-list", page.getViewName());

        List<Report> resultReports = (List<Report>) page.getModel().get("reports");
        assertEquals(reports, resultReports);
    }



}
