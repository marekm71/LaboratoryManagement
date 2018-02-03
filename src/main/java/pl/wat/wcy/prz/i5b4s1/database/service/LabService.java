package pl.wat.wcy.prz.i5b4s1.database.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.wat.wcy.prz.i5b4s1.database.dao.LabDao;
import pl.wat.wcy.prz.i5b4s1.database.model.Lab;

import java.util.List;

public class LabService {
    private static LabDao labDao;

    public LabService() {
        labDao = new LabDao();
    }

    public Service addLab(Lab lab){
        return new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        labDao.addLab(lab);
                        return null;
                    }
                };
            }
        };
    }

    public Service removeLabByNumber(int number){
        return new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        labDao.removeLabByNumber(number);
                        return null;
                    }
                };
            }
        };
    }

    public Service<List<Lab>> allLab(){
        return new Service<List<Lab>>() {
            @Override
            protected Task<List<Lab>> createTask() {
                return new Task<List<Lab>>() {
                    @Override protected List<Lab> call() throws Exception {
                        return labDao.allLab();
                    }
                };
            }
        };
    }
}
