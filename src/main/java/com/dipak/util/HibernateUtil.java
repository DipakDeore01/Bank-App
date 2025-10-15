package com.dipak.util;

import com.dipak.entity.Users;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory factory;

    static{
        try {
            Configuration config = new Configuration();
            config.addAnnotatedClass(Users.class);
            config.configure();

            factory = config.buildSessionFactory();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getFactory() {
        return factory;
    }
}
