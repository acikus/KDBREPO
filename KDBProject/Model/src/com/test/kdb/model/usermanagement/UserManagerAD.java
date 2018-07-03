package com.test.kdb.model.usermanagement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.test.kdb.model.entity.User;
import com.test.kdb.model.utils.ModelUtils;


//*******************************************************************
//Produkcija
//*******************************************************************
public class UserManagerAD implements UserManager {


    private LdapContext ctx = null;

    private String adTarget = null;
    private String adRoot = null;


    String[] attrIDs =
    { "sAMAccountName", "distinguishedName", "sn", "givenName", "description", "ouName", "extensionAttribute1",
      "extensionAttribute15", "mail", "telephoneNumber", "title", "department", "memberOf" };

    public UserManagerAD() {
    }

    private void connectToLdap() {
        try {
            Hashtable env = new Hashtable();
            adTarget = ModelUtils.getStringFromBundle("TARGET_AD");
            adRoot = ModelUtils.getStringFromBundle("AD_ROOT_" + adTarget);
            //  adTarget = "";
            // adRoot = "DC=PURS,DC=LOCAL";

            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.SECURITY_AUTHENTICATION, "Simple");
            env.put(Context.PROVIDER_URL, "ldap://" + ModelUtils.getStringFromBundle("PROVIDER_URL_" + adTarget));
            env.put(Context.SECURITY_PRINCIPAL, ModelUtils.getStringFromBundle("SECURITY_PRINCIPAL_" + adTarget) + "," + adRoot);
            env.put(Context.SECURITY_CREDENTIALS, ModelUtils.getStringFromBundle("SECURITY_CREDENTIALS_" + adTarget));
            env.put(Context.REFERRAL, "ignore");


            this.ctx = new InitialLdapContext(env, null);
            System.out.println("Connection Successful.");
        } catch (NamingException nex) {
            System.out.println("AD Connection: FAILED");
            nex.printStackTrace();

        }
    }

    private void disconnectFromLdap() {
        if (this.ctx != null) {
            try {
                this.ctx.close();
            } catch (NamingException e) {
                e.printStackTrace();
            }
            this.ctx = null;
        }
    }

    private User getUserBasicAttributes(String username) {
        User k = null;
        Set memberOf = new HashSet();
        try {
            this.connectToLdap();
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            constraints.setReturningAttributes(attrIDs);
            String subUserLocation = ModelUtils.getStringFromBundle("KDB_USERS_AD_LOCATION_" + adTarget);
            String userLocation = (subUserLocation.isEmpty() ? "" : (subUserLocation + ",")) + adRoot;
            NamingEnumeration answer = this.ctx.search(userLocation, "sAMAccountName=" + username, constraints);
            if (answer.hasMore()) {

                Attributes attrs = ((SearchResult)answer.next()).getAttributes();
                if (attrs != null) {
                    Attribute memberOfAttr = attrs.get("memberOf");

                    if (memberOfAttr != null) {
                        NamingEnumeration rolesEnum = memberOfAttr.getAll();

                        while (rolesEnum.hasMoreElements()) {
                            Object role = rolesEnum.nextElement();

                            // save group names into group list
                            memberOf.add(role.toString());
                        }
                    }
                }
                k = createUser(attrs);

            } else {
                throw new Exception("Invalid User");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.disconnectFromLdap();
        }
        return k;
    }


    public User getUserByUsername(String username) {
        return getUserBasicAttributes(username);
    }


    public List<User> findUsersByParams(String username, String group, String fname, String lname, String ouId,
                                        String ouName, String jmbg, String email, int firstResult, int maxResults) {
        if ((username == null) && (group == null) && (fname == null) && (lname == null) && (ouId == null) &&
            (ouName == null) && (jmbg == null) && (email == null))
            return new ArrayList<User>();

        List<User> result = new ArrayList<User>();

        try {
            this.connectToLdap();

            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            constraints.setReturningAttributes(attrIDs);

            String subUserLocation = ModelUtils.getStringFromBundle("KDB_USERS_AD_LOCATION_" + adTarget);
            String userLocation = (subUserLocation.isEmpty() ? "" : (subUserLocation + ",")) + adRoot;

            String subGroupsLocation = ModelUtils.getStringFromBundle("KDB_GROUPS_AD_LOCATION_" + adTarget);
            String groupsLocation = (subGroupsLocation.isEmpty() ? "" : (subGroupsLocation + ",")) + adRoot;

            //{ "sAMAccountName", "distinguishedName", "sn", "givenName", "description", "extensionAttribute1", "memberOf" }
            StringBuilder sb = new StringBuilder();

            if (username != null && !username.isEmpty()) {
                sb.append("(&(sAMAccountName=" + username + ")");
            }

            if (group != null && !group.isEmpty()) {
                if (sb.length() == 0)
                    sb.append("(&");
                sb.append("(memberOf=" + "CN=" + group + "," + groupsLocation + ")");
            }
            if (fname != null && !fname.isEmpty()) {
                if (sb.length() == 0)
                    sb.append("(&");
                sb.append("(givenName=" + fname + ")");
            }
            if (lname != null && !lname.isEmpty()) {
                if (sb.length() == 0)
                    sb.append("(&");
                sb.append("(sn=" + lname + ")");
            }
            if (email != null && !email.isEmpty()) {
                if (sb.length() == 0)
                    sb.append("(&");
                sb.append("(mail=" + email + ")");
            }
            if (ouId != null && !ouId.isEmpty()) {
                if (sb.length() == 0)
                    sb.append("(&");
                sb.append("(extensionAttribute1=" + ouId + ")");
            }
            if (ouName != null && !ouName.isEmpty()) {
                if (sb.length() == 0)
                    sb.append("(&");
                sb.append("(department=" + ouName + ")");
            }
            if (jmbg != null && !jmbg.isEmpty()) {
                if (sb.length() == 0)
                    sb.append("(&");
                sb.append("(extensionAttribute15=" + jmbg + ")");
            }
            sb.append("(objectClass=user))");
            NamingEnumeration answer = this.ctx.search(userLocation, sb.toString(), constraints);

            if (firstResult > 0)
                for (int i = 0; i < firstResult && answer.hasMore(); i++) {
                    answer.next();
                }

            for (int i = 0; (maxResults == 0 || i < maxResults) && answer.hasMore(); i++) {

                Attributes attrs = ((SearchResult)answer.next()).getAttributes();
                result.add(createUser(attrs));

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.disconnectFromLdap();
        }

        return result;
    }


    private User createUser(Attributes attrs) {
        User k = new User();

        if (attrs.get("sAMAccountName") != null) {
            k.setUsername(attrs.get("sAMAccountName").toString().replaceFirst("sAMAccountName: ", ""));
        }

        if (attrs.get("givenName") != null) {
            k.setFirstName(attrs.get("givenName").toString().replaceFirst("givenName: ", ""));
        }

        if (attrs.get("sn") != null) {
            k.setLastName(attrs.get("sn").toString().replaceFirst("sn: ", ""));
        }

        if (attrs.get("mail") != null) {
            k.setEmail(attrs.get("mail").toString().replaceFirst("mail: ", ""));
        }
        if (attrs.get("telephoneNumber") != null) {
            k.setPhoneNumber(attrs.get("telephoneNumber").toString().replaceFirst("telephoneNumber: ", ""));
        }
        if (attrs.get("title") != null) {
            k.setTitle(attrs.get("title").toString().replaceFirst("title: ", ""));
        }
        if (attrs.get("company") != null) {
            k.setLocation(attrs.get("company").toString().replaceFirst("company: ", ""));
        }
        
        if (attrs.get("extensionAttribute1") != null) {
            k.setLocation(attrs.get("extensionAttribute1").toString().replaceFirst("extensionAttribute1: ", ""));
        }



        return k;
    }


}
