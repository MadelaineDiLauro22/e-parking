package com.tallerwebi.infraestructura;

import com.tallerwebi.model.Parking;
import com.tallerwebi.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository("parkingRepository")
public class ParkingRepositoryImpl implements ParkingRepository {

    private final SessionFactory sessionFactory;

    public ParkingRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void save(Parking parking) {
        sessionFactory.getCurrentSession().saveOrUpdate(parking);
    }

    @Override
    public Parking findById(Long id) {
        return (Parking) sessionFactory.getCurrentSession().createCriteria(Parking.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }
}
