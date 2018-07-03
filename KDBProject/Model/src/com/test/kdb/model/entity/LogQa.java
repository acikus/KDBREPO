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
  @NamedQuery(name = "LogQa.findAll", query = "select o from LogQa o")
})
@Table(name = "LOG_QA")
public class LogQa implements Serializable {
    @Column(name="DATE_UPDATE")
    private Timestamp dateUpdate;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqLogQaId")
    @SequenceGenerator(name="seqLogQaId", sequenceName="LOG_QA_ID", allocationSize=1)
    @Column(name="LOG_QA_ID", nullable = false)
    private Long logQaId;
    private char[] text;
    @ManyToOne
    @JoinColumn(name = "QA_ID")
    private Qa qa;

    public LogQa() {
    }

    public LogQa(Timestamp dateUpdate, Long logQaId, Qa qa) {
        this.dateUpdate = dateUpdate;
        this.logQaId = logQaId;
        this.qa = qa;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Long getLogQaId() {
        return logQaId;
    }

    public void setLogQaId(Long logQaId) {
        this.logQaId = logQaId;
    }


    public char[] getText() {
        return text;
    }

    public void setText(char[] text) {
        this.text = text;
    }

    public Qa getQa() {
        return qa;
    }

    public void setQa(Qa qa) {
        this.qa = qa;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof LogQa)) {
            return false;
        }
        final LogQa other = (LogQa)object;
        if (!(logQaId == null ? other.logQaId == null : logQaId.equals(other.logQaId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((logQaId == null) ? 0 : logQaId.hashCode());
        return result;
    }
}
