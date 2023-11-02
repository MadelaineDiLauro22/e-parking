package com.tallerwebi.infraestructura;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Parking;
import com.tallerwebi.model.ParkingPlace;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Repository("parkingPlaceRepository")
public class ParkingPlaceRepositoryImpl implements ParkingPlaceRepository{

    private final SessionFactory sessionFactory;

    public ParkingPlaceRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ParkingPlace findById(Long id) {
        return (ParkingPlace) sessionFactory.getCurrentSession().createCriteria(ParkingPlace.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public ParkingPlace findGarageByUser(MobileUser user) {
        return (ParkingPlace) sessionFactory.getCurrentSession().createCriteria(ParkingPlace.class)
                .add(Restrictions.eq("user", user))
                .uniqueResult();
    }

    @Override
    public List<ParkingPlace> findAll() {
        return (List<ParkingPlace>) sessionFactory.getCurrentSession().createCriteria(ParkingPlace.class).list();
    }

    @Override
    public void save(ParkingPlace parkingPlace) {
        sessionFactory.getCurrentSession().saveOrUpdate(parkingPlace);
    }
}
