package ru.kpfu.itis.kononenko.service;

import ru.kpfu.itis.kononenko.dao.UserDao;
import ru.kpfu.itis.kononenko.entity.User;
import ru.kpfu.itis.kononenko.util.Configuration;
import ru.kpfu.itis.kononenko.util.PasswordUtil;

import java.sql.Timestamp;

//TODO сейчас бзе него работаю, вообще не уверена, что этот клас мне нужен

public class UserService {
    private static final UserDao userDao = Configuration.getUserDao();

    public boolean register(String username,String email, String passwordHash, Timestamp createdAt) {
        return userDao.save(new User(
                null,
                username,
                email,
                PasswordUtil.encrypt(passwordHash),
                createdAt
        ));
    }

    public boolean checkSing(String login, String password) {
        User userBD = userDao.findByLogin(login);
        return userBD != null && userBD.passwordHash().equals(PasswordUtil.encrypt(password));
    }



}
