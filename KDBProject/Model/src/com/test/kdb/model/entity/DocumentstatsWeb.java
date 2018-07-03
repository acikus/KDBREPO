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
  @NamedQuery(name = "DocumentstatsWeb.findAll", query = "select o from DocumentstatsWeb o")
})
@Table(name = "DOCUMENTSTATS_WEB")
public class DocumentstatsWeb implements Serializable {
    private Long counter;
    @Id
    @Column(name="DOCUMENT_ID", nullable = false, insertable = false,
            updatable = false)
    private Long documentId;
    @Column(name="USEFUL_N")
    private Long usefulN;
    @Column(name="USEFUL_Y")
    private Long usefulY;
    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID")
    private Document document;

    public DocumentstatsWeb() {
    }

    public DocumentstatsWeb(Long counter, Document document, Long usefulN,
                            Long usefulY) {
        this.counter = counter;
        this.document = document;
        this.usefulN = usefulN;
        this.usefulY = usefulY;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
        if (document != null) {
            this.documentId = document.getDocumentId();
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DocumentstatsWeb)) {
            return false;
        }
        final DocumentstatsWeb other = (DocumentstatsWeb)object;
        if (!(documentId == null ? other.documentId == null : documentId.equals(other.documentId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((documentId == null) ? 0 : documentId.hashCode());
        return result;
    }
}
