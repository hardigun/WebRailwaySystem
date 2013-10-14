package com.tsystems.webrailwaysystem.filters;

import com.tsystems.webrailwaysystem.entities.StationInfoEntity;

import java.util.Date;

/**
 * Date: 13.10.13
 */
public class StationsFilter {

    private StationInfoEntity stationInfoFrom;
    private StationInfoEntity stationInfoTo;
    private Date startDate;
    private Date endDate;

    public StationInfoEntity getStationInfoFrom() {
        return stationInfoFrom;
    }

    public void setStationInfoFrom(StationInfoEntity stationInfoFrom) {
        this.stationInfoFrom = stationInfoFrom;
    }

    public StationInfoEntity getStationInfoTo() {
        return stationInfoTo;
    }

    public void setStationInfoTo(StationInfoEntity stationInfoTo) {
        this.stationInfoTo = stationInfoTo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
