package pl.wat.wcy.prz.i5b4s1.database.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.wat.wcy.prz.i5b4s1.database.dbutil.HibernateUtil;
import pl.wat.wcy.prz.i5b4s1.database.model.User;

import java.util.LinkedList;
import java.util.List;

public class UserDao {

    public void addUser(User user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.persist(user);
        t.commit();
        session.close();
    }

    public void updateUser(User user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.update(user);
        t.commit();
        session.close();
    }

    public List<User> allUser(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        List<User> list = session.createQuery("from User").list();
        t.commit();
        session.close();
        return list;
    }

    public User getUserByLogin(String login){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        List<User> list = session.createQuery("from User").list();
        for(User c : list){
            if(c.getLogin().equals(login)) return c;
        }
        t.commit();
        session.close();
        return null;
    }
}