package org.example.factory;

import org.hibernate.SessionFactory;

public interface HibernateSessionFactory {
    SessionFactory getSessionFactory();
}
