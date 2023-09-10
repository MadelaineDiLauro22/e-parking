package com.tallerwebi.infraestructura;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Vehicle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository("repositorioVehiculo")
public class VehiculeRepositoryImpl implements VehicleRepository{

    private final SessionFactory sessionFactory;

    public VehiculeRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Vehicle> findVehiclesByUser(MobileUser user) {
        Session session = sessionFactory.getCurrentSession();

        return (List<Vehicle>) session.createCriteria(Vehicle.class)
                .add(Restrictions.eq("usuario", user)).list();
    }
}
