package com.dipak.dao;

import com.dipak.entity.Account;
import com.dipak.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class AccountDAO {
    HibernateUtil hibernateUtil = new HibernateUtil();
    SessionFactory factory = hibernateUtil.getFactory();
    Session session = factory.openSession();
    Transaction tx = session.beginTransaction();

    public void create(Account account){
        session.persist(account);
        tx.commit();
    }
}
