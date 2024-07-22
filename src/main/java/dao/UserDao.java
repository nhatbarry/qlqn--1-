package dao;

import entity.User;

public class UserDao {
    public boolean checkUser(User user) {
        return user != null && "admin".equals(user.getUserName()) && "admin".equals(user.getPassword());
    }
}
