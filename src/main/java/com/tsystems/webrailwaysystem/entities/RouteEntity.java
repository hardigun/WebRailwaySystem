package com.tsystems.webrailwaysystem.entities;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Contract: describe route in the system; contains stations list through which trains go
 *           and SheduleItems list in which this route used
 *
 *           routeNumber - unique
 *
 * Date: 16.09.13
 */

@Entity
@Table(name = "routes")
public class RouteEntity extends AbstractEntity implements Cloneable{

    @Column(name = "route_number", unique = true)
    @NotEmpty(message = "Route number must be not empty")
    private String routeNumber;

    @OneToMany(mappedBy = "route")
    @Size(min = 2, message = "Route should have minimum two stations")
    @JsonBackReference
    private List<StationEntity> stationsList = new ArrayList<StationEntity>();

    @OneToMany(mappedBy = "route")
    @JsonBackReference
    private List<SheduleItemEntity> sheduleList = new ArrayList<SheduleItemEntity>();

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber.trim().toLowerCase();
    }

    public List<StationEntity> getStationsList() {
        return stationsList;
    }

    public void setStationsList(List<StationEntity> stationsList) {
        this.stationsList = stationsList;
    }

    public List<SheduleItemEntity> getSheduleList() {
        return sheduleList;
    }

    public void setSheduleList(List<SheduleItemEntity> sheduleList) {
        this.sheduleList = sheduleList;
    }

    @Override
    public boolean equals(Object o) {
        if((o == null) || !(o instanceof RouteEntity)) {
            return false;
        }
        RouteEntity route = (RouteEntity) o;
        if(!this.getRouteNumber().equals(route.getRouteNumber())) {
            return false;
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        RouteEntity route = (RouteEntity) super.clone();
        route.setId(this.getId());
        route.setRouteNumber(this.getRouteNumber());
        List<StationEntity> stationsList = new ArrayList<StationEntity>();
        for(StationEntity station : this.getStationsList()) {
            stationsList.add((StationEntity) station.clone());
        }
        route.setStationsList(stationsList);
        return route;
    }

    @Override
    public String toString() {
        return this.getRouteNumber();
    }

}
