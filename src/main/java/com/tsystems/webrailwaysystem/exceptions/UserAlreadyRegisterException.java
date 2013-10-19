package com.tsystems.webrailwaysystem.exceptions;

/**
 * Contract: happens when in the system already exist user with same name and surname
 *
 * Date: 01.10.13
 */
public class UserAlreadyRegisterException extends RailwaySystemException {

    public UserAlreadyRegisterException() {
        this.message = "User with same login already register in the system";
    }

}
