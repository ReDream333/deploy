package ru.kpfu.itis.kononenko;

import ru.kpfu.itis.kononenko.dao.UserDao;
import ru.kpfu.itis.kononenko.entity.User;
import ru.kpfu.itis.kononenko.util.Configuration;

public class Main {
    public static void main(String[] args) {
        UserDao u = Configuration.getUserDao();
//        System.out.println(u.save(new User(null, "m", "m", "m")));
    }
}
