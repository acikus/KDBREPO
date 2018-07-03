package com.test.kdb.reports;

import java.sql.Timestamp;

import java.util.Date;

public class EmailLastDays {
    private String uniqueId;
    private String firstName;
    private String lastName;
    private Timestamp dateUpdateReceived;
    private Timestamp dateUpdateSent;
    public EmailLastDays() {
        super();
    }

    public EmailLastDays(String uniqueId, String firstName, String lastName,
                         Timestamp dateUpdateReceived,
                         Timestamp dateUpdateSent) {
        super();
        this.uniqueId = uniqueId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateUpdateReceived = dateUpdateReceived;
        this.dateUpdateSent = dateUpdateSent;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
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

    public void setDateUpdateReceived(Timestamp dateUpdateReceived) {
        this.dateUpdateReceived = dateUpdateReceived;
    }

    public Timestamp getDateUpdateReceived() {
        return dateUpdateReceived;
    }

    public void setDateUpdateSent(Timestamp dateUpdateSent) {
        this.dateUpdateSent = dateUpdateSent;
    }

    public Timestamp getDateUpdateSent() {
        return dateUpdateSent;
    }
}
