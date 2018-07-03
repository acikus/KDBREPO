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
  @NamedQuery(name = "ExternalLink.findAll", query = "select o from ExternalLink o Order by o.urllink")
})
@Table(name = "EXTERNAL_LINK")
public class ExternalLink implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqExternalLinkId")
    @SequenceGenerator(name="seqExternalLinkId", sequenceName="EXTERNAL_LINK_ID", allocationSize=1)
    @Column(name="EXTERNAL_LINK_ID", nullable = false)
    private Long externalLinkId;
    @Column(length = 500)
    private String urllink;
    @Column(length = 500)
    private String description;
    @ManyToOne
    @JoinColumn(name = "QA_ID")
    private Qa qa;

    public ExternalLink() {
    }

    public ExternalLink(Qa qa, String urllink, String description) {
        this.qa = qa;
        this.urllink = urllink;
        this.description = description;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ExternalLink)) {
            return false;
        }
        final ExternalLink other = (ExternalLink)object;
        if (!(externalLinkId == null ? other.externalLinkId == null : externalLinkId.equals(other.externalLinkId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((externalLinkId == null) ? 0 : externalLinkId.hashCode());
        return result;
    }

    public Long getExternalLinkId() {
        return externalLinkId;
    }

    public void setExternalLinkId(Long externalLinkId) {
        this.externalLinkId = externalLinkId;
    }

    public String getUrllink() {
        return urllink;
    }

    public void setUrllink(String urllink) {
        this.urllink = urllink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Qa getQa() {
        return qa;
    }

    public void setQa(Qa qa) {
        this.qa = qa;
    }
}
