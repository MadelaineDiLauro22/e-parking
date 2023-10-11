package com.tallerwebi.infraestructura;

import com.tallerwebi.model.MobileUser;
import com.tallerwebi.model.Notification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository("notificationRepository")
public class NotificationRepositoryImpl implements NotificationRepository {

    private final SessionFactory sessionFactory;

    public NotificationRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Notification> findAllByUser(MobileUser user) {
        Session session = sessionFactory.getCurrentSession();

        return (List<Notification>) session.createCriteria(Notification.class)
                .add(Restrictions.eq("user", user)).list();
    }

    @Override
    public List<Notification> findAllByUserAndNotRead(MobileUser user) {
        Session session = sessionFactory.getCurrentSession();

        return (List<Notification>) session.createCriteria(Notification.class)
                .add(Restrictions.eq("user", user))
                .add(Restrictions.eq("isRead", false))
                .addOrder(Order.desc("creationDate")).list();
    }

    @Override
    public void save(Notification notification) {
        sessionFactory.getCurrentSession().saveOrUpdate(notification);
    }
}
