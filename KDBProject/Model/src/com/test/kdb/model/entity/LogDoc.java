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
  @NamedQuery(name = "LogDoc.findAll", query = "select o from LogDoc o")
})
@Table(name = "LOG_DOC")
public class LogDoc implements Serializable {
    @Column(name="DATE_UPDATE")
    private Timestamp dateUpdate;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqLogDocId")
    @SequenceGenerator(name="seqLogDocId", sequenceName="LOG_DOC_ID", allocationSize=1)
    @Column(name="LOG_DOC_ID", nullable = false)
    private Long logDocId;
    private char[] text;
    @ManyToOne
    @JoinColumn(name = "DOC_ID")
    private Document document;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public LogDoc() {
    }

    public LogDoc(Timestamp dateUpdate, Document document, Long logDocId,
                  User user) {
        this.dateUpdate = dateUpdate;
        this.document = document;
        this.logDocId = logDocId;
        this.user = user;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }


    public Long getLogDocId() {
        return logDocId;
    }

    public void setLogDocId(Long logDocId) {
        this.logDocId = logDocId;
    }

    public char[] getText() {
        return text;
    }

    public void setText(char[] text) {
        this.text = text;
    }


    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
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
        if (!(object instanceof LogDoc)) {
            return false;
        }
        final LogDoc other = (LogDoc)object;
        if (!(logDocId == null ? other.logDocId == null : logDocId.equals(other.logDocId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((logDocId == null) ? 0 : logDocId.hashCode());
        return result;
    }
}
