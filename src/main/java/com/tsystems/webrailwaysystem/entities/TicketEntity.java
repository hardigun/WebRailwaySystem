package com.tsystems.webrailwaysystem.entities;

/**
 * Contract: describe ticket in the system;
 *           contains SheduleItem for which this ticket buy
 *           contains passenger that buy this ticket
 *           contains saleConfirmed flag that sale is approved(by default FALSE)
 *
 *           sheduleItem - not null
 *           passenger - not null
 * Date: 24.09.13
 */

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;

@Entity
@Table (name = "tickets")
public class TicketEntity extends AbstractEntity {

    @Column (name = "sale_confirmed")
    private boolean saleConfirmed = false;

    @ManyToOne
    @JoinColumn (name = "shedule_id")
    @NotNull(message = "Shedule item must be not null")
    @JsonIgnore
    private SheduleItemEntity sheduleItem;

    @ManyToOne
    @JoinColumn (name = "passenger_id")
    @NotNull(message = "Passenger must be not null")
    @JsonManagedReference
    private PassengerEntity passenger;

    public boolean isSaleConfirmed() {
        return saleConfirmed;
    }

    public void setSaleConfirmed(boolean saleConfirmed) {
        this.saleConfirmed = saleConfirmed;
    }

    public SheduleItemEntity getSheduleItem() {
        return sheduleItem;
    }

    public void setSheduleItem(SheduleItemEntity sheduleItem) {
        this.sheduleItem = sheduleItem;
    }

    public PassengerEntity getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerEntity passenger) {
        this.passenger = passenger;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        StringBuffer ticketBuf = new StringBuffer();
        ticketBuf.append("Ticket number: #").append(this.getId());
        if(this.getSheduleItem() != null) {
            ticketBuf.append(" Train: ").append(this.getSheduleItem().getTrain().getTrainNumber());
            ticketBuf.append(" Departure date: ").append(simpleDateFormat.format(
                    this.getSheduleItem().getDepartureDate()
            ));
        }
        return ticketBuf.toString();
    }
}
