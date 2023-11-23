package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ReportService;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Report;
import com.tallerwebi.presentacion.dto.EditReportDTO;
import com.tallerwebi.presentacion.dto.ReportDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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
            return new ModelAndView("admin-report-list", model);

        }catch (Exception e){
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }

    @GetMapping(value = "view-report")
    public ModelAndView viewReport(@RequestParam(name = "id") Long reportId){
        try{
            ModelMap model = new ModelMap();
            Report report = reportService.findReportById(reportId);
            model.put("report", report);
            model.put("editReport", new EditReportDTO(report.isActive(), report.getReportStatus(), reportId, report.getUser().getId()));
            model.put("success", true);

            return new ModelAndView("admin-edit-report", model);
        }catch(Exception e){
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }

    @PostMapping(value = "edit-report")
    public ModelAndView editReport(@ModelAttribute("editReport") EditReportDTO reportDTO){
        try{
            reportService.editReport(reportDTO);
            ModelMap model = new ModelMap();
            model.put("success", true);
            return new ModelAndView("redirect:/mobile/admin/reports", model);
        }catch(Exception e){
            return new ModelAndView("redirect:/error?errorMessage=" + e.getMessage());
        }
    }
}
