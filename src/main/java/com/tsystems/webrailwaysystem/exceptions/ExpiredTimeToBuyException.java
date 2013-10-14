package com.tsystems.webrailwaysystem.exceptions;

/**
 * Contract: happens when date and time of departure minus 10 minutes is before current date and time
 *
 * Date: 25.09.13
 */
public class ExpiredTimeToBuyException extends RailwaySystemException {

    public ExpiredTimeToBuyException() {
        this.message = "Expired time to buy ticket on this train";
    }

}
