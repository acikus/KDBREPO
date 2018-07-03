package com.test.kdb.model;

import java.util.List;

import javax.ejb.Remote;

import com.test.kdb.model.entity.DocExtension;
import com.test.kdb.model.entity.Email;
import com.test.kdb.model.entity.LogEmail;

@Remote
public interface MailWebService {
    Boolean submitNewEmail(String username, String password,
                                      String subject, 
                                      String body, 
                                      Long law,
                                      Long taxType,
                                      Long taxPayerType,
                                      Long event,
                                      Long replyMethod,
                                      String firstName,
                                      String lastName,
                                      String address,
                                      String phone,
                                      String email,
                                      String uniqueId);
}
