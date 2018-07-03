package com.test.kdb.model.usermanagement;

import java.util.List;

import javax.ejb.Local;

import com.test.kdb.model.entity.User;


@Local
public interface UserManager {
    public User getUserByUsername(String username);
//    public List<User> findUsersByParams(String username, String group, String fname, String lname, String ouId, String email,
//                                               int firstResult, int maxResults);
}
