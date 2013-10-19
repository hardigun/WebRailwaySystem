package com.tsystems.webrailwaysystem.exceptions;

/**
 * Contract: happens when can't generate MD5 sum for string
 *
 * Date: 14.09.13
 */
public class EncryptionGenerationException extends RailwaySystemException {

    public EncryptionGenerationException() {
        this.message = "Can't generate MD5 sum for password";
    }
}
