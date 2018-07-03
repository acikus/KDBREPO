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
  @NamedQuery(name = "DocExtension.findAll", query = "select o from DocExtension o Order by o.extension"),
  @NamedQuery(name = "DocExtension.findActive", query = "select o from DocExtension o where o.activeYn = :active Order by o.extension"),
  @NamedQuery(name = "DocExtension.findById", query = "select o from DocExtension o where o.docExtensionId = :id"),
  @NamedQuery(name = "DocExtension.findByExtension", query = "select o from DocExtension o where o.extension = :extension")
})
@Table(name = "DOC_EXTENSION")
public class DocExtension implements Serializable {
    @Column(name="ACTIVE_YN")
    private Long activeYn;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqDocExtensionId")
    @SequenceGenerator(name="seqDocExtensionId", sequenceName="DOC_EXTENSION_ID", allocationSize=1)
    @Column(name="DOC_EXTENSION_ID", nullable = false)
    private Long docExtensionId;
    @Column(length = 20)
    private String extension;
    @Column(length = 500)
    private String mimetype;
    @OneToMany(mappedBy = "docExtension")
    private List<Document> documentList;

    public DocExtension() {
    }

    public DocExtension(Boolean activeYn, Long docExtensionId, String extension,
                        String mimetype) {
        if (activeYn == null)
            this.activeYn = 0L;
        else
            this.activeYn = activeYn ? 1L : 0L;
        this.docExtensionId = docExtensionId;
        this.extension = extension;
        this.mimetype = mimetype;
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

    public Long getDocExtensionId() {
        return docExtensionId;
    }

    public void setDocExtensionId(Long docExtensionId) {
        this.docExtensionId = docExtensionId;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    public Document addDocument(Document document) {
        getDocumentList().add(document);
        document.setDocExtension(this);
        return document;
    }

    public Document removeDocument(Document document) {
        getDocumentList().remove(document);
        document.setDocExtension(null);
        return document;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DocExtension)) {
            return false;
        }
        final DocExtension other = (DocExtension)object;
        if (!(docExtensionId == null ? other.docExtensionId == null : docExtensionId.equals(other.docExtensionId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((docExtensionId == null) ? 0 : docExtensionId.hashCode());
        return result;
    }
}
