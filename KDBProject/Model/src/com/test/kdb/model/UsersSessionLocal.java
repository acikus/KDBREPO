package com.test.kdb.model;

import java.util.List;

import javax.ejb.Local;

import com.test.kdb.model.entity.Document;
import com.test.kdb.model.entity.Group;
import com.test.kdb.model.entity.Permission;
import com.test.kdb.model.entity.Qa;
import com.test.kdb.model.entity.User;

@Local
public interface UsersSessionLocal {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    User findOrCreateUser(User user);
    
    User persistUser(User user);

    User mergeUser(User user);

    void removeUser(User user);
    
    Long changeUserPassword(User user, String passwordOld, String passwordNew1, String passwordNew2);

    List<User> listUser();
    
    List<User> getUserFindAll();

    List<User> getUserFindByUsername(String username);

    Group findOrCreateGroup(Group group);
    
    Group persistGroup(Group group);

    Group mergeGroup(Group group);

    void removeGroup(Group group);

    List<Group> listGroup();
    
    List<Group> getGroupFindAll();

    Permission persistPermission(Permission permission);

    Permission mergePermission(Permission permission);

    void removePermission(Permission permission);

    List<Permission> listPermission();
    
    List<Permission> getPermissionFindAll();
    
    List<Qa> getFavoriteQa(Long userId);
    
    List<Document> getFavoriteDoc(Long userId);
    
    void addFavoriteQa(User user, Qa qa);
    
    void removeFavoriteQa(User user, Qa qa);

    void addFavoriteDoc(User user, Document document);

    void removeFavoriteDoc(User user, Document document);

    void userAddGroup(User user, Group group);
    
    void groupAddUser(User user, Group group);
    
    void userRemoveGroup(User user, Group group);
    
    void groupRemoveUser(User user, Group group);

    void groupAddPermission(Group group, Permission permission);
    
    void groupRemovePermission(Group group, Permission permission);
    
    boolean userLikeQa(Long userId, Qa qa);
    
    boolean userQaUseful(Long userId, Qa qa);
    
    boolean userLikeDoc(Long userId, Document doc);
    
    boolean userDocUseful(Long userId, Document doc);             
}
