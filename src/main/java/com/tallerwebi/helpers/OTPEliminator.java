package com.tallerwebi.helpers;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.tallerwebi.model.OTP;


import java.util.Collections;

@Component
public class OTPEliminator {
    @Autowired
    private SessionFactory sessionFactory;

    @Scheduled(fixedRate = 300000)
    public void deleteData() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Criteria criteria = session.createCriteria(OTP.class);

        criteria.add(Restrictions.allEq(Collections.emptyMap()));

        criteria.list().forEach(session::delete);

        session.getTransaction().commit();
    }
}