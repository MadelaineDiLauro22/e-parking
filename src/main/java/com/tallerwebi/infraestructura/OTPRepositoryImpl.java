package com.tallerwebi.infraestructura;

import com.tallerwebi.model.OTP;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class OTPRepositoryImpl implements OTPRepository{

    private final SessionFactory sessionFactory;

    public OTPRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(OTP otp) {
        Session session = sessionFactory.getCurrentSession();
        sessionFactory.getCurrentSession().saveOrUpdate(otp);
    }

    @Override
    public boolean exists(String userEmail, Long idGarage, String otpKey) {
        Session session = sessionFactory.getCurrentSession();

        OTP otp = (OTP) session.createCriteria(OTP.class)
                .add(Restrictions.eq("userEmail", userEmail))
                .add(Restrictions.eq("idGarage", idGarage))
                .add(Restrictions.eq("otpKey", otpKey))
                .uniqueResult();

        return otp != null;
    }
}
