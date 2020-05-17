package com.example.paytoday.dao;


import com.example.paytoday.model.BaseEntity;


public interface BaseDAO<T extends BaseEntity> {

       T getById(T t, Long id);

       Long create(T obj);

      void update(T obj);

      void delete(T obj);





}
