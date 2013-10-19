package com.tsystems.webrailwaysystem.dao;

import com.tsystems.webrailwaysystem.entities.UserEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contract: DAO execute operation with database for UserEntity
 *
 * Date: 14.09.13
 */
@Repository("userDAO")
public class UserDAO extends AbstractDAO<UserEntity> {

    /**
     * Contract: get all users
     *
     * @param exceptAdmin if true will be except user with role ADMIN
     * @return
     */
    public List<UserEntity> getAll(boolean exceptAdmin) {
        StringBuffer queryStr = new StringBuffer(
                "SELECT NEW UserEntity(id, login, name, surname, userRole, regDate)" +
                " FROM UserEntity"
        );
        if(exceptAdmin) {
            queryStr.append(" WHERE userRole <> 'ADMIN'");
        }
        queryStr.append(" ORDER BY surname ASC, name ASC");
        return this.getSessionFactory().getCurrentSession().createQuery(queryStr.toString()).list();
    }

    public UserEntity getByLogin(String login) {
        String queryStr = "FROM UserEntity" +
                          " WHERE login = :login";
        Query query = this.getSessionFactory().getCurrentSession().createQuery(queryStr.toString())
                .setParameter("login", login);
        List<UserEntity> userList = query.list();
        if(userList.size() == 1) {
            return userList.get(0);
        }
        return null;
    }

    public int update(UserEntity user) {
        String queryStr = "UPDATE UserEntity" +
                " SET login = :login" +
                " , name = :name" +
                " , surname = :surname" +
                " , userPass = :userPass" +
                " , userRole = :userRole" +
                " WHERE id = :id";
        Query query = this.getSessionFactory().getCurrentSession().createQuery(queryStr);
        query.setParameter("id", user.getId())
             .setParameter("login", user.getLogin())
             .setParameter("name", user.getName())
             .setParameter("surname", user.getSurname())
             .setParameter("userPass", user.getUserPass())
             .setParameter("userRole", user.getUserRole())
             .setParameter("id", user.getId());
        return query.executeUpdate();
    }

    public int remove(UserEntity user) {
        String queryStr = "DELETE FROM UserEntity WHERE id = :id";
        Query query = this.getSessionFactory().getCurrentSession().createQuery(queryStr);
        query.setParameter("id", user.getId());
        return query.executeUpdate();
    }

}
