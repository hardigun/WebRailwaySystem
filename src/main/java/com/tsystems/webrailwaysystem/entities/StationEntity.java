package com.tsystems.webrailwaysystem.entities;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Contract: describe station in the system;
 *           it is concrete station which train follow at concrete time for this route
 *           contains route that include station
 *           contains stationInfo that describe title of the station
 *
 *           timeOffset - can't be negative
 *           route - not null
 *           stationInfo - not null
 *
 * Date: 10.09.13
 */

@Entity
@Table(name = "stations")
public class StationEntity extends AbstractEntity implements Cloneable {
    @Min(value = 0, message = "Time offset must be positive numeric")
    private int timeOffset;

    @ManyToOne
    @JoinColumn(name = "route_id")
    @NotNull(message = "Route must be not null")
    private RouteEntity route;

    @ManyToOne
    @JoinColumn(name = "stations_info_id")
    @NotNull(message = "Station title must be not null")
    private StationInfoEntity stationInfo;

    public int getTimeOffset() {
        return timeOffset;
    }

    public void setTimeOffset(int timeOffset) {
        this.timeOffset = timeOffset;
    }

    public RouteEntity getRoute() {
        return route;
    }

    public void setRoute(RouteEntity route) {
        this.route = route;
    }

    public StationInfoEntity getStationInfo() {
        return stationInfo;
    }

    public void setStationInfo(StationInfoEntity stationInfo) {
        this.stationInfo = stationInfo;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        StationEntity station = (StationEntity) super.clone();
        station.setId(this.getId());
        station.setTimeOffset(this.getTimeOffset());
        station.setStationInfo((StationInfoEntity) this.getStationInfo().clone());
        return station;
    }
}
