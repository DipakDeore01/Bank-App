package com.dipak.dao;

import com.dipak.entity.Users;
import com.dipak.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDAO {
    HibernateUtil hibernateUtil = new HibernateUtil();
    SessionFactory factory = hibernateUtil.getFactory();
    Session session = factory.openSession();
    Transaction tx = session.beginTransaction();

    public void create(Users users){
        session.persist(users);
        tx.commit();
    }
    public void delete(String email){
       Users users = session.find(Users.class, email);
       session.remove(users);
    }
}
