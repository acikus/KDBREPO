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

@Entity
@NamedQueries({
  @NamedQuery(name = "Language.findAll", query = "select o from Language o Order by o.name"),
  @NamedQuery(name = "Language.findActive", query = "select o from Language o where o.activeYn = :active Order by o.name")
})
public class Language implements Serializable {
    @Column(name="ACTIVE_YN")
    private Long activeYn;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqLanguageId")
    @SequenceGenerator(name="seqLanguageId", sequenceName="LANGUAGE_ID", allocationSize=1)
    @Column(name="LANGUAGE_ID", nullable = false)
    private Long languageId;
    @Column(length = 500)
    private String name;
    @OneToMany(mappedBy = "language")
    private List<Qa> qaList;
    @OneToMany(mappedBy = "language")
    private List<Document> documentList;

    public Language() {
    }

    public Language(Long activeYn, Long languageId, String name) {
        this.activeYn = activeYn;
        this.languageId = languageId;
        this.name = name;
    }

    public Long getActiveYn() {
        return activeYn;
    }

    public void setActiveYn(Long activeYn) {
        this.activeYn = activeYn;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Qa> getQaList() {
        return qaList;
    }

    public void setQaList(List<Qa> qaList) {
        this.qaList = qaList;
    }

    public Qa addQa(Qa qa) {
        getQaList().add(qa);
        qa.setLanguage(this);
        return qa;
    }

    public Qa removeQa(Qa qa) {
        getQaList().remove(qa);
        qa.setLanguage(null);
        return qa;
    }

    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    public Document addDocument(Document document) {
        getDocumentList().add(document);
        document.setLanguage(this);
        return document;
    }

    public Document removeDocument(Document document) {
        getDocumentList().remove(document);
        document.setLanguage(null);
        return document;
    }
    
    
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Language)) {
            return false;
        }
        final Language other = (Language)object;
        if (!(languageId == null ? other.languageId == null : languageId.equals(other.languageId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((languageId == null) ? 0 : languageId.hashCode());
        return result;
    }
    
    
}
