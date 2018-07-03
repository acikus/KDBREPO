package com.test.kdb.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "QastatsWeb.findAll", query = "select o from QastatsWeb o")
})
@Table(name = "QASTATS_WEB")
public class QastatsWeb implements Serializable {
    private Long counter;
    @Id
    @Column(name="QA_ID", nullable = false, insertable = false, updatable = false)
    private Long qaId;
    @Column(name="USEFUL_N")
    private Long usefulN;
    @Column(name="USEFUL_Y")
    private Long usefulY;
    @ManyToOne
    @JoinColumn(name = "QA_ID")
    private Qa qa;

    public QastatsWeb() {
    }

    public QastatsWeb(Long counter, Qa qa, Long usefulN, Long usefulY) {
        this.counter = counter;
        this.qa = qa;
        this.usefulN = usefulN;
        this.usefulY = usefulY;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Long getQaId() {
        return qaId;
    }

    public void setQaId(Long qaId) {
        this.qaId = qaId;
    }

    public Long getUsefulN() {
        return usefulN;
    }

    public void setUsefulN(Long usefulN) {
        this.usefulN = usefulN;
    }

    public Long getUsefulY() {
        return usefulY;
    }

    public void setUsefulY(Long usefulY) {
        this.usefulY = usefulY;
    }

    public Qa getQa() {
        return qa;
    }

    public void setQa(Qa qa) {
        this.qa = qa;
        if (qa != null) {
            this.qaId = qa.getQaId();
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof QastatsWeb)) {
            return false;
        }
        final QastatsWeb other = (QastatsWeb)object;
        if (!(qaId == null ? other.qaId == null : qaId.equals(other.qaId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((qaId == null) ? 0 : qaId.hashCode());
        return result;
    }
}
