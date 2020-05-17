package com.example.paytoday.dao;


import com.example.paytoday.model.User;


import java.util.List;


public interface UserDAO extends BaseDAO<User> {

    User getUserforLogin(User user);

    User getUserbyName(String username);

    User getUserbyEmail(String email);



}
