package com.tallerwebi.infraestructura;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Parking;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

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

    @Override
    public List<Parking> findParkingsByUser(MobileUser user){
        Session session = sessionFactory.getCurrentSession();

        return (List<Parking>) session.createCriteria(Parking.class)
                .add(Restrictions.eq("user", user)).list();
    }
}
