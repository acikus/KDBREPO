package com.test.kdb.model.entity;

import java.io.Serializable;

import java.sql.Timestamp;

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
  @NamedQuery(name = "ErrorLog.findAll", query = "select o from ErrorLog o")
})
@Table(name = "ERROR_LOG")
public class ErrorLog implements Serializable {
    @Column(name="DATE_UPDATE")
    private Timestamp dateUpdate;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqErrorLogId")
    @SequenceGenerator(name="seqErrorLogId", sequenceName="ERROR_LOG_ID", allocationSize=1)
    @Column(name="ERROR_LOG_ID", nullable = false)
    private Long errorLogId;
    @Column(name="ERROR_MESSAGE")
    private char[] errorMessage;
    @Column(length = 500)
    private String message;
    @Column(length = 500)
    private String url;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public ErrorLog() {
    }

    public ErrorLog(Timestamp dateUpdate, Long errorLogId, String message,
                    String url, User user) {
        this.dateUpdate = dateUpdate;
        this.errorLogId = errorLogId;
        this.message = message;
        this.url = url;
        this.user = user;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Long getErrorLogId() {
        return errorLogId;
    }

    public void setErrorLogId(Long errorLogId) {
        this.errorLogId = errorLogId;
    }

    public char[] getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(char[] errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        if (!(object instanceof ErrorLog)) {
            return false;
        }
        final ErrorLog other = (ErrorLog)object;
        if (!(errorLogId == null ? other.errorLogId == null : errorLogId.equals(other.errorLogId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((errorLogId == null) ? 0 : errorLogId.hashCode());
        return result;
    }
}
