package com.test.kdb.model.entity;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
@NamedQueries({
  @NamedQuery(name = "Email.findAll", query = "select o from Email o"),
  @NamedQuery(name = "Email.incomingMessages", query = "select e from Email e where e.status = 1 order by e.dateInsert desc"),
  @NamedQuery(name = "Email.newMessages", query = "select e from Email e where e.status = 1 and e.userReadBy is null order by e.dateInsert desc"),
  @NamedQuery(name = "Email.answeredMessages", query = "select e from Email e where e.status = 1 and e.userAnsweredBy is not null order by e.dateInsert desc"),
  @NamedQuery(name = "Email.notAnsweredMessages", query = "select e from Email e where e.status = 1 and e.userAnsweredBy is null order by e.dateInsert desc"),
  @NamedQuery(name = "Email.lockedMessages", query = "select e from Email e where e.status = 1 and e.userLockedBy is not null  and e.userAnsweredBy is null order by e.dateInsert desc"),
  @NamedQuery(name = "Email.draftMessages", query = "select e from Email e where e.status = 2 order by e.dateInsert desc"),
  @NamedQuery(name = "Email.sentMessages", query = "select e from Email e where e.status = 3 order by e.dateInsert desc"),
  @NamedQuery(name = "Email.trashdMessages", query = "select e from Email e where e.status = 4 and e.userLockedBy is not null order by e.dateInsert desc"),
  @NamedQuery(name = "Email.archivedMessages", query = "select e from Email e where e.status = 5 and e.userLockedBy is not null order by e.dateInsert desc"),
  @NamedQuery(name = "Email.userMessages", query = "select e from Email e where e.status = 1 and e.userLockedBy = :user and  e.userAnsweredBy is null order by e.dateInsert desc"),
  @NamedQuery(name = "Email.notAssignedMessages", query = "select e from Email e where e.status = 1 and e.userLockedBy is null and  e.userAnsweredBy is null order by e.dateInsert desc"),
  @NamedQuery(name = "Email.threadMessages", query = "select e from Email e where e.uniqueId = :uniqueId order by e.dateInsert desc")
})
public class Email implements Serializable {
    
    public static final String STATUS_NEW = "New";
    public static final String STATUS_LOCKED = "Locked";
    public static final String STATUS_READ = "Read";
    public static final String STATUS_ANSWERED = "Answered";
    public static final String STATUS_UNKNOWN = "Unknown";

    public static final Long INCOMMING = 1L;
    public static final Long DRAFT = 2L;
    public static final Long OUTGOING = 3L;
    
    @Column(length = 300)
    private String address;
    private String body;
    @Column(length = 500)
    private String comments;
    @Column(name="DATE_INSERT")
    private Timestamp dateInsert;
    @Column(name="DATE_UPDATE")
    private Timestamp dateUpdate;
    @Column(length = 200)
    private String email;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqEmailId")
    @SequenceGenerator(name="seqEmailId", sequenceName="EMAIL_ID", allocationSize=1)
    @Column(name="EMAIL_ID", nullable = false)
    private Long emailId;
    private Long event;
    @Column(length = 200)
    private String fax;
    @Column(name="FIRST_NAME", length = 200)
    private String firstName;
    @Column(name="LAST_NAME", length = 200)
    private String lastName;
    private Long law;
    private Long read;
    @Column(name="REPLY_METHOD")
    private Long replyMethod;
    private Long status;
    @Column(length = 500)
    private String subject;
    @Column(name="TAX_PAYER_TYPE")
    private Long taxPayerType;
    @Column(name="TAX_TYPE")
    private Long taxType;
    @Column(name="UNIQUE_ID", length = 100)
    private String uniqueId;
    @ManyToOne
    @JoinColumn(name = "USER_CREATED_BY")
    private User userCreatedBy;
    @ManyToOne
    @JoinColumn(name = "USER_READ_BY")
    private User userReadBy;
    @OneToMany(mappedBy = "email")
    private List<LogEmail> logEmailList;
    @ManyToOne
    @JoinColumn(name = "USER_LOCKED_BY")
    private User userLockedBy;
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    @OrderBy("dateInsert")
    private Email parentEmail;

    @Transient
    private String currentStatus;
    

    @ManyToOne
    @JoinColumn(name = "USER_ANSWERED_BY")
    private User userAnsweredBy;

    public Email() {
    }

    public Email(String address, String comments, Timestamp dateUpdate,
                 String email, Long emailId, Long event, String fax,
                 String firstName, String lastName, Long law, Long read,
                 Long replyMethod, Long status, String subject,
                 Long taxPayerType, Long taxType, String uniqueId, User userCreatedBy,
                 User userLockedBy, User userReadBy, User userAnsweredBy) {
        this.address = address;
        this.comments = comments;
        this.dateUpdate = dateUpdate;
        this.email = email;
        this.emailId = emailId;
        this.event = event;
        this.fax = fax;
        this.firstName = firstName;
        this.lastName = lastName;
        this.law = law;
        this.read = read;
        this.replyMethod = replyMethod;
        this.status = status;
        this.subject = subject;
        this.taxPayerType = taxPayerType;
        this.taxType = taxType;
        this.uniqueId = uniqueId;
        this.userCreatedBy = userCreatedBy;
        this.userLockedBy = userLockedBy;
        this.userReadBy = userReadBy;
        this.userAnsweredBy = userAnsweredBy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Timestamp getDateInsert() {
        return dateInsert;
    }

    public void setDateInsert(Timestamp dateInsert) {
        this.dateInsert = dateInsert;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public Long getEvent() {
        return event;
    }

    public void setEvent(Long event) {
        this.event = event;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
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

    public Long getLaw() {
        return law;
    }

    public void setLaw(Long law) {
        this.law = law;
    }

    public Long getRead() {
        return read;
    }

    public void setRead(Long read) {
        this.read = read;
    }

    public Long getReplyMethod() {
        return replyMethod;
    }

    public void setReplyMethod(Long replyMethod) {
        this.replyMethod = replyMethod;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getTaxPayerType() {
        return taxPayerType;
    }

    public void setTaxPayerType(Long taxPayerType) {
        this.taxPayerType = taxPayerType;
    }

    public Long getTaxType() {
        return taxType;
    }

    public void setTaxType(Long taxType) {
        this.taxType = taxType;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }


    public User getUserCreatedBy() {
        return userCreatedBy;
    }

    public void setUserCreatedBy(User userCreatedBy) {
        this.userCreatedBy = userCreatedBy;
    }

    public User getUserReadBy() {
        return userReadBy;
    }

    public void setUserReadBy(User userReadBy) {
        this.userReadBy = userReadBy;
    }

    public List<LogEmail> getLogEmailList() {
        return logEmailList;
    }

    public void setLogEmailList(List<LogEmail> logEmailList) {
        this.logEmailList = logEmailList;
    }

    public LogEmail addLogEmail(LogEmail logEmail) {
        getLogEmailList().add(logEmail);
        logEmail.setEmail(this);
        return logEmail;
    }

    public LogEmail removeLogEmail(LogEmail logEmail) {
        getLogEmailList().remove(logEmail);
        logEmail.setEmail(null);
        return logEmail;
    }

    public User getUserLockedBy() {
        return userLockedBy;
    }

    public void setUserLockedBy(User userLockedBy) {
        this.userLockedBy = userLockedBy;
    }

    public User getUserAnsweredBy() {
        return userAnsweredBy;
    }

    public void setUserAnsweredBy(User userAnsweredBy) {
        this.userAnsweredBy = userAnsweredBy;
    }

    public Email getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(Email parentEmail) {
        this.parentEmail = parentEmail;
    }
    
    public String getCurrentStatus() {
        
        if (this.userReadBy == null) {
            return Email.STATUS_NEW;
        }
        
        if (this.userReadBy != null && this.userLockedBy == null && this.userAnsweredBy == null) {
            return Email.STATUS_READ;
        }

        if (this.userLockedBy != null && this.userAnsweredBy == null) {
            return Email.STATUS_LOCKED;
        }
        
        if (this.userAnsweredBy != null) {
            return Email.STATUS_ANSWERED;
        }
        
        return Email.STATUS_UNKNOWN;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Email)) {
            return false;
        }
        final Email other = (Email)object;
        if (!(emailId == null ? other.emailId == null : emailId.equals(other.emailId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((emailId == null) ? 0 : emailId.hashCode());
        return result;
    }
}
