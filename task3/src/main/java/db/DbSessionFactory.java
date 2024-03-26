package db;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;

import java.sql.Connection;
import java.util.function.Function;

public class DbSessionFactory {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";

    private static SessionFactory sessionFactory;

    private DbSessionFactory() {
    }

    public static SessionFactory getInstance() {
        if (sessionFactory == null) {
            Configuration configuration = getH2Configuration();
            sessionFactory = createSessionFactory(configuration);
        }

        return sessionFactory;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(registry);
    }

    private static Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "test");
        configuration.setProperty("hibernate.connection.password", "test");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

}
