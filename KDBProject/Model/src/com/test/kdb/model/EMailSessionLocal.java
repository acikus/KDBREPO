package com.test.kdb.model;

import java.util.List;

import javax.ejb.Local;

import com.test.kdb.model.entity.Email;
import com.test.kdb.model.entity.User;

@Local
public interface EMailSessionLocal {
    Email persistEmail(Email email);

    Email mergeEmail(Email email);

    void removeEmail(Email email);

    List<Email> getEmailFindAll();

    List<Email> getEmailIncomingMessages(Integer startFrom, Integer maxResults);

    List<Email> getEmailNewMessages(Integer startFrom, Integer maxResults);

    List<Email> getEmailAnsweredMessages(Integer startFrom, Integer maxResults);

    List<Email> getEmailNotAnsweredMessages(Integer startFrom, Integer maxResults);

    List<Email> getEmailLockedMessages(Integer startFrom, Integer maxResults);

    List<Email> getEmailDraftMessages(Integer startFrom, Integer maxResults);

    List<Email> getEmailSentMessages(Integer startFrom, Integer maxResults);

    List<Email> getEmailTrashdMessages(Integer startFrom, Integer maxResults);

    List<Email> getEmailArchivedMessages(Integer startFrom, Integer maxResults);
    
    Email readMail(Email eMail, User u);
    
    Email lockMail(Email eMail, User u);
    
    Email loadOrCreateDraft(Email email, User user);
    
    Email findById(Long emailId);
    
    Boolean sendEmail(String emailFrom, String emailTo, String emailBcc, String subject, String messageText);
    
    Boolean sendEmail(Email email, User user);

    List<Email> getEmailThreadMessages(String uniqueId);
    
    List<User> getEmailUsers(Boolean allUsers);
    
    Email assignEMailToUser(Email eMail, User user);

    List<Email> getEmailUserMessages(User user);
    
    List<Email> getEmailByTerm(String term, Boolean searchBody);
}
