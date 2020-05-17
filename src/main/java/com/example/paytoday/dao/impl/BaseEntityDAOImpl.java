package com.example.paytoday.dao.impl;

import com.example.paytoday.dao.BaseDAO;
import com.example.paytoday.model.BaseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class BaseEntityDAOImpl<T extends BaseEntity> implements BaseDAO<T> {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return sessionFactory.getCurrentSession();

    }


    @Override
    public T getById(T obj, Long id) {
        Session session = sessionFactory.getCurrentSession();
        return (T) session.get(obj.getClass(),id);
    }

    @Override
    public Long create(T obj) {
        Session session = sessionFactory.getCurrentSession();
        Long i= (Long) session.save(obj);
        session.flush();
        return i;


    }

    @Override
    public void update(T obj) {

    }

    @Override
    public void delete(T obj) {

    }




}
