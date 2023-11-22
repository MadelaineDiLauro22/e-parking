package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ReportService;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Report;
import com.tallerwebi.presentacion.dto.EditReportDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("mobile/admin")
public class AdminController {
    private final ReportService reportService;

    public AdminController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value = "/reports")
    public ModelAndView getAllReports(){
        try{
            List<Report> reports = reportService.getAllReports();
            ModelMap model = new ModelMap();
            model.put("reports", reports);
            return new ModelAndView("admin-report-list");

        }catch (Exception e){
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }

    @PostMapping(value = "edit-report")
    public ModelAndView editReport(@RequestParam(name = "report") EditReportDTO reportDTO){
        try{
            reportService.editReport(reportDTO);
            ModelMap model = new ModelMap();
            model.put("succeed", true);
            return new ModelAndView("admin-edit-report");
        }catch(Exception e){
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }
}
