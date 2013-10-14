package com.tsystems.webrailwaysystem.entities;

import com.tsystems.webrailwaysystem.enums.EUserRoles;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Contract: describe users in the system
 *
 *           name, surname - bundle of unique fields
 *           name - 2-255 charactes
 *           surname - 3-255 charactes
 *           userPass and userRole - not null
 *
 * Date: 14.09.13
 */

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "surname"})
})
public class UserEntity extends AbstractEntity implements Comparable<UserEntity> {

    @Size(min = 2, max = 255, message = "Name size must be between 2 and 255")
    private String name;

    @Size(min = 3, max = 255, message = "Surname size must be between 3 and 255")
    private String surname;

    @NotNull(message = "User password must be not null")
    @Column(name = "user_pass")
    private String userPass;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "User role must be not null")
    @Column(name = "user_role")
    private EUserRoles userRole;

    @Column(name="reg_date")
    private Date regDate;

    public UserEntity() {

    }

    public UserEntity(int id, String name, String surname, EUserRoles userRole, Date regDate) {
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setUserRole(userRole);
        this.setRegDate(regDate);
    }

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

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public EUserRoles getUserRole() {
        return userRole;
    }

    public void setUserRole(EUserRoles userRole) {
        this.userRole = userRole;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof UserEntity)) {
            return false;
        }
        UserEntity user = (UserEntity) o;
        if(!this.getName().equals(user.getName()) || !this.getSurname().equals(user.getSurname())) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(UserEntity userEntity) {
        if(this.id == userEntity.id) {
            return 0;
        }
        if(this.id > userEntity.id) {
            return 1;
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(this.surname).append(" ").append(this.name).append(" (").append(this.userRole).append(")");
        return strBuf.toString();
    }

}
