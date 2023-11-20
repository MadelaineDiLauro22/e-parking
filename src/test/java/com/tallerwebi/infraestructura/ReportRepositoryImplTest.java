package com.tallerwebi.infraestructura;

import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Report;
import com.tallerwebi.model.ReportType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
class ReportRepositoryImplTest {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Rollback
    @Test
    public void whenSaveReportShouldPersistIt() {
        Report report = new Report(ReportType.FRAUD, "some report", null, null);

        reportRepository.save(report);

        List<Report> reportList = reportRepository.getAllReports();

        assertEquals(1, reportList.size());
        assertEquals(report, reportList.get(0));
    }

    @Transactional
    @Rollback
    @Test
    public void whenGetAllReports_shouldNotGetInactiveReports() {
        Report report = new Report(ReportType.FRAUD, "some report", null, null);
        report.setActive(false);

        reportRepository.save(report);

        List<Report> reportList = reportRepository.getAllReports();

        assertTrue(reportList.isEmpty());
    }

    @Transactional
    @Rollback
    @Test
    public void shouldGetAllReportsByUser() {
        MobileUser user = new MobileUser();
        Report report = new Report(ReportType.FRAUD, "some report", null, user);
        user.addReport(report);

        reportRepository.save(report);
        userRepository.save(user);

        List<Report> reportList = reportRepository.getReportByUser(user);

        assertEquals(1, reportList.size());
        assertEquals(report, reportList.get(0));
    }

}