package pl.wat.wcy.prz.i5b4s1.database.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.wat.wcy.prz.i5b4s1.database.dao.UserDao;
import pl.wat.wcy.prz.i5b4s1.database.model.User;

import java.util.List;

public class UserService {
    private static UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public Service addUser(User user){
        return new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        userDao.addUser(user);
                        return null;
                    }
                };
            }
        };
    }

    public Service updateUser(User user){
        return new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        userDao.updateUser(user);
                        return null;
                    }
                };
            }
        };
    }

    public Service<List<User>> allUser(){
        return new Service<List<User>>() {
            @Override
            protected Task<List<User>> createTask() {
                return new Task<List<User>>() {
                    @Override protected List<User> call() throws Exception {
                       return userDao.allUser();
                    }
                };
            }
        };
    }

    public Service<User> getUserByLogin(String login){
        return new Service<User>() {
            @Override
            protected Task<User> createTask() {
                return new Task<User>() {
                    @Override protected User call() throws Exception {
                        return userDao.getUserByLogin(login);
                    }
                };
            }
        };
    }

}
