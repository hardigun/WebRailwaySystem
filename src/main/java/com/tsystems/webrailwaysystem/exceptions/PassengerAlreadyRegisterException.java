package com.tsystems.webrailwaysystem.exceptions;

/**
 * Contract: happens when in the system on the selected SheduleItem
 * already register passenger with same name, surname and birthday
 *
 * Date: 24.09.13
 */
public class PassengerAlreadyRegisterException extends RailwaySystemException {

    public PassengerAlreadyRegisterException() {
        this.message = "Passenger with same credentials already register to this train";
    }
}
