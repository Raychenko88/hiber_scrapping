package org.example.dao;

import org.example.factory.impl.PostgresFactory;
import org.example.model.ItemModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.ParameterizedType;

public class ItemDAO {

    private Class<ItemModel> type;
    public final SessionFactory sessionFactory;

    public ItemDAO(){

        sessionFactory = new PostgresFactory().getSessionFactory();
    }

    public ItemModel save(ItemModel entity) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        Integer id = (Integer) session.save(entity);
        entity.setId(id);
        session.getTransaction().commit();
        session.close();

        return entity;
    }

    public ItemModel update(ItemModel entity) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
        return entity;
    }

    public ItemModel findById(Integer id) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        ItemModel entity = session.find(type, id);
        session.getTransaction().commit();
        session.close();
        return entity;
    }

    public void delete(ItemModel entity) {
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }
}
