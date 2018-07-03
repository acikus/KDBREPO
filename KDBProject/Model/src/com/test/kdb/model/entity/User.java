package com.test.kdb.model.entity;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;

@Entity
@Cache(type = CacheType.NONE)
@Table(name="KDBUSER")
@NamedQueries({
  @NamedQuery(name = "User.findAll", query = "select o from User o Order by o.username"),
  @NamedQuery(name = "User.findByUsername", query = "select o from User o where o.username = :username")
})
public class User implements Serializable {
    @Column(name="ACTIVE_YN")
    private Long activeYn;
    @Column(length = 200)
    private String email;
    @Column(name="FIRST_NAME", length = 400)
    private String firstName;
    @Column(name="LAST_NAME", length = 400)
    private String lastName;
    @Column(length = 200)
    private String location;
    @Column(length = 100)
    private String password;
    @Column(name="PHONE_NUMBER", length = 200)
    private String phoneNumber;
    @Column(length = 200)
    private String title;
    @Column(unique = true, length = 100)
    private String username;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqUserId")
    @SequenceGenerator(name="seqUserId", sequenceName="USER_ID", allocationSize=1)
    @Column(name="USER_ID", nullable = false)
    private Long userId;
    @OneToMany(mappedBy = "user")
    private List<News> newsList;
    @OneToMany(mappedBy = "user")
    private List<Document> documentList;
    @OneToMany(mappedBy = "userLockedBy")
    private List<Email> emailList;
    @OneToMany(mappedBy = "userAnsweredBy")
    private List<Email> emailList1;
    @OneToMany(mappedBy = "user")
    private List<LogDoc> logDocList;
    @OneToMany(mappedBy = "user")
    private List<QaNote> qaNoteList;
    @OneToMany(mappedBy = "user")
    private List<ErrorLog> errorLogList;
    @OneToMany(mappedBy = "user")
    private List<Qa> qaList;
    @OneToMany(mappedBy = "userReadBy")
    private List<Email> emailList2;
    @OneToMany(mappedBy = "user1")
    private List<QaNote> qaNoteList1;
    @OneToMany(mappedBy = "user")
    private List<LogEmail> logEmailList;

    public User() {
    }

    public User(Boolean activeYn, String email, String firstName, String lastName,
                String location, String password, String phoneNumber,
                String title, Long userId, String username) {
        if (activeYn == null)
            this.activeYn = 0L;
        else
            this.activeYn = activeYn ? 1L : 0L;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.title = title;
        this.userId = userId;
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public News addNews(News news) {
        getNewsList().add(news);
        news.setUser(this);
        return news;
    }

    public News removeNews(News news) {
        getNewsList().remove(news);
        news.setUser(null);
        return news;
    }

    public List<Document> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    public Document addDocument(Document document) {
        getDocumentList().add(document);
        document.setUser(this);
        return document;
    }

    public Document removeDocument(Document document) {
        getDocumentList().remove(document);
        document.setUser(null);
        return document;
    }

    public List<Email> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<Email> emailList) {
        this.emailList = emailList;
    }

/*    public Email addEmail(Email email) {
        getEmailList().add(email);
        email.setUser(this);
        return email;
    }

    public Email removeEmail(Email email) {
        getEmailList().remove(email);
        email.setUser(null);
        return email;
    }
*/
    public List<Email> getEmailList1() {
        return emailList1;
    }

    public void setEmailList1(List<Email> emailList1) {
        this.emailList1 = emailList1;
    }
/*
    public Email addEmail1(Email email) {
        getEmailList1().add(email);
        email.setUser1(this);
        return email;
    }

    public Email removeEmail1(Email email) {
        getEmailList1().remove(email);
        email.setUser1(null);
        return email;
    }
*/
    public List<LogDoc> getLogDocList() {
        return logDocList;
    }

    public void setLogDocList(List<LogDoc> logDocList) {
        this.logDocList = logDocList;
    }

    public LogDoc addLogDoc(LogDoc logDoc) {
        getLogDocList().add(logDoc);
        logDoc.setUser(this);
        return logDoc;
    }

    public LogDoc removeLogDoc(LogDoc logDoc) {
        getLogDocList().remove(logDoc);
        logDoc.setUser(null);
        return logDoc;
    }

    public List<QaNote> getQaNoteList() {
        return qaNoteList;
    }

    public void setQaNoteList(List<QaNote> qaNoteList) {
        this.qaNoteList = qaNoteList;
    }

    public QaNote addQaNote(QaNote qaNote) {
        getQaNoteList().add(qaNote);
        qaNote.setUser(this);
        return qaNote;
    }

    public QaNote removeQaNote(QaNote qaNote) {
        getQaNoteList().remove(qaNote);
        qaNote.setUser(null);
        return qaNote;
    }

    public List<ErrorLog> getErrorLogList() {
        return errorLogList;
    }

    public void setErrorLogList(List<ErrorLog> errorLogList) {
        this.errorLogList = errorLogList;
    }

    public ErrorLog addErrorLog(ErrorLog errorLog) {
        getErrorLogList().add(errorLog);
        errorLog.setUser(this);
        return errorLog;
    }

    public ErrorLog removeErrorLog(ErrorLog errorLog) {
        getErrorLogList().remove(errorLog);
        errorLog.setUser(null);
        return errorLog;
    }

    public List<Qa> getQaList() {
        return qaList;
    }

    public void setQaList(List<Qa> qaList) {
        this.qaList = qaList;
    }

    public Qa addQa(Qa qa) {
        getQaList().add(qa);
        qa.setUser(this);
        return qa;
    }

    public Qa removeQa(Qa qa) {
        getQaList().remove(qa);
        qa.setUser(null);
        return qa;
    }

    public List<Email> getEmailList2() {
        return emailList2;
    }

    public void setEmailList2(List<Email> emailList2) {
        this.emailList2 = emailList2;
    }
/*
    public Email addEmail2(Email email) {
        getEmailList2().add(email);
        email.setUser2(this);
        return email;
    }

    public Email removeEmail2(Email email) {
        getEmailList2().remove(email);
        email.setUser2(null);
        return email;
    }
*/
    public List<QaNote> getQaNoteList1() {
        return qaNoteList1;
    }

    public void setQaNoteList1(List<QaNote> qaNoteList1) {
        this.qaNoteList1 = qaNoteList1;
    }

    public QaNote addQaNote1(QaNote qaNote) {
        getQaNoteList1().add(qaNote);
        qaNote.setUser1(this);
        return qaNote;
    }

    public QaNote removeQaNote1(QaNote qaNote) {
        getQaNoteList1().remove(qaNote);
        qaNote.setUser1(null);
        return qaNote;
    }

    public List<LogEmail> getLogEmailList() {
        return logEmailList;
    }

    public void setLogEmailList(List<LogEmail> logEmailList) {
        this.logEmailList = logEmailList;
    }

    public LogEmail addLogEmail(LogEmail logEmail) {
        getLogEmailList().add(logEmail);
        logEmail.setUser(this);
        return logEmail;
    }

    public LogEmail removeLogEmail(LogEmail logEmail) {
        getLogEmailList().remove(logEmail);
        logEmail.setUser(null);
        return logEmail;
    }
    
    /* ************************************************************************
     * for joing the tables (many-to-many)
     * Added by Igor Paunov
     *************************************************************************/
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "GROUP_USER",
    joinColumns = {
    @JoinColumn(name="USER_ID") 
    },
    inverseJoinColumns = {
    @JoinColumn(name="GROUP_ID")
    }
    )
    private List<Group> groups;

    /**
    * @return 
    */
    public List<Group> getGroups() {
    return groups;
    }

    /**
    * @param 
    */
    public void setGroups(List<Group> groups) {
    this.groups = groups;
    }
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_QA",
            joinColumns = {
                @JoinColumn(name = "USER_ID") 
            },
            inverseJoinColumns = {
                @JoinColumn(name = "QA_ID")
            }
        )
    @OrderBy("counter DESC")
    private List<Qa> favoriteQas;

    /**
    * @return 
    */
    public List<Qa> getFavoriteQas() {
    return favoriteQas;
    }

    /**
    * @param 
    */
    public void setFavoriteQas(List<Qa> favoriteQas) {
    this.favoriteQas = favoriteQas;
    }
    
    public void addFavoriteQa(Qa qa) {
        if (this.favoriteQas == null) {
            favoriteQas = new ArrayList<Qa>();
        }
        favoriteQas.add(qa);
    }
    
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_QAUSEFUL",
            joinColumns = {
                @JoinColumn(name = "USER_ID") 
            },
            inverseJoinColumns = {
                @JoinColumn(name = "QA_ID")
            }
        )
    @OrderBy("counter DESC")
    private List<Qa> usefulQas;

    /**
    * @return 
    */
    public List<Qa> getUsefulQas() {
    return usefulQas;
    }

    /**
    * @param 
    */
    public void setUsefulQas(List<Qa> usefulQas) {
    this.usefulQas = usefulQas;
    }
    
    public void addUsefulQa(Qa qa) {
        if (this.usefulQas == null) {
            usefulQas = new ArrayList<Qa>();
        }
        usefulQas.add(qa);
    }
    
    /**
    * @param 
    */
    public void removeUsefulQa(Qa qa) {
        if (this.usefulQas != null) {
            this.usefulQas.remove(qa);
        }
    }   
    /* ***********************************************************************/
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_DOCUMENT",
            joinColumns = {
                @JoinColumn(name = "USER_ID") 
            },
            inverseJoinColumns = {
                @JoinColumn(name = "DOCUMENT_ID")
            }
        )
    @OrderBy("counter")
    private List<Document> favoriteDocuments;

    /**
    * @return 
    */
    public List<Document> getFavoriteDocuments() {
    return favoriteDocuments;
    }

    /**
    * @param 
    */
    public void setFavoriteDocuments(List<Document> favoriteDocuments) {
    this.favoriteDocuments = favoriteDocuments;
    }
    
    public void addFavoriteDocument(Document document) {
        if (this.favoriteDocuments == null) {
            favoriteDocuments = new ArrayList<Document>();
        }
        favoriteDocuments.add(document);
    }
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_DOCUSEFUL",
            joinColumns = {
                @JoinColumn(name = "USER_ID") 
            },
            inverseJoinColumns = {
                @JoinColumn(name = "DOCUMENT_ID")
            }
        )
    @OrderBy("counter DESC")
    private List<Document> usefulDocuments;

    /**
    * @return 
    */
    public List<Document> getUsefulDocuments() {
    return usefulDocuments;
    }

    /**
    * @param 
    */
    public void setUsefulDocuments(List<Document> usefulDocuments) {
    this.usefulDocuments = usefulDocuments;
    }
    
    public void addUsefulDocument(Document doc) {
        if (this.usefulDocuments == null) {
            usefulDocuments = new ArrayList<Document>();
        }
        usefulDocuments.add(doc);
    }
    
    /**
    * @param 
    */
    public void removeUsefulDocument(Document doc) {
        if (this.usefulDocuments != null) {
            this.usefulDocuments.remove(doc);
        }
    }  
    /* ***********************************************************************/

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof User)) {
            return false;
        }
        final User other = (User)object;
        if (!(userId == null ? other.userId == null : userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }
}
