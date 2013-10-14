package com.tsystems.webrailwaysystem.entities;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Contract: describe trains in the system; contains SheduleItems list in which this train used
 *
 *           trainNumber - unique, notEmpty
 *           capacity - from 0 to 1000
 *
 * Date: 10.09.13
 */

@Entity
@Table (name = "trains")
public class TrainEntity extends AbstractEntity {

    @Column(name = "train_number", unique = true)
    @NotEmpty(message = "Train number must be not empty")
    private String trainNumber;

    @Range(min = 1, max = 1000, message = "Train capacity must be in range from 1 to 1000")
    private int capacity;
    @OneToMany(mappedBy = "train")
    private List<SheduleItemEntity> sheduleList = new ArrayList<SheduleItemEntity>();

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber.trim().toLowerCase();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<SheduleItemEntity> getSheduleList() {
        return sheduleList;
    }

    public void setSheduleList(List<SheduleItemEntity> sheduleList) {
        this.sheduleList = sheduleList;
    }

    @Override
    public boolean equals(Object o) {
        if((o == null) || !(o instanceof TrainEntity)) {
            return false;
        }
        TrainEntity train = (TrainEntity) o;
        if(!this.getTrainNumber().equals(train.getTrainNumber())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("#").append(this.getTrainNumber());
        strBuf.append(" (").append(this.getCapacity()).append(" pass.)");
        return strBuf.toString();
    }

}
