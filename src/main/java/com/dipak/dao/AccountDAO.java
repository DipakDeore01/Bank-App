package com.dipak.dao;

import com.dipak.entity.Account;
import com.dipak.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class AccountDAO {

    HibernateUtil hibernateUtil = new HibernateUtil();
    SessionFactory factory = hibernateUtil.getFactory();

    public void create(Account account) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(account);
            tx.commit();
        }
    }

    public Account view(String acc_no) {
        try (Session session = factory.openSession()) {
            return session.find(Account.class, acc_no);
        }
    }
}
