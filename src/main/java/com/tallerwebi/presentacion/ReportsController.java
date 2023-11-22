package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ReportService;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Report;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("mobile/reports")
public class ReportsController {
    private final ReportService reportService;


    public ReportsController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ModelAndView getReportView(@RequestParam(name = "mail") String mail, @RequestParam(name = "idGarage") String idGarage) {
        try {
            ModelMap model = new ModelMap();
            model.put("mail", mail);
            model.put("idGarage", idGarage);

            return new ModelAndView("report-register", model);
        } catch (Exception e) {
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }

    @PostMapping(value = "/register")
    public ModelAndView registerReport(@RequestParam(name = "report") Report report){
        try{
            reportService.registerReport(report);
            ModelMap model = new ModelMap();
            model.put("succeed", true);
            return new ModelAndView("report-register", model);

        }catch (Exception e){
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }

    @GetMapping
    public ModelAndView getReportsByUser(@RequestParam(name = "user") MobileUser user){
        try{
            List<Report> userReports = reportService.getUserReport(user);
            ModelMap model = new ModelMap();
            model.put("reports", userReports);

            return new ModelAndView("report-list", model);
        }catch (Exception e){
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }
}
