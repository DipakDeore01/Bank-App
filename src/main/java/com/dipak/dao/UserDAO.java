package com.dipak.dao;

import com.dipak.entity.User;
import com.dipak.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDAO {
    HibernateUtil hibernateUtil = new HibernateUtil();
    SessionFactory factory = hibernateUtil.getFactory();
    Session session = factory.openSession();

    public void create(User user){
        session.persist(user);
    }
    public void delete(User user, String email){
       User user1 = session.find(User.class, email);
       session.remove(user1);
    }
}
