package com.tsystems.webrailwaysystem.services;

import com.tsystems.webrailwaysystem.entities.UserEntity;
import com.tsystems.webrailwaysystem.enums.EUserRoles;
import com.tsystems.webrailwaysystem.exceptions.UserNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 19.10.13
 */
@Service("loginService")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW)
public class LoginService implements UserDetailsService {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    @Autowired
    private UserService userService;

    private List<GrantedAuthority> getUserRoles(UserEntity user) {
        List<GrantedAuthority> grantedList = new ArrayList<GrantedAuthority>();
        if(user.getUserRole() == EUserRoles.ADMIN) {
            grantedList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        if(user.getUserRole() == EUserRoles.MANAGER) {
            grantedList.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        }
        if(user.getUserRole() == EUserRoles.CLIENT) {
            grantedList.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
        }
        return grantedList;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity user;
        try {
            user = this.userService.getByLogin(login);
        } catch (UserNotFoundException exc) {
            LOGGER.debug(exc + "(Login: " + login + ")");
            throw new UsernameNotFoundException(exc.getMessage());
        }
        return new User(user.getLogin(), user.getUserPass(), true, true, true, true, this.getUserRoles(user));
    }

}
