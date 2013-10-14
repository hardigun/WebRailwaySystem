package com.tsystems.webrailwaysystem.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contract: describe passenger in the system; contains tickets that buy this passenger
 *           name - 2 - 255 characters
 *           surname - 3 - 255 characters
 *           birthday - not null
 *
 * Date: 24.09.13
 */

@Entity
@Table(name = "passengers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "surname", "birthday"})
})
public class PassengerEntity extends AbstractEntity {

    @Size(min = 2, max = 255, message = "Name size must be between 2 and 255")
    private String name;

    @Size(min = 3, max = 255, message = "Surname size must be between 3 and 255")
    private String surname;

    @NotNull(message = "Birthday must be not null")
    private Date birthday;

    @OneToMany (mappedBy = "passenger")
    @Size
    List<TicketEntity> ticketList = new ArrayList<TicketEntity>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim().toLowerCase();
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname.trim().toLowerCase();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<TicketEntity> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<TicketEntity> ticketList) {
        this.ticketList = ticketList;
    }
}
