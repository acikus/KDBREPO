package com.test.kdb.model.entity;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.test.kdb.model.utils.DocumentChunk;


@Entity

@NamedQueries({
  @NamedQuery(name = "Document.findAll", query = "select o from Document o Order by o.counter"),
  @NamedQuery(name = "Document.findById", query = "select o from Document o where o.documentId = :id ")
  })
public class Document implements Serializable {
    @Column(name="ACTIVE_YN")
    private Long activeYn;
    @Column(name="CONTENT_APPROVED")
    private Long contentApproved;
    @Column(name="CONTENT_RECOMMENDED")
    private Long contentRecommended;
    private Long counter;
    @Column(name="DATE_INSERT")
    private Timestamp dateInsert;
    @Column(name="DATE_LEGAL")
    private Timestamp dateLegal;
    @Column(name="DATE_UPDATE")
    private Timestamp dateUpdate;
    @Column(length = 1000)
    private String description;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqDocumentId")
    @SequenceGenerator(name="seqDocumentId", sequenceName="DOCUMENT_ID", allocationSize=1)
    @Column(name="DOCUMENT_ID", nullable = false)
    private Long documentId;
    @Column(name="EXPIRED_YN")
    private Long expiredYn;
    @Column(name="INTERNAL_DOC_YN")
    private Long internalDocYn;
    @Column(length = 1000)
    private String keywords;
    @Column(length = 200)
    private String name;
    @Lob
    @Basic(fetch=FetchType.LAZY)
    private byte[] object;
    @Column(name="USEFUL_N")
    private Long usefulN;
    @Column(name="USEFUL_Y")
    private Long usefulY;
    private Long version;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "SOURCE_ID")
    private DocSource docSource;
    @OneToMany(mappedBy = "document")
    private List<LogDoc> logDocList;
    @ManyToOne
    @JoinColumn(name = "TYPE_ID")
    private DocType docType;
    @ManyToOne
    @JoinColumn(name = "LANGUAGE_ID")
    private Language language;
    @OneToMany(mappedBy = "document")
    private List<DocumentstatsWeb> documentstatsWebList;
    @ManyToOne
    @JoinColumn(name = "EXTENSION_ID")
    private DocExtension docExtension;
    @Transient
    private List<DocumentChunk> documentChunksList = new ArrayList<DocumentChunk>();
    @Transient
    private String filename;

    public Document() {
    }

    public Document(Long activeYn, Long contentApproved,
                    Long contentRecommended, Long counter,
                    Timestamp dateInsert, Timestamp dateLegal,
                    Timestamp dateUpdate, String description, Long documentId,
                    Long expiredYn, DocExtension docExtension, Long internalDocYn,
                    String keywords, Language language, String name,
                    DocSource docSource, DocType docType, Long usefulN, Long usefulY,
                    User user, Long version) {
        this.activeYn = activeYn;
        this.contentApproved = contentApproved;
        this.contentRecommended = contentRecommended;
        this.counter = counter;
        this.dateInsert = dateInsert;
        this.dateLegal = dateLegal;
        this.dateUpdate = dateUpdate;
        this.description = description;
        this.documentId = documentId;
        this.expiredYn = expiredYn;
        this.docExtension = docExtension;
        this.internalDocYn = internalDocYn;
        this.keywords = keywords;
        this.language = language;
        this.name = name;
        this.docSource = docSource;
        this.docType = docType;
        this.usefulN = usefulN;
        this.usefulY = usefulY;
        this.user = user;
        this.version = version;
    }

    public Boolean getActiveYn() {
        return this.activeYn == null ? false : this.activeYn == 1;
    }

    public void setActiveYn(Boolean activeYn) {
        if (activeYn == null)
            this.activeYn = 0L;
        else
            this.activeYn = activeYn ? 1L : 0L;
    }

    public Boolean getContentApproved() {
        return this.contentApproved == null ? false : this.contentApproved == 1;
    }

    public void setContentApproved(Boolean contentApproved) {
        if (contentApproved == null)
            this.contentApproved = 0L;
        else
            this.contentApproved = contentApproved ? 1L : 0L;
    }

    public Boolean getContentRecommended() {
        return this.contentRecommended == null ? false : this.contentRecommended == 1;
    }

    public void setContentRecommended(Boolean contentRecommended) {
        if (contentRecommended == null)
            this.contentRecommended = 0L;
        else
            this.contentRecommended = contentRecommended ? 1L : 0L;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Timestamp getDateInsert() {
        return dateInsert;
    }

    public void setDateInsert(Timestamp dateInsert) {
        this.dateInsert = dateInsert;
    }

    public Timestamp getDateLegal() {
        return dateLegal;
    }

    public void setDateLegal(Timestamp dateLegal) {
        this.dateLegal = dateLegal;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Boolean getExpiredYn() {
        return this.expiredYn == null ? false : this.expiredYn == 1;
    }

    public void setExpiredYn(Boolean expiredYn) {
        if (expiredYn == null)
            this.expiredYn = 0L;
        else
            this.expiredYn = expiredYn ? 1L : 0L;
    }


    public Boolean getInternalDocYn() {
        return this.internalDocYn == null ? false : this.internalDocYn == 1;
    }

    public void setInternalDocYn(Boolean internalDocYn) {
        if (internalDocYn == null)
            this.internalDocYn = 0L;
        else
            this.internalDocYn = internalDocYn ? 1L : 0L;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getObject() {
        return object;
    }

    public void setObject(byte[] object) {
        this.object = object;
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


    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DocSource getDocSource() {
        return docSource;
    }

    public void setDocSource(DocSource docSource) {
        this.docSource = docSource;
    }

    public List<LogDoc> getLogDocList() {
        return logDocList;
    }

    public void setLogDocList(List<LogDoc> logDocList) {
        this.logDocList = logDocList;
    }

    public LogDoc addLogDoc(LogDoc logDoc) {
        getLogDocList().add(logDoc);
        logDoc.setDocument(this);
        return logDoc;
    }

    public LogDoc removeLogDoc(LogDoc logDoc) {
        getLogDocList().remove(logDoc);
        logDoc.setDocument(null);
        return logDoc;
    }

    public DocType getDocType() {
        return docType;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<DocumentstatsWeb> getDocumentstatsWebList() {
        return documentstatsWebList;
    }

    public void setDocumentstatsWebList(List<DocumentstatsWeb> documentstatsWebList) {
        this.documentstatsWebList = documentstatsWebList;
    }

    public DocumentstatsWeb addDocumentstatsWeb(DocumentstatsWeb documentstatsWeb) {
        getDocumentstatsWebList().add(documentstatsWeb);
        documentstatsWeb.setDocument(this);
        return documentstatsWeb;
    }

    public DocumentstatsWeb removeDocumentstatsWeb(DocumentstatsWeb documentstatsWeb) {
        getDocumentstatsWebList().remove(documentstatsWeb);
        documentstatsWeb.setDocument(null);
        return documentstatsWeb;
    }

    public DocExtension getDocExtension() {
        return docExtension;
    }

    public void setDocExtension(DocExtension docExtension) {
        this.docExtension = docExtension;
    }
    
    /* ************************************************************************
     * for joing the tables (many-to-many)
     * Added by Igor Paunov
     *************************************************************************/
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "DOCUMENT_QA",
            joinColumns = {
                @JoinColumn(name = "DOCUMENT_ID") 
            },
            inverseJoinColumns = {
                @JoinColumn(name = "QA_ID")
            }
        )
    @OrderBy("counter DESC")
    private List<Qa> qas;

    /**
    * @return 
    */
    public List<Qa> getQas() {
    return qas;
    }

    /**
    * @param 
    */
    public void setQas(List<Qa> qas) {
    this.qas = qas;
    }
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "DOCUMENT_CATEGORY",
            joinColumns = {
                @JoinColumn(name = "DOCUMENT_ID") 
            },
            inverseJoinColumns = {
                @JoinColumn(name = "CATEGORY_ID")
            }
        )
    @OrderBy("name")
    private List<Category> categories = new ArrayList<Category>();

    /**
    * @return 
    */
    public List<Category> getCategories() {
    return categories;
    }

    /**
    * @param 
    */
    public void setCategories(List<Category> categories) {
    this.categories = categories;
    }
    
    /**
    * @param 
    */
//    public void addCategory(Category category) {
//        if (this.categories == null) {
//            categories = new ArrayList<Category>();
//        }
//        if (!categories.contains(category)) {
//            this.categories.add(category);
//        }
//        category.getDocuments().add(this);
//    }

    /**
    * @param 
    */
//    public void removeCategory(Category category) {
//        if (this.categories != null) {
//            this.categories.remove(category);
//        }
//    }

    /* ***********************************************************************/


    public void setDocumentChunksList(List<DocumentChunk> documentChunksList) {
        this.documentChunksList = documentChunksList;
    }

    public List<DocumentChunk> getDocumentChunksList() {
        return documentChunksList;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
    
    
    
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "USER_DOCUMENT",
            joinColumns = {
                @JoinColumn(name="DOCUMENT_ID") 
            },
            inverseJoinColumns = {
                @JoinColumn(name="USER_ID")
            }
        )
    @OrderBy("username")
    private List<User> users;

    /**
    * @return 
    */
    public List<User> getUsers() {
    return users;
    }

    /**
    * @param 
    */
    public void setUsers(List<User> users) {
    this.users = users;
    }
    /* ***********************************************************************/


    public void addUser(User user) {
        if (this.users == null) {
            users = new ArrayList<User>();
        }
        if (!users.contains(user)) {
            this.users.add(user);
        }
    }

    public void addUsers(List<User> users) {
        if (this.users == null) {
            users = new ArrayList<User>();
        }
        for (User user : users) {
            if (!users.contains(user)) {
                this.users.add(user);
            }
        }
    }

    /**
    * @param 
    */
    public void removeUser(User user) {
        if (this.users != null) {
            this.users.remove(user);
        }
    }


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_DOCUSEFUL",
            joinColumns = {
                @JoinColumn(name = "DOCUMENT_ID") 
            },
            inverseJoinColumns = {
                @JoinColumn(name = "USER_ID")
            }
        )
    @OrderBy("username")
    private List<User> usefulDocUsers;

    /**
    * @return 
    */
    public List<User> getUsefulDocUsers() {
    return usefulDocUsers;
    }

    /**
    * @param 
    */
    public void setUsefulDocUsers(List<User> usefulDocUsers) {
    this.usefulDocUsers = usefulDocUsers;
    }
    /* ***********************************************************************/

    public void addUsefulDocUsers(User user) {
        if (this.usefulDocUsers == null) {
            usefulDocUsers = new ArrayList<User>();
        }
        if (!usefulDocUsers.contains(user)) {
            this.usefulDocUsers.add(user);
        }
    }

    public void addUsefulDocUsers(List<User> users) {
        if (this.usefulDocUsers == null) {
            usefulDocUsers = new ArrayList<User>();
        }
        for (User user : users) {
            if (!usefulDocUsers.contains(user)) {
                this.usefulDocUsers.add(user);
            }
        }
    }

    /**
    * @param 
    */
    public void removeUsefulDocUsers(User user) {
        if (this.usefulDocUsers != null) {
            this.usefulDocUsers.remove(user);
        }
    }
    


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Document)) {
            return false;
        }
        final Document other = (Document)object;
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
