package pl.wat.wcy.prz.i5b4s1.database.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.wat.wcy.prz.i5b4s1.database.dbutil.HibernateUtil;
import pl.wat.wcy.prz.i5b4s1.database.model.Equipment;
import pl.wat.wcy.prz.i5b4s1.database.model.Lab;

import java.util.LinkedList;
import java.util.Set;

public class EquipmentDao {
    public void removeEquipment(int numberEquipment) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        Equipment equipment = (Equipment) session.get(Equipment.class, numberEquipment);
        session.delete(equipment);
        t.commit();
        session.close();
    }

    public void updateEquipment(Equipment equipment) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.update(equipment);
        t.commit();
        session.close();
    }

    public void removeEquipmentsFromLab(int numberLab){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        Lab lab = (Lab) session.get(Lab.class, numberLab);
        Set<Equipment> list = lab.getEquipment();
        for (Equipment x : list) {
            session.delete(x);
        }
        t.commit();
        session.close();
    }
    public void addEquipment(int numberLab, String name, String serialNumber){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        Lab lab = (Lab) session.get(Lab.class, numberLab);
        Set<Equipment> list = lab.getEquipment();
        Equipment equipment = new Equipment();
        equipment.setName(name);
        equipment.setSerialNumber(serialNumber);
        equipment.setLab(lab);
        list.add(equipment);
        session.persist(lab);
        t.commit();
        session.close();
    }

    public ObservableList<Equipment> getListEquipmentByNumberLab(int number){
        ObservableList<Equipment> observableList = FXCollections.observableList(new LinkedList<>());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        Lab lab = (Lab) session.get(Lab.class, number);
        Set<Equipment> list = lab.getEquipment();
        for (Equipment x : list) {
            Equipment equipment = new Equipment();
            equipment.setId(x.getId());
            equipment.setLab(x.getLab());
            equipment.setName(x.getName());
            equipment.setSerialNumber(x.getSerialNumber());
            observableList.add(equipment);
        }
        t.commit();
        session.close();
        return observableList;
    }
}
