package com.tsystems.webrailwaysystem.services;

import com.tsystems.webrailwaysystem.dao.UserDAO;
import com.tsystems.webrailwaysystem.entities.UserEntity;
import com.tsystems.webrailwaysystem.enums.EUserRoles;
import com.tsystems.webrailwaysystem.exceptions.EncryptionGenerationException;
import com.tsystems.webrailwaysystem.exceptions.RailwaySystemException;
import com.tsystems.webrailwaysystem.exceptions.UserAlreadyRegisterException;
import com.tsystems.webrailwaysystem.exceptions.UserNotFoundException;
import com.tsystems.webrailwaysystem.utils.ApplicationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contract: service make operations with UserEntity
 *
 * Date: 21.09.13
 */
@Service("userService")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW)
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Transactional(readOnly = true)
    public List<UserEntity> getAll() {
        return this.userDAO.getAll(true);
    }

    /**
     * Contract: check if in the database exist user with same login and return it
     *
     * @param login
     * @return user with all credentials
     * @throws UserNotFoundException if user with same login not found
     */
    @Transactional(readOnly = true)
    public UserEntity getByLogin(String login) throws UserNotFoundException {
        UserEntity user = this.userDAO.getByLogin(login);
        if(user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }


    /**
     * Contract: save user in the database
     *
     * @param user that need to save
     * @throws UserAlreadyRegisterException if in the database already exist user with same name and surname
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void addUser(UserEntity user) throws UserAlreadyRegisterException, EncryptionGenerationException {
        UserEntity existSameUser = this.userDAO.getByLogin(user.getLogin());
        if(existSameUser != null) {
            throw new UserAlreadyRegisterException();
        }
        user.setUserPass(ApplicationUtil.getMD5(user.getUserPass()));
        user.setRegDate(new Date());
        this.userDAO.save(user);
    }

    @Transactional(readOnly = true)
    public UserEntity getUser(int id) {
        return this.userDAO.getById(UserEntity.class, id);
    }

    /**
     * Contract: update user in the database
     *
     * @param user that need to update
     * @throws UserAlreadyRegisterException if in database exist user with same name and surname and it's not
     * current user that execute updating
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void updateUser(UserEntity user) throws UserAlreadyRegisterException, EncryptionGenerationException {
        UserEntity existSameUser = this.userDAO.getByLogin(user.getLogin());
        if(existSameUser != null && existSameUser.getId() != user.getId()) {
            throw new UserAlreadyRegisterException();
        }
        user.setUserPass(ApplicationUtil.getMD5(user.getUserPass()));
        this.userDAO.update(user);
    }

    /**
     * Contract: remove user from the database
     *
     * @param user that need to delete
     */
    public int removeUser(UserEntity user) {
        return this.userDAO.remove(user);
    }

}
