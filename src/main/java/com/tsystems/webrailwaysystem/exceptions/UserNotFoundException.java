package com.tsystems.webrailwaysystem.exceptions;

/**
 * Contracts: happens when in the system no users with same id OR name, surname and password
 *
 * Date: 21.09.13
 */
public class UserNotFoundException extends RailwaySystemException {

    public UserNotFoundException() {
        this.message = "User with same credentials not found";
    }

}
