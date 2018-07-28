package com.epam.server.service;


import com.epam.server.dao.UserDaoImpl;
import com.epam.server.model.User;

import java.util.List;

public class UserService {

    public static final UserDaoImpl userDao = new UserDaoImpl();

    public static User getUserById(String input) {
        try {
            int id = Integer.parseInt(input);
            return userDao.getUserById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static boolean addUser(User user) {
        return userDao.addUser(user);
    }

    public static boolean checkForUser(String email, String password){
        return userDao.checkForEmailAndPassword(email,password);
    }

}
