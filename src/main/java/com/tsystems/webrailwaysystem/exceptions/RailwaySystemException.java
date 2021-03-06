package com.tsystems.webrailwaysystem.exceptions;

/**
 * Contract: this exception and it's children can sends from server to client
 * and have message that can be displayed to client
 *
 * Date: 21.09.13
 */
public class RailwaySystemException extends Exception {

    protected String message;

    public RailwaySystemException() {
        super();
        this.message = "Error occurred during the server work";
    }

    public RailwaySystemException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return this.getMessage();
    }

}
