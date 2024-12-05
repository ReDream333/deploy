package ru.kpfu.itis.kononenko.service;

import ru.kpfu.itis.kononenko.dao.UserDao;
import ru.kpfu.itis.kononenko.entity.User;
import ru.kpfu.itis.kononenko.util.Configuration;
import ru.kpfu.itis.kononenko.util.PasswordUtil;

import java.sql.Timestamp;

public class UserService {
    private static final UserDao userDao = Configuration.getUserDao();

    public User register(String username,String email, String passwordHash, Timestamp createdAt) {
        User user = new User(
                null,
                username,
                email,
                PasswordUtil.encrypt(passwordHash),
                createdAt
        );
        long id = userDao.save(user);
        return userDao.findById(id);
    }

    public User checkSign(String login, String password) {
        //TODO либо имена у всех пользователей одинаковые либо надо findByLogin&&Password
        User userBD = userDao.findByLogin(login);
        if(userBD != null && userBD.passwordHash().equals(PasswordUtil.encrypt(password))){
            return userBD;
        }else {
            return null;
        }
    }

    public User changeName(String newName, String tempName) {
        userDao.updateUser(newName, tempName);
        return userDao.findByLogin(newName);
    }



}
