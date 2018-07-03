package com.test.kdb.model;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

import com.test.kdb.model.entity.Config;
import com.test.kdb.model.entity.DocExtension;
import com.test.kdb.model.entity.Email;
import com.test.kdb.model.entity.LogEmail;
import com.test.kdb.model.entity.User;

@Stateless(name = "MailSession", mappedName = "BazaZnanja-Model-MailSession")
@Remote
@Local
@WebService(name = "Service", serviceName = "Service", portName = "ServicePort")
public class MailWebServiceBean implements MailWebService, MailWebServiceLocal {
    @PersistenceContext(unitName = "Model")
    private EntityManager em;
    @EJB(name = "EMailSessionBean")
    private EMailSessionLocal emailSessionLocal;

    public MailWebServiceBean() {
    }

    private String replaceMacros(Email email, String text) {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        String result;

        Date dateInsert = new Date(email.getDateInsert().getTime());
        result = text.
            replace("$$FIRST_NAME$$", email.getFirstName()).
            replace("$$LAST_NAME$$", email.getLastName()).
            replace("$$MESSAGE_TIMESTAMP$$", df.format(dateInsert)).
            replace("$$MESSAGE_ID$$", email.getUniqueId());
        return result;
    }

    @WebMethod
    public Boolean submitNewEmail(@WebParam(name = "username")
        String username, @WebParam(name = "password")
        String password, @WebParam(name = "eSubject")
        String subject, @WebParam(name = "eBody")
        String body, @WebParam(name = "law")
        Long law, @WebParam(name = "taxType")
        Long taxType, @WebParam(name = "taxPayerType")
        Long taxPayerType, @WebParam(name = "eEvent")
        Long event, @WebParam(name = "replyMethod")
        Long replyMethod, @WebParam(name = "firstName")
        String firstName, @WebParam(name = "lastName")
        String lastName, @WebParam(name = "address")
        String address, @WebParam(name = "phone")
        String phone, @WebParam(name = "emailAddress")
        String email, @WebParam(name = "uniqueId")
        String uniqueId) {

        User user;
        Boolean result;
        
        try {
            user = (User)em.createNamedQuery("User.findByUsername").setParameter("username", username).getSingleResult();
            if (!user.getPassword().equals(password)) {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
        
        /* persist e-mail */
        Email newEmail = new Email();
        newEmail.setAddress(address);
        newEmail.setBody(body);
        newEmail.setEmail(email);
        newEmail.setEvent(event);
        newEmail.setFax(phone);
        newEmail.setFirstName(firstName);
        newEmail.setLastName(lastName);
        newEmail.setLaw(law);
        newEmail.setRead(0L);
        newEmail.setReplyMethod(replyMethod);
        newEmail.setSubject(subject);
        newEmail.setTaxPayerType(taxPayerType);
        newEmail.setTaxType(taxType);
        newEmail.setUserCreatedBy(user);
        newEmail.setDateInsert(new java.sql.Timestamp(new Date().getTime()));
        newEmail.setDateUpdate(new java.sql.Timestamp(new Date().getTime()));
        if (uniqueId == null || uniqueId.equals("")) {
            UUID id = UUID.randomUUID();
            uniqueId = String.valueOf(id);
        }
        newEmail.setUniqueId(uniqueId);
        newEmail.setStatus(1L); // 1 - incoming
        em.persist(newEmail);
        
        result = true;
        if (email != null && ! email.isEmpty()) {
            String emailSubject;
            String emailBody;
            
            /* get subject and body templates */
            emailSubject = ((Config)em.createNamedQuery("Config.findByKey").
                setParameter("key", "emailReceivedReplySubject").
                getResultList().get(0)).
                getValue();
            emailBody = ((Config)em.createNamedQuery("Config.findByKey").
                setParameter("key", "emailReceivedReplyBody").
                getResultList().get(0)).
                getValue();
            
            /* macro substitution */
            emailSubject = replaceMacros(newEmail, emailSubject);
            emailBody = replaceMacros(newEmail, emailBody);
            
            /* send e-mail */
            result = emailSessionLocal.sendEmail(
                null, 
                email, 
                null, 
                emailSubject, 
                emailBody);
        }
        return result;
    }


}
