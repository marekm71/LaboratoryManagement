package pl.wat.wcy.prz.i5b4s1.database.service;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.wat.wcy.prz.i5b4s1.database.dao.EquipmentDao;
import pl.wat.wcy.prz.i5b4s1.database.model.Equipment;

public class EquipmentService {
    private static EquipmentDao equipmentDao;

    public EquipmentService() {
        equipmentDao = new EquipmentDao();
    }

    public Service removeEquipment(int numberEquipment) {
        return new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        equipmentDao.removeEquipment(numberEquipment);
                        return null;
                    }
                };
            }
        };
    }

    public Service updateEquipment(Equipment equipment) {
        return new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        equipmentDao.updateEquipment(equipment);
                        return null;
                    }
                };
            }
        };
    }

    public Service removeEquipmentsFromLab(int numberLab){
        return new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        equipmentDao.removeEquipmentsFromLab(numberLab);
                        return null;
                    }
                };
            }
        };
    }
    public Service addEquipment(int numberLab, String name, String serialNumber){
        return new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                      equipmentDao.addEquipment(numberLab,name,serialNumber);
                        return null;
                    }
                };
            }
        };
    }

    public Service<ObservableList<Equipment>> getListEquipmentByNumberLab(int number){
        return new Service<ObservableList<Equipment>>() {
            @Override
            protected Task<ObservableList<Equipment>> createTask() {
                return new Task<ObservableList<Equipment>>() {
                    @Override protected ObservableList<Equipment> call() throws Exception {
                       return equipmentDao.getListEquipmentByNumberLab(number);
                    }
                };
            }
        };
    }
}
