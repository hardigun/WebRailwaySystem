package com.tsystems.webrailwaysystem.utils;

import com.tsystems.webrailwaysystem.entities.UserEntity;
import com.tsystems.webrailwaysystem.enums.EUserRoles;
import com.tsystems.webrailwaysystem.exceptions.EncryptionGenerationException;
import com.tsystems.webrailwaysystem.exceptions.UserAlreadyRegisterException;
import com.tsystems.webrailwaysystem.exceptions.UserNotFoundException;
import com.tsystems.webrailwaysystem.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Date: 19.10.13
 */
@Component
public class ApplicationStartEventHandler implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger LOGGER = Logger.getLogger("webrailwaysystem");

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        UserEntity user = null;
        try {
            user = this.userService.getByLogin("admin");
        } catch (UserNotFoundException e) {

        }
        if(user != null) {
            return;
        }
        user = new UserEntity();
        user.setLogin("admin");
        user.setName("admin");
        user.setSurname("admin");
        user.setUserPass("69pn3z5v");
        user.setUserRole(EUserRoles.ADMIN);
        user.setBirthday(new Date());
        user.setRegDate(new Date());

        try {
            this.userService.addUser(user);
        } catch (Exception exc) {
            exc.printStackTrace();
            LOGGER.warn(exc);
        }
    }

}
