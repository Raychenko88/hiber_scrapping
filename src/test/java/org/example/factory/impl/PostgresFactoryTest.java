package org.example.factory.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostgresFactoryTest {

    @Test
    void getSessionFactory() {
        SessionFactory sessionFactory = new PostgresFactory().getSessionFactory();
        assertNotNull(sessionFactory);

        Session session = sessionFactory.openSession();

        assertNotNull(session);

        session.close();
    }
}