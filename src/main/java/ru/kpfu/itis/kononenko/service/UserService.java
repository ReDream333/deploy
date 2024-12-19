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
        if (isLoginTaken(username)&& isEmailTaken(email)) {
            throw new IllegalArgumentException("Пользователь c таким именем и почтой уже существует");
        } else if (isLoginTaken(username)) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }else if (isEmailTaken(email)) {
            throw new IllegalArgumentException("Пользователь с такой почтой уже существует");
        }

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

    private boolean isEmailTaken(String email) {
        return userDao.findByEmail(email) != null;
    }

    private boolean isLoginTaken(String username) {
        return userDao.findByLogin(username) != null;
    }

    public User checkSign(String login, String password) {
        User userBD = userDao.findByLoginAndPassword(login, PasswordUtil.encrypt(password));

        if(userBD != null){
            LOG.info(userBD.toString());
            return userBD;
        }else {
            LOG.info("UserBD is null or password does not match");
            return null;
        }
    }

    public User changeName(String newName, Long userId) {
        userDao.updateUserName(newName, userId);
        return userDao.findById(userId);
    }


//TODO доделать
    public User photoUpload(long userId, String photoUrl) {
        userDao.updateUserPhoto(userId, photoUrl);
        return userDao.findById(userId);
    }


    public boolean deleteUser(Long id) {
        return userDao.deleteById(id);
    }

    public boolean validatePassword(User user, String currentPassword) {
        return user.passwordHash().equals(PasswordUtil.encrypt(currentPassword));
    }

    public User changePassword(String newPassword, Long userId) {
        userDao.updateUserPassword(PasswordUtil.encrypt(newPassword), userId);
        return userDao.findById(userId);
    }
}
