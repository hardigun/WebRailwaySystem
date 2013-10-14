package com.tsystems.webrailwaysystem.exceptions;

/**
 * Contract: happens when on the train for selected SheduleItem no available free seats
 *
 * Date: 25.09.13
 */
public class NoAvailableSeatsException extends RailwaySystemException {

    public NoAvailableSeatsException() {
        this.message = "No available seats on this train";
    }

}
