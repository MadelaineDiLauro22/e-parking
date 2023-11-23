package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ProfileService;
import com.tallerwebi.dominio.ReportService;
import com.tallerwebi.model.Report;
import com.tallerwebi.presentacion.dto.ReportDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("mobile/reports")
public class ReportsController {
    private final ReportService reportService;
    private final HttpSession session;

    public ReportsController(ReportService reportService, HttpSession session) {
        this.reportService = reportService;
        this.session = session;
    }

    @GetMapping
    public ModelAndView getReportView(@RequestParam(name = "mail") String mail, @RequestParam(name = "idGarage") long idGarage) {
        try {
            ModelMap model = new ModelMap();
            model.put("mail", mail);
            model.put("idGarage", idGarage);
            model.put("reportRegister", new ReportDTO(mail, idGarage));

            return new ModelAndView("register-report", model);
        } catch (Exception e) {
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }

    @PostMapping(value = "/register")
    public ModelAndView registerReport(@ModelAttribute("reportRegister") ReportDTO report){
        try{
            reportService.registerReport(report);
            ModelMap model = new ModelMap();
            model.put("success", true);
            return new ModelAndView("redirect:/mobile/home", model);
        }catch (Exception e){
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }

    @GetMapping(value = "/user-reports")
    public ModelAndView getReportsByUser(){
        try{
            List<Report> userReports = reportService.getUserReport((Long) session.getAttribute("id"));
            ModelMap model = new ModelMap();
            model.put("reports", userReports);

            return new ModelAndView("report-list", model);
        }catch (Exception e){
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }
}
