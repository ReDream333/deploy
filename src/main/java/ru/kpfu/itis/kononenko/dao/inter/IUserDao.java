package ru.kpfu.itis.kononenko.dao.inter;

import ru.kpfu.itis.kononenko.entity.User;

public interface IUserDao extends ICrud<User>{
    void updateUserName(String newName, Long userId);
    void updateUserPassword(String newPassword, Long userId);
    User findByLogin(String login);
    boolean deleteByLogin(String username);
    void updateUserPhoto(long userId, String photoUrl);
    User findByLoginAndPassword(String login, String hashPassword);
}
