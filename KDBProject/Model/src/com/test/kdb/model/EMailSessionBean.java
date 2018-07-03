package com.test.kdb.model;

import java.sql.Timestamp;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.test.kdb.model.entity.Email;

import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.*;

import com.test.kdb.model.entity.Config;
import com.test.kdb.model.entity.User;

@Stateless(name = "EMailSession", mappedName = "KDB-Model-EMailSession")
@Local
public class EMailSessionBean implements EMailSessionLocal {
    @PersistenceContext(unitName="Model")
    private EntityManager em;

    public EMailSessionBean() {
    }

    public Email persistEmail(Email email) {
        em.persist(email);
        return email;
    }

    public Email mergeEmail(Email email) {
        return em.merge(email);
    }

    public void removeEmail(Email email) {
        email = em.find(Email.class, email.getEmailId());
        em.remove(email);
    }

    /** <code>select o from Email o</code> */
    public List<Email> getEmailFindAll() {
        return em.createNamedQuery("Email.findAll").getResultList();
    }

    /** <code>select o from Email o where status = 1 order by e.dateInsert desc</code> */
    public List<Email> getEmailIncomingMessages(Integer startFrom, Integer maxResults) {
        return em.createNamedQuery("Email.incomingMessages").getResultList();
    }

    /** <code>select o from Email o where status = 1 and userReadBy is null order by e.dateInsert desc</code> */
    public List<Email> getEmailNewMessages(Integer startFrom, Integer maxResults) {
        return em.createNamedQuery("Email.newMessages").getResultList();
    }

    /** <code>select o from Email o where status = 1 and userAnsweredBy is not null order by e.dateInsert desc</code> */
    public List<Email> getEmailAnsweredMessages(Integer startFrom, Integer maxResults) {
        return em.createNamedQuery("Email.answeredMessages").getResultList();
    }

    /** <code>select o from Email o where status = 1 and userAnsweredBy is null order by e.dateInsert desc</code> */
    public List<Email> getEmailNotAnsweredMessages(Integer startFrom, Integer maxResults) {
        return em.createNamedQuery("Email.notAnsweredMessages").getResultList();
    }

    /** <code>select o from Email o where status = 1 and userLockedBy is not null order by e.dateInsert desc</code> */
    public List<Email> getEmailLockedMessages(Integer startFrom, Integer maxResults) {
        return em.createNamedQuery("Email.lockedMessages").getResultList();
    }
    
    /** <code>select e from Email e where e.status = 2 order by e.dateInsert desc</code> */
    public List<Email> getEmailDraftMessages(Integer startFrom, Integer maxResults) {
        return em.createNamedQuery("Email.draftMessages").getResultList();
    }

    /** <code>select e from Email e where e.status = 3 order by e.dateInsert desc</code> */
    public List<Email> getEmailSentMessages(Integer startFrom, Integer maxResults) {
        return em.createNamedQuery("Email.sentMessages").getResultList();
    }

    /** <code>select e from Email e where e.status = 4 and e.userLockedBy is not null order by e.dateInsert desc</code> */
    public List<Email> getEmailTrashdMessages(Integer startFrom, Integer maxResults) {
        return em.createNamedQuery("Email.trashdMessages").getResultList();
    }

    /** <code>select e from Email e where e.status = 5 and e.userLockedBy is not null order by e.dateInsert desc</code> */
    public List<Email> getEmailArchivedMessages(Integer startFrom, Integer maxResults) {
        return em.createNamedQuery("Email.archivedMessages").getResultList();
    }

    public Email loadOrCreateDraft(Email email, User user) {
        if (email.getStatus().longValue() == Email.INCOMMING.longValue()) {
            // Create new Draft
            Email draft = new Email();
            draft.setEmail(email.getEmail());
            draft.setSubject(email.getSubject());
            draft.setUniqueId(email.getUniqueId());
            
            Config emailReplyStandardText = em.find(Config.class, "emailReplyStandardText");
            String replyLink = "";
            if (emailReplyStandardText != null ) {
               //  replyLink = MailWebServiceBean.replaceMacros(email, emailReplyStandardText.getValue());
               replyLink = emailReplyStandardText.getValue().replace("$$MESSAGE_ID$$", email.getUniqueId());
            }
            String body = "<BR>\n<BR>\n" +
                          replyLink + "\n" +
                          "<BR>\n---<BR>\n" + 
                          email.getBody();
            draft.setBody(body);
            draft.setUserCreatedBy(user);
            draft.setStatus(Email.DRAFT);
            draft.setDateInsert(new Timestamp(new Date().getTime()));
            draft.setLaw(email.getLaw());
            draft.setTaxPayerType(email.getTaxPayerType());
            draft.setParentEmail(email);
            em.persist(draft);
            lockMail(email, user);
            return draft;
        } else {
            // editDraft
            return email;
        }
    }

    public Email lockMail(Email eMail, User u) {
        if (eMail.getUserLockedBy() == null) {
            eMail.setUserLockedBy(u);
        } else {
            eMail.setUserLockedBy(null);
        }
        eMail.setDateUpdate(new java.sql.Timestamp(new Date().getTime()));
        eMail = em.merge(eMail);
        return eMail;
    }

    public Email readMail(Email eMail, User u) {
        if (eMail.getUserReadBy() == null) {
            eMail.setUserReadBy(u);
            eMail.setDateUpdate(new java.sql.Timestamp(new Date().getTime()));
            eMail = em.merge(eMail);
        }
        return eMail;
    }
    
    public Email findById(Long emailId) {
        if (emailId != null ) {
            return em.find(Email.class, emailId);
        } else {
            return null;
        }
    }
    
    public Boolean sendEmail(String emailFrom, String emailTo, String emailBcc, String subject, String messageText) {
        InitialContext ic;
        Session session;
        Config configMailFrom;
        Config configMailBcc;
        
        if (emailFrom == null || emailFrom.isEmpty()) {
            /* get mailFrom from database */
            configMailFrom = (Config)em.createNamedQuery("Config.findByKey").
                        setParameter("key", "emailFrom").
                        getResultList().get(0);
            emailFrom = configMailFrom.getValue();
        }
        if (emailBcc == null || emailBcc.isEmpty()) {
            /* get mailBcc from database */
            configMailBcc = (Config)em.createNamedQuery("Config.findByKey").
                        setParameter("key", "emailBcc").
                        getResultList().get(0);
            emailBcc = configMailBcc.getValue();
        }
        try {
            ic = new InitialContext();
            session = (Session) ic.lookup("kdbMail");
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFrom));
            message.setRecipients(Message.RecipientType.TO,
                              InternetAddress.parse(emailTo, false));
            if (emailBcc != null && !emailBcc.isEmpty()) {
                // bcc address given
                message.setRecipients(Message.RecipientType.BCC,
                                  InternetAddress.parse(emailBcc, false));
            }
            message.setSubject(subject, "UTF-8");
            message.setSentDate(new Date());

            // Content is stored in a MIME multi-part message
            // with one body part
            MimeBodyPart bodypart = new MimeBodyPart();
            bodypart.setContent(messageText, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodypart);
            message.setContent(multipart);
            
            Transport.send(message);
            
        } catch (NamingException e) {
            // sending failed
            e.printStackTrace();
            return false;
            
        } catch (MessagingException e) {
            // sending failed
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean sendEmail(Email email, User user) {
        Boolean sent = sendEmail(null, email.getEmail(), null, email.getSubject(), email.getBody());
        if (sent) {
            if (email.getParentEmail() != null) {
                Email originalEmail = email.getParentEmail();
                originalEmail.setUserAnsweredBy(user);
                originalEmail = em.merge(originalEmail);
                email.setParentEmail(originalEmail);
            }
            email.setStatus(Email.OUTGOING);
            em.merge(email);
        }
        return sent;
    }

    /** <code>select e from Email e where e.uniqueId = :uniqueId order by e.dateInsert </code> */
    public List<Email> getEmailThreadMessages(String uniqueId) {
        return em.createNamedQuery("Email.threadMessages").setParameter("uniqueId", uniqueId).getResultList();
    }
    
    public List<User> getEmailUsers(Boolean allUsers) {
        String query = "SELECT u FROM User u ";
        return em.createQuery(query).getResultList();
    }
    
    public Email assignEMailToUser(Email eMail, User user) {
        eMail.setUserLockedBy(user);
        eMail.setDateUpdate(new java.sql.Timestamp(new Date().getTime()));
        eMail = em.merge(eMail);
        return eMail;
    }

    /** <code>select e from Email e where e.status = 1 and e.userLockedBy = :user and  e.userAnsweredBy is null order by e.dateInsert desc</code> */
    public List<Email> getEmailUserMessages(User user) {
        if (user != null) {
            return em.createNamedQuery("Email.userMessages").setParameter("user", user).getResultList();
        } else {
            return em.createNamedQuery("Email.notAssignedMessages").getResultList();
            
        }
    }
    
    public List<Email> getEmailByTerm(String term, Boolean searchBody) {
        String query = "select e from Email e " +
            "where e.status = 1 and " +
            "(e.subject like :term " +
            (searchBody?" or e.body like :term ":"") +
            " or e.uniqueId = :strictTerm" +
            " or e.address like :term " +
            " or e.email = :strictTerm " +
            ")";
        List<Email> result = em.createQuery(query)
                .setParameter("term", "%" + term + "%")
                .setParameter("strictTerm", term)
                .getResultList();
        
        return result;
    }
    
}
