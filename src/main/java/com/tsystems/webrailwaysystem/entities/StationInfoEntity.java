package com.tsystems.webrailwaysystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;


/**
 * Contract: describe station title in the system
 *           StationInfoEntity unlike StationEntity can be same for different route
 *
 *           title - size 3-255, unique
 *
 * Date: 17.09.13
 */

@Entity
@Table(name = "stations_info")
public class StationInfoEntity extends AbstractEntity implements Cloneable {

    @Column(unique = true)
    @Size(min = 3 , max = 255, message = "Station title size must be between 3 and 255")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.trim().toLowerCase();
    }

    @Override
    public boolean equals(Object obj) {
        if((obj == null) || !(obj instanceof StationInfoEntity)) {
            return false;
        }
        StationInfoEntity stationInfo = (StationInfoEntity) obj;
        if(!this.getTitle().equals(stationInfo.getTitle())) {
            return false;
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        StationInfoEntity stationInfo = (StationInfoEntity) super.clone();
        stationInfo.setId(this.getId());
        stationInfo.setTitle(this.getTitle());
        return stationInfo;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
