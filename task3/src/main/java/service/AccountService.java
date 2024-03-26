package service;

import db.DbSessionFactory;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;

public class AccountService {
    private SessionFactory sessionFactory;

    public AccountService() {
        this.sessionFactory = DbSessionFactory.getInstance();
    }

    public long addUser(String login, String password) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        long id = (Long) session.save(new User(login, password));
        session.close();

        return id;
    }

    public User getUser(long id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        session.close();

        return user;
    }

    public User findUserByLogin(String login) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from User where login=:login");
        query.setParameter("login", login);
        User user = (User) query.getSingleResult();
        session.close();

        return user;
    }

}
