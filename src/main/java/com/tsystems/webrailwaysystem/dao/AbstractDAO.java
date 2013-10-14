package com.tsystems.webrailwaysystem.dao;

import com.tsystems.webrailwaysystem.entities.AbstractEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Contract: DAO execute common operations with database for all classes extends AbstractEntity
 *
 * Date: 20.09.13
 */

@Repository("abstractDAO")
public abstract class AbstractDAO<T extends AbstractEntity> {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(T obj) {
        this.sessionFactory.getCurrentSession().save(obj);
    }

    public Object getById(Class<T> tClass, int id) {
        return this.sessionFactory.getCurrentSession().get(tClass, id);
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
