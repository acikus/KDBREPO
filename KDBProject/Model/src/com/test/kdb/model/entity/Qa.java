package com.test.kdb.model.entity;

import java.io.*;

import java.sql.*;

import java.util.*;

import javax.persistence.*;


@Entity
@NamedQueries({
  @NamedQuery(name = "Qa.findAll", query = "select o from Qa o Order by o.counter"),
  @NamedQuery(name = "Qa.findById", query = "select o from Qa o where o.qaId = :id ")
})
public class Qa implements Serializable {
    @Column(name="ACTIVE_YN")
    private Long activeYn;
    @Column(name="CONTENT_APPROVED")
    private Long contentApproved;
    @Column(name="CONTENT_RECOMMENDED")
    private Long contentRecommended;
    private Long counter;
    @Column(name="DATE_INSERT")
    private Timestamp dateInsert;
    @Column(name="DATE_UPDATE")
    private Timestamp dateUpdate;
    @Column(name="DETAIL_ANSWER")
    private String detailAnswer;
    @Column(name="DETAIL_ANSWER_HTML")
    private String detailAnswerHtml;
    @Column(length = 500)
    private String keywords;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqQaId")
    @SequenceGenerator(name="seqQaId", sequenceName="QA_ID", allocationSize=1)
    @Column(name="QA_ID", nullable = false)
    private Long qaId;
    @Column(length = 1000)
    private String questions;
    @Column(name="SHORT_ANSWER", length = 1000)
    private String shortAnswer;
    @Column(name="USEFUL_N")
    private Long usefulN;
    @Column(name="USEFUL_Y")
    private Long usefulY;
    private Long version;
    @OneToMany(mappedBy = "qa")
    private List<ExternalLink> externalLinkList;
    @OneToMany(mappedBy = "qa")
    private List<QastatsWeb> qastatsWebList;
    @ManyToOne
    @JoinColumn(name = "LANGUAGE_ID")
    private Language language;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @OneToMany(mappedBy = "qa")
    private List<QaNote> qaNoteList;
    @OneToMany(mappedBy = "qa")
    private List<LogQa> logQaList;

    public Qa() {
    }

    public Qa(Long activeYn, Long contentApproved, Long contentRecommended,
              Long counter, Timestamp dateInsert, Timestamp dateUpdate,
              String keywords, Language language, Long qaId, String questions,
              String shortAnswer, Long usefulN, Long usefulY, User user,
              Long version) {
        this.activeYn = activeYn;
        this.contentApproved = contentApproved;
        this.contentRecommended = contentRecommended;
        this.counter = counter;
        this.dateInsert = dateInsert;
        this.dateUpdate = dateUpdate;
        this.keywords = keywords;
        this.language = language;
        this.qaId = qaId;
        this.questions = questions;
        this.shortAnswer = shortAnswer;
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

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getDetailAnswer() {
        return detailAnswer;
    }

    public void setDetailAnswer(String detailAnswer) {
        this.detailAnswer = detailAnswer;
    }

    public String getDetailAnswerHtml() {
        return detailAnswerHtml;
    }

    public void setDetailAnswerHtml(String detailAnswerHtml) {
        this.detailAnswerHtml = detailAnswerHtml;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }


    public Long getQaId() {
        return qaId;
    }

    public void setQaId(Long qaId) {
        this.qaId = qaId;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getShortAnswer() {
        return shortAnswer;
    }

    public void setShortAnswer(String shortAnswer) {
        this.shortAnswer = shortAnswer;
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

    public List<ExternalLink> getExternalLinkList() {
        return externalLinkList;
    }

    public void setExternalLinkList(List<ExternalLink> externalLinkList) {
        this.externalLinkList = externalLinkList;
    }

    public List<QastatsWeb> getQastatsWebList() {
        return qastatsWebList;
    }

    public void setQastatsWebList(List<QastatsWeb> qastatsWebList) {
        this.qastatsWebList = qastatsWebList;
    }

    public QastatsWeb addQastatsWeb(QastatsWeb qastatsWeb) {
        getQastatsWebList().add(qastatsWeb);
        qastatsWeb.setQa(this);
        return qastatsWeb;
    }

    public QastatsWeb removeQastatsWeb(QastatsWeb qastatsWeb) {
        getQastatsWebList().remove(qastatsWeb);
        qastatsWeb.setQa(null);
        return qastatsWeb;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<QaNote> getQaNoteList() {
        return qaNoteList;
    }

    public void setQaNoteList(List<QaNote> qaNoteList) {
        this.qaNoteList = qaNoteList;
    }

    public QaNote addQaNote(QaNote qaNote) {
        getQaNoteList().add(qaNote);
        qaNote.setQa(this);
        return qaNote;
    }

    public QaNote removeQaNote(QaNote qaNote) {
        getQaNoteList().remove(qaNote);
        qaNote.setQa(null);
        return qaNote;
    }

    public List<LogQa> getLogQaList() {
        return logQaList;
    }

    public void setLogQaList(List<LogQa> logQaList) {
        this.logQaList = logQaList;
    }

    public LogQa addLogQa(LogQa logQa) {
        getLogQaList().add(logQa);
        logQa.setQa(this);
        return logQa;
    }

    public LogQa removeLogQa(LogQa logQa) {
        getLogQaList().remove(logQa);
        logQa.setQa(null);
        return logQa;
    }
    
    /* ************************************************************************
     * for joing the tables (many-to-many)
     * Added by Igor Paunov
     *************************************************************************/
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "DOCUMENT_QA",
            joinColumns = {
                @JoinColumn(name = "QA_ID") 
            },
            inverseJoinColumns = {
                @JoinColumn(name = "DOCUMENT_ID")
            }
        )
    @OrderBy("counter DESC")
    private List<Document> documents;

    /**
    * @return 
    */
    public List<Document> getDocuments() {
    return documents;
    }

    /**
    * @param 
    */
    public void setDocuments(List<Document> documents) {
    this.documents = documents;
    }
    
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "QA_QA",
            joinColumns = {
                @JoinColumn(name = "QA_ID") 
            },
            inverseJoinColumns = {
                @JoinColumn(name = "L_QA_ID")
            }
        )
    @OrderBy("counter")
    private List<Qa> qas;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "CATEGORY_QA",
            joinColumns = {
                @JoinColumn(name = "QA_ID") 
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
    
//    public void addCategory(Category category) {
//        if (this.categories == null) {
//            categories = new ArrayList<Category>();
//        }
//        if (!categories.contains(category)) {
//            this.categories.add(category);
//        }
//    }
//    public void addCategories(List<Category> categories) {
//        if (this.categories == null) {
//            categories = new ArrayList<Category>();
//        }
//        for (Category category : categories) {
//            if (!categories.contains(category)) {
//                this.categories.add(category);
//            }
//        }
//    }
//
//    /**
//    * @param 
//    */
//    public void removeCategory(Category category) {
//        if (this.categories != null) {
//            this.categories.remove(category);
//        }
//    }
        
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_QA",
            joinColumns = {
                @JoinColumn(name = "QA_ID") 
            },
            inverseJoinColumns = {
                @JoinColumn(name = "USER_ID")
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

    public void setQas(List<Qa> qas) {
        this.qas = qas;
    }

    public List<Qa> getQas() {
        return qas;
    }
    
//    public boolean equals(Object obj) {
//           if (obj == null) return false;
//           if (!this.getClass().equals(obj.getClass())) return false;
//           Qa obj2 = (Qa) obj;
//           return this.qaId.equals(obj2.qaId);
//    }
    
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_QAUSEFUL",
            joinColumns = {
                @JoinColumn(name = "QA_ID") 
            },
            inverseJoinColumns = {
                @JoinColumn(name = "USER_ID")
            }
        )
    @OrderBy("username")
    private List<User> usefulQaUsers;

    /**
    * @return 
    */
    public List<User> getUsefulQaUsers() {
    return usefulQaUsers;
    }

    /**
    * @param 
    */
    public void setUsefulQaUsers(List<User> usefulQaUsers) {
    this.usefulQaUsers = usefulQaUsers;
    }
    /* ***********************************************************************/

    public void addUsefulQaUsers(User user) {
        if (this.usefulQaUsers == null) {
            usefulQaUsers = new ArrayList<User>();
        }
        if (!usefulQaUsers.contains(user)) {
            this.usefulQaUsers.add(user);
        }
    }

    public void addUsefulQaUsers(List<User> users) {
        if (this.usefulQaUsers == null) {
            usefulQaUsers = new ArrayList<User>();
        }
        for (User user : users) {
            if (!usefulQaUsers.contains(user)) {
                this.usefulQaUsers.add(user);
            }
        }
    }

    /**
    * @param 
    */
    public void removeUsefulQaUsers(User user) {
        if (this.usefulQaUsers != null) {
            this.usefulQaUsers.remove(user);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Qa)) {
            return false;
        }
        final Qa other = (Qa)object;
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
