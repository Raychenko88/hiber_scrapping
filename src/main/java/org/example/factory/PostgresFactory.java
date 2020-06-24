package org.example.factory;

import org.example.model.Item;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class PostgresFactory {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties properties = new Properties();
            properties.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            properties.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/hiber_scrapping");
            properties.setProperty("hibernate.connection.username", "postgres");
            properties.setProperty("hibernate.connection.password", "123456");
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");
            properties.setProperty("hibernate.hbm2ddl.auto", "update");
            properties.setProperty("hibernate.show_sql", "true");
            properties.setProperty("hibernate.format_sql", "true");
            configuration.addProperties(properties);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
                    applySettings(configuration.getProperties()).build();

            configuration.addAnnotatedClass(Item.class);

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}
