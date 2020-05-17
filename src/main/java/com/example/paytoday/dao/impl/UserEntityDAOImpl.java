package com.example.paytoday.dao.impl;


import com.example.paytoday.dao.UserDAO;
import com.example.paytoday.model.User;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserEntityDAOImpl extends BaseEntityDAOImpl<User> implements UserDAO {

    public UserEntityDAOImpl() { }


    @Override
    public User getUserforLogin(User user) {
         return (User) getSession().createCriteria(User.class)
                .add(Restrictions.eq("email", user.getEmail()))
                .add(Restrictions.eq("password",user.getPassword()))
                .uniqueResult();


    }

    @Override
    public User getUserbyName(String username) {
        return (User) getSession().createCriteria(User.class)
                .add(Restrictions.eq("name", username))
                .uniqueResult();
    }

    @Override
    public User getUserbyEmail(String email) {
        return (User) getSession().createCriteria(User.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }





}
