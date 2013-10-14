package com.tsystems.webrailwaysystem.filters;

import com.tsystems.webrailwaysystem.entities.StationInfoEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contract: used for send station search params on shedule page
 *
 * Date: 22.09.13
 */
public class SheduleFilter {

    private StationInfoEntity stationInfo;
    private Date date;

    public StationInfoEntity getStationInfo() {
        return stationInfo;
    }

    public void setStationInfo(StationInfoEntity stationInfo) {
        this.stationInfo = stationInfo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
