package pl.wat.wcy.prz.i5b4s1.database.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.wat.wcy.prz.i5b4s1.database.dbutil.HibernateUtil;
import pl.wat.wcy.prz.i5b4s1.database.model.Lab;
import pl.wat.wcy.prz.i5b4s1.database.model.User;

import javax.naming.event.ObjectChangeListener;
import java.util.LinkedList;
import java.util.List;

public class LabDao {
    public void addLab(Lab lab){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.persist(lab);
        t.commit();
        session.close();
    }
    public void removeLabByNumber(int number){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        Lab lab = (Lab) session.get(Lab.class, number);
        session.delete(lab);
        t.commit();
        session.close();
    }

    public List<Lab> allLab(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        List<Lab> list = session.createQuery("from Lab").list();
        t.commit();
        session.close();
        return list;
    }
}
