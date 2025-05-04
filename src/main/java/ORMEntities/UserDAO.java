package ORMEntities;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDAO {
    SessionFactory sessionFactory = MySessionFactory.getMySessionFactory();
    public User findByLogin(String login) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, login);
        session.close();
        return user;
    }

    public void registerNewUser(User user){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(user); // save() устарел.
        transaction.commit();
        session.close();
    }
}
