package org.example.dao;

import org.example.factory.impl.PostgresFactory;
import org.example.model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ItemDAO {

    public final SessionFactory sessionFactory;

    public ItemDAO(){

        sessionFactory = new PostgresFactory().getSessionFactory();
    }

    public Item save(Item entity) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Integer id = (Integer) session.save(entity);
        entity.setItemId(id.toString());
        session.getTransaction().commit();
        session.close();

        return entity;
    }

    public Item update(Item entity) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
        return entity;
    }

    public Item findById(Integer id) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Item entity = session.find(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return entity;
    }

    public void delete(Item entity) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }
}
