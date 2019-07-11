package pl.bartekk.revoluttask.service;

import pl.bartekk.revoluttask.model.User;
import pl.bartekk.revoluttask.repository.UserDao;

import java.util.List;

public class UserService {

    private UserDao userDao = UserDao.getInstance();
    private static UserService instance;

    private UserService() {
    }

    //create an singleton object
    public static UserService getInstance() {
        return instance == null ? instance = new UserService() : instance;
    }

    public boolean createNewUser(String name) {
        return userDao.insertUser(new User(name));
    }

    public User getUser(String name) {
        return userDao.getUser(name);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public boolean removeUser(String name) {
        userDao.removeUser(name);
        return true;
    }
}
