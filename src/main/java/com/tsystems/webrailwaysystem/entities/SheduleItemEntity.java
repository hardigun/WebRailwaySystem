package com.tsystems.webrailwaysystem.entities;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contract: describe shedule item in the system;
 *           contains the train that go
 *           contains the route which train follow
 *           contains list of tickets that sale
 *
 *           departureDate - not null and must be date in the future
 *           route - not null
 *           train - not null
 *
 * Date: 16.09.13
 */

@Entity
@Table(name = "shedule")
public class SheduleItemEntity extends AbstractEntity implements Cloneable {

    @Column(name="departure_date")
    @NotNull(message = "Departure date must be not null")
    @Future(message = "Departure date must be more than current date")
    private Date departureDate;

    @ManyToOne
    @JoinColumn(name = "route_id")
    @NotNull(message = "Route must be not null")
    private RouteEntity route;

    @ManyToOne
    @JoinColumn(name = "train_id")
    @NotNull(message = "Train must be not null")
    private TrainEntity train;

    @OneToMany(mappedBy = "sheduleItem", fetch = FetchType.EAGER)
    List<TicketEntity> ticketsList = new ArrayList<TicketEntity>();

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public RouteEntity getRoute() {
        return route;
    }

    public void setRoute(RouteEntity route) {
        this.route = route;
    }

    public TrainEntity getTrain() {
        return train;
    }

    public void setTrain(TrainEntity train) {
        this.train = train;
    }

    public List<TicketEntity> getTicketsList() {
        return ticketsList;
    }

    public void setTicketsList(List<TicketEntity> ticketsList) {
        this.ticketsList = ticketsList;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        SheduleItemEntity sheduleItem = (SheduleItemEntity) super.clone();
        sheduleItem.setId(this.getId());
        sheduleItem.setDepartureDate((Date) this.getDepartureDate().clone());
        sheduleItem.setRoute((RouteEntity) this.getRoute().clone());
        return sheduleItem;
    }

}
