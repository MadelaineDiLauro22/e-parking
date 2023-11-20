package com.tallerwebi.infraestructura;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Parking;
import com.tallerwebi.model.Report;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository("reportRepository")
public class ReportRepositoryImpl implements ReportRepository {

    private final SessionFactory sessionFactory;

    public ReportRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Report> getReportByUser(MobileUser mobileUser) {
        return (List<Report>) sessionFactory.getCurrentSession().createCriteria(Report.class)
                .add(Restrictions.eq("user", mobileUser))
                .list();
    }

    @Override
    public List<Report> getAllReports() {
        return (List<Report>) sessionFactory.getCurrentSession().createCriteria(Report.class)
                .add(Restrictions.eq("isActive", true))
                .list();
    }

    @Override
    public void save(Report report) {
        sessionFactory.getCurrentSession().saveOrUpdate(report);
    }
}
