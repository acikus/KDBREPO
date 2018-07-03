package com.test.kdb.model.entity;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "DocSource.findAll", query = "select o from DocSource o Order by o.docSource"),
  @NamedQuery(name = "DocSource.findActive", query = "select o from DocSource o where o.activeYn = :active Order by o.docSource")

})
@Table(name = "DOC_SOURCE")
public class DocSource implements Serializable {
    @Column(name="ACTIVE_YN")
    private Long activeYn;
    @Column(name="DOC_SOURCE", length = 500)
    private String docSource;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqDocSourceId")
    @SequenceGenerator(name="seqDocSourceId", sequenceName="DOC_SOURCE_ID", allocationSize=1)
    @Column(name="DOC_SOURCE_ID", nullable = false)
    private Long docSourceId;
    @OneToMany(mappedBy = "docSource")
    private List<Document> documentList;

    public DocSource() {
    }

    public DocSource(Boolean activeYn, String docSource, Long docSourceId) {
        if (activeYn == null)
            this.activeYn = 0L;
        else
            this.activeYn = activeYn ? 1L : 0L;
        this.docSource = docSource;
        this.docSourceId = docSourceId;
    }

    public Boolean getActiveYn() {
        if (this.activeYn == null)
            return false;
        return (this.activeYn == 1) ? true : false;
    }

    public void setActiveYn(Boolean activeYn) {
        if (activeYn == null)
            this.activeYn = 0L;
        else
            this.activeYn = activeYn ? 1L : 0L;
    }

    public String getDocSource() {
        return docSource;
    }

    public void setDocSource(String docSource) {
        this.docSource = docSource;
    }

    public Long getDocSourceId() {
        return docSourceId;
    }

    public void setDocSourceId(Long docSourceId) {
        this.docSourceId = docSourceId;
    }

    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    public Document addDocument(Document document) {
        getDocumentList().add(document);
        document.setDocSource(this);
        return document;
    }

    public Document removeDocument(Document document) {
        getDocumentList().remove(document);
        document.setDocSource(null);
        return document;
    }
    
    
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DocSource)) {
            return false;
        }
        final DocSource other = (DocSource)object;
        if (!(docSourceId == null ? other.docSourceId == null : docSourceId.equals(other.docSourceId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((docSourceId == null) ? 0 : docSourceId.hashCode());
        return result;
    }
}
