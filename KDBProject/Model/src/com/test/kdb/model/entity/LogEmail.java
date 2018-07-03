package com.test.kdb.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "LogEmail.findAll", query = "select o from LogEmail o")
})
@Table(name = "LOG_EMAIL")
public class LogEmail implements Serializable {
    @Column(name="DATE_UPDATE")
    private Long dateUpdate;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqLogEmailId")
    @SequenceGenerator(name="seqLogEmailId", sequenceName="LOG_EMAIL_ID", allocationSize=1)
    @Column(name="LOG_EMAIL_ID", nullable = false)
    private Long logEmailId;
    private char[] text;
    @ManyToOne
    @JoinColumn(name = "EMAIL_ID")
    private Email email;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public LogEmail() {
    }

    public LogEmail(Long dateUpdate, Email email, Long logEmailId, User user) {
        this.dateUpdate = dateUpdate;
        this.email = email;
        this.logEmailId = logEmailId;
        this.user = user;
    }

    public Long getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Long dateUpdate) {
        this.dateUpdate = dateUpdate;
    }


    public Long getLogEmailId() {
        return logEmailId;
    }

    public void setLogEmailId(Long logEmailId) {
        this.logEmailId = logEmailId;
    }

    public char[] getText() {
        return text;
    }

    public void setText(char[] text) {
        this.text = text;
    }


    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof LogEmail)) {
            return false;
        }
        final LogEmail other = (LogEmail)object;
        if (!(logEmailId == null ? other.logEmailId == null : logEmailId.equals(other.logEmailId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((logEmailId == null) ? 0 : logEmailId.hashCode());
        return result;
    }
}
