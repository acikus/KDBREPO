package com.test.kdb.reports;

import java.io.Serializable;

import java.sql.Timestamp;

public class NumberOfDays{
    private String uniqueId;
    private String firstName;
    private String lastName;
    private Long counter;
    
    public NumberOfDays() {
        super();
    }

    public NumberOfDays(String uniqueId, String firstName, String lastName, Long counter) {
        super();
        this.uniqueId = uniqueId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.counter = counter;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Long getCounter() {
        return counter;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
    }
}
