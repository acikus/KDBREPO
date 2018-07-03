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
  @NamedQuery(name = "DocType.findAll", query = "select o from DocType o Order by o.name"),
  @NamedQuery(name = "DocType.findActive", query = "select o from DocType o where o.activeYn = :active Order by o.name")
})
@Table(name = "DOC_TYPE")
public class DocType implements Serializable {
    @Column(name="ACTIVE_YN")
    private Long activeYn;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqDocTypeId")
    @SequenceGenerator(name="seqDocTypeId", sequenceName="DOC_TYPE_ID", allocationSize=1)
    @Column(name="DOC_TYPE_ID", nullable = false)
    private Long docTypeId;
    @Column(length = 500)
    private String name;
    @OneToMany(mappedBy = "docType")
    private List<Document> documentList;

    public DocType() {
    }

    public DocType(Boolean activeYn, Long docTypeId, String name) {
        if (activeYn == null)
            this.activeYn = 0L;
        else
            this.activeYn = activeYn ? 1L : 0L;
        this.docTypeId = docTypeId;
        this.name = name;
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

    public Long getDocTypeId() {
        return docTypeId;
    }

    public void setDocTypeId(Long docTypeId) {
        this.docTypeId = docTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    public Document addDocument(Document document) {
        getDocumentList().add(document);
        document.setDocType(this);
        return document;
    }

    public Document removeDocument(Document document) {
        getDocumentList().remove(document);
        document.setDocType(null);
        return document;
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DocType)) {
            return false;
        }
        final DocType other = (DocType)object;
        if (!(docTypeId == null ? other.docTypeId == null : docTypeId.equals(other.docTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((docTypeId == null) ? 0 : docTypeId.hashCode());
        return result;
    }
}
