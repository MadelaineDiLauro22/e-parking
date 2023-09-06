package com.tallerwebi.infraestructura;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Vehiculo;
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
    public List<Vehiculo> obtenerVehiculosPorUsuario(MobileUser user) {
        Session session = sessionFactory.getCurrentSession();

        return (List<Vehiculo>) session.createCriteria(Vehiculo.class)
                .add(Restrictions.eq("usuario", user)).list();
    }
}
