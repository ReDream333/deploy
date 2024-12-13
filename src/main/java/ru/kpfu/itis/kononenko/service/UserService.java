package ru.kpfu.itis.kononenko.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.kononenko.dao.UserDao;
import ru.kpfu.itis.kononenko.entity.User;
import ru.kpfu.itis.kononenko.util.Configuration;
import ru.kpfu.itis.kononenko.util.PasswordUtil;

import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;

public class UserService {
    private static final UserDao userDao = Configuration.getUserDao();
    private static final Logger LOG =
            LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public User register(String username,String email, String passwordHash, Timestamp createdAt) {
        User user = new User(
                null,
                username,
                email,
                PasswordUtil.encrypt(passwordHash),
                createdAt,
                null
        );
        long id = userDao.save(user);
        return userDao.findById(id);
    }

    public User checkSign(String login, String password) {
        //TODO либо имена у всех пользователей одинаковые либо надо findByLogin&&Password

        User userBD = userDao.findByLogin(login);

        if(userBD != null && userBD.passwordHash().equals(PasswordUtil.encrypt(password))){
            LOG.info(userBD.toString());
            return userBD;
        }else {
            LOG.info("UserBD is null or password does not match");

            return null;
        }
    }

    public User changeName(String newName, String tempName) {
        userDao.updateUser(newName, tempName);
        return userDao.findByLogin(newName);
    }


//TODO доделать
    public User photoUpload(long userId, String photoUrl) {
        userDao.updateUserPhoto(userId, photoUrl);
        return userDao.findById(userId);
    }





}
