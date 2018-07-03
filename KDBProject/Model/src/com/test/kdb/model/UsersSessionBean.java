package com.test.kdb.model;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.test.kdb.model.entity.Category;
import com.test.kdb.model.entity.DocSource;
import com.test.kdb.model.entity.Document;
import com.test.kdb.model.entity.Group;
import com.test.kdb.model.entity.Permission;
import com.test.kdb.model.entity.User;
import com.test.kdb.model.entity.Qa;

@Stateless(name = "UsersSession", mappedName = "KDB-Model-UsersSession")
@Local
public class UsersSessionBean implements UsersSessionLocal {
    @PersistenceContext(unitName="Model")
    private EntityManager em;

    public UsersSessionBean() {
    }

    public Object queryByRange(String jpqlStmt, int firstResult,
                               int maxResults) {
        Query query = em.createQuery(jpqlStmt);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public User persistUser(User user) {
        user.setActiveYn(user.getActiveYn());
        em.persist(user);
        return user;
    }

    public User findOrCreateUser(User user) {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public User mergeUser(User user) {
        user.setActiveYn(user.getActiveYn());
        return em.merge(user);
    }

    public void removeUser(User user) {
        user = em.find(User.class, user.getUserId());
        em.remove(user);
    }

    public Long changeUserPassword(User user, String passwordOld, String passwordNew1, String passwordNew2) {

        if (!user.getPassword().equals(passwordOld))
            /* invalid password */
            return 1L;

        if (!passwordNew1.equals(passwordNew2))
            /* new passwords do not match */
            return 2L;

        if (passwordNew1.length() < 5)
            /* new password is too short */
            return 3L;
        
        /* update password */
        user.setPassword(passwordNew1);
        em.merge(user);
        return 0L;
    }

    /** <code>select o from User o</code> */
    public List<User> listUser() {
        return em.createNamedQuery("User.findAll").getResultList();
    }

    /** <code>select o from User o</code> */
    public List<User> getUserFindAll() {
        return em.createNamedQuery("User.findAll").getResultList();
    }

    /** <code>select o from User o where o.username = :username</code> */
    public List<User> getUserFindByUsername(String username) {
        return em.createNamedQuery("User.findByUsername").setParameter("username", username.toLowerCase()).getResultList();
    }

    public List<Qa> getFavoriteQa(Long userId) {
        String hql = "SELECT u.favoriteQas FROM User u WHERE u.userId = :userId ";
        Query query = em.createQuery(hql);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    public List<Document> getFavoriteDoc(Long userId) {
        String hql = "SELECT u.favoriteDocuments FROM User u WHERE u.userId = :userId ";
        Query query = em.createQuery(hql);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public void addFavoriteQa(User user, Qa qa) {
        boolean alreadyExistsInFavorite = false;
        if  (user.getFavoriteQas() != null) {
            for (Qa tmpQa : user.getFavoriteQas()) {
                if (qa.getQaId() == tmpQa.getQaId()) {
                    alreadyExistsInFavorite = true;
                    break;
                }
            }
        }
        if (!alreadyExistsInFavorite) {
            user.addFavoriteQa(qa);
            em.merge(user);
        }
    }

    public void removeFavoriteQa(User user, Qa qa) {
        if  (user.getFavoriteQas() != null) {
            for (Qa tmpQa : user.getFavoriteQas()) {
                if (qa.getQaId() == tmpQa.getQaId()) {
                    user.getFavoriteQas().remove(tmpQa);
                    break;
                }
            }
        }
        em.merge(user);
        qa.removeUser(user);
        em.merge(qa);
    }

    public void addFavoriteDoc(User user, Document document) {
        boolean alreadyExistsInFavorite = false;
        if  (user.getFavoriteDocuments() != null) {
            for (Document tmpDoc : user.getFavoriteDocuments()) {
                if (document.getDocumentId() == tmpDoc.getDocumentId()) {
                    alreadyExistsInFavorite = true;
                    break;
                }
            }
        }
        if (!alreadyExistsInFavorite) {
            user.addFavoriteDocument(document);
            em.merge(user);
        }
    }

    public void removeFavoriteDoc(User user, Document document) {
        if  (user.getFavoriteDocuments() != null) {
            for (Document tmpDoc : user.getFavoriteDocuments()) {
                    if (document.getDocumentId() == tmpDoc.getDocumentId()) {
                    user.getFavoriteDocuments().remove(tmpDoc);
                    break;
                }
            }
        }
        em.merge(user);
        document.getUsers().remove(user);
        em.merge(document);
    }


    public Group findOrCreateGroup(Group group) {
        if (group == null) {
            group = new Group();
        }
        return group;
    }

    public Group persistGroup(Group group) {
        em.persist(group);
        return group;
    }

    public Group mergeGroup(Group group) {
        group.setActiveYn(group.getActiveYn());
        return em.merge(group);
    }

    public void removeGroup(Group group) {
        group = em.find(Group.class, group.getGroupId());
        em.remove(group);
    }

    /** <code>select o from Group o</code> */
    public List<Group> getGroupFindAll() {
        return em.createNamedQuery("Group.findAll").getResultList();
    }

    /** <code>select o from Group o</code> */
    public List<Group> listGroup() {
        return em.createNamedQuery("Group.findAll").getResultList();
    }

    public Permission persistPermission(Permission permission) {
        em.persist(permission);
        return permission;
    }

    public Permission mergePermission(Permission permission) {
        return em.merge(permission);
    }

    public void removePermission(Permission permission) {
        permission = em.find(Permission.class, permission.getPermissionId());
        em.remove(permission);
    }

    /** <code>select o from Permission o</code> */
    public List<Permission> getPermissionFindAll() {
        return em.createNamedQuery("Permission.findAll").getResultList();
    }

    /** <code>select o from Permission o</code> */
    public List<Permission> listPermission() {
        return em.createNamedQuery("Permission.findAll").getResultList();
    }

    public void userAddGroup(User user, Group group) {
        if (!user.getGroups().contains(group)) {
            user.getGroups().add(group);
            // group.getUsers().add(user);
            em.merge(user);
        }
    }
    
    public void groupAddUser(User user, Group group) {
        if (!group.getUsers().contains(user)) {
            group.getUsers().add(user);
            // user.getGroups().add(group);
            em.merge(group);
        }
    }
    
    public void userRemoveGroup(User user, Group group) {
        user.getGroups().remove(group);
        // group.getUsers().remove(user);
        em.merge(user);
    }

    public void groupRemoveUser(User user, Group group) {
        group.getUsers().remove(user);
        // user.getGroups().remove(group);
        em.merge(group);
    }

    public void groupAddPermission(Group group, Permission permission) {
        if (!group.getPermissions().contains(permission)) {
            group.getPermissions().add(permission);
            // permission.getGroups().add(group);
            em.merge(group);
        }
    }
    
    public void groupRemovePermission(Group group, Permission permission) {
        group.getPermissions().remove(permission);
        // permission.getGroups().remove(group);
        em.merge(group);
    }
    
    public boolean userLikeQa(Long userId, Qa qa) {
            boolean like = false;
            String hql = "SELECT u.favoriteQas FROM User u WHERE u.userId = :userId ";
            Query query = em.createQuery(hql);
            query.setParameter("userId", userId);
            List<Qa> likes = query.getResultList();
            if(likes.contains(qa)){
                like = true;
            }
            return like;
        }
    
    public boolean userQaUseful(Long userId, Qa qa) {
            boolean useful = false;
            String hql = "SELECT u.usefulQas FROM User u WHERE u.userId = :userId ";
            Query query = em.createQuery(hql);
            query.setParameter("userId", userId);
            List<Qa> usefuls = query.getResultList();
            if(usefuls.contains(qa)){
                useful = true;
            }
            return useful;
        }
    
    public boolean userLikeDoc(Long userId, Document document) {
            boolean like = false;
            String hql = "SELECT u.favoriteDocuments FROM User u WHERE u.userId = :userId ";
            Query query = em.createQuery(hql);
            query.setParameter("userId", userId);
            List<Document> likes = query.getResultList();
            if(likes.contains(document)){
                like = true;
            }
            return like;
        }
    
    public boolean userDocUseful(Long userId, Document document) {
            boolean useful = false;
            String hql = "SELECT u.usefulDocuments FROM User u WHERE u.userId = :userId ";
            Query query = em.createQuery(hql);
            query.setParameter("userId", userId);
            List<Document> usefuls = query.getResultList();
            if(usefuls.contains(document)){
                useful = true;
            }
            return useful;
        }    
}
