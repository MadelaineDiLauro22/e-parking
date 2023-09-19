package com.tallerwebi.infraestructura;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository("repositorioUsuario")
public class UserRepositoryImpl implements UserRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public MobileUser findUserByMailAndPassword(String email, String password) {

        final Session session = sessionFactory.getCurrentSession();
        return (MobileUser) session.createCriteria(User.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }

    @Override
    public void save(MobileUser usuario) {
        sessionFactory.getCurrentSession().saveOrUpdate(usuario);
    }

    @Override
    public MobileUser findUserByMail(String email) {
        return (MobileUser) sessionFactory.getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public MobileUser findUserById(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return (MobileUser) session.createCriteria(User.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

}
