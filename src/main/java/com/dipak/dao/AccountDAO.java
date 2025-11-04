package com.dipak.dao;

import com.dipak.entity.Account;
import com.dipak.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class AccountDAO {

    private final SessionFactory factory = HibernateUtil.getFactory();

    public Account view(String acc_no) {
        try (Session session = factory.openSession()) {
            return session.get(Account.class, acc_no);
        }
    }

    public void create(Account account) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(account);
            tx.commit();
        }
    }

    public void transfer(String senderPhone, String receiverPhone, double amount) {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();

            Account sender = session.createQuery("from Account where phone = :phone", Account.class)
                    .setParameter("phone", senderPhone)
                    .uniqueResult();

            Account receiver = session.createQuery("from Account where phone = :phone", Account.class)
                    .setParameter("phone", receiverPhone)
                    .uniqueResult();

            if (sender == null || receiver == null) {
                System.out.println("Account not found.");
                if (tx != null) tx.rollback();
                return;
            }

            if (sender.getBalance() < amount) {
                System.out.println("Insufficient balance.");
                if (tx != null) tx.rollback();
                return;
            }

            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);

            session.merge(sender);
            session.merge(receiver);
            tx.commit();
            System.out.println("₹" + amount + " transferred successfully to "+receiver.getName());
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void accountTransfer(String senderAccNo, String receiverAccNo, double amount) {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();

            Account sender = session.get(Account.class, senderAccNo);
            Account receiver = session.get(Account.class, receiverAccNo);

            if (sender == null || receiver == null) {
                System.out.println("Account not found.");
                if (tx != null) tx.rollback();
                return;
            }

            if (sender.getBalance() < amount) {
                System.out.println("Insufficient balance.");
                if (tx != null) tx.rollback();
                return;
            }

            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);

            session.merge(sender);
            session.merge(receiver);
            tx.commit();

            System.out.println("₹" + amount + " transferred successfully to "+receiver.getName());
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
