package com.test.kdb.view.login;

import java.io.IOException;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;

import java.util.List;

import java.util.Set;

import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.adf.share.ADFContext;

import com.test.kdb.model.UsersSessionLocal;
import com.test.kdb.model.entity.User;
import com.test.kdb.model.usermanagement.UserManagerAD;

import com.test.kdb.view.JSFUtils;

import weblogic.security.URLCallbackHandler;
import weblogic.security.principal.WLSUserImpl;
import weblogic.security.services.Authentication;

import weblogic.servlet.security.ServletAuthentication;


public class LoginHandler {

    private String username;
    private String password;
    private String saltKey = "x21t46659";

    public LoginHandler() {
        super();
    }


    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return
     */
    public String performLogin() {
        String un = ((getUsername() == null) ? "" : getUsername());
        String pw = ((getPassword() == null) ? "" : getPassword());
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)ctx.getExternalContext().getRequest();
        CallbackHandler handler = new URLCallbackHandler(un, pw.getBytes());
        try {
            Subject mySubject = Authentication.login(handler);
            Set<Principal> principals = mySubject.getPrincipals();

            ServletAuthentication.runAs(mySubject, request);
            ServletAuthentication.generateNewSessionID(request);

            User k = null;

            for (Principal p : principals) {
                if (p instanceof WLSUserImpl) {
                    WLSUserImpl user = (WLSUserImpl)p;
                    UserManagerAD Um = new UserManagerAD();
                    k = Um.getUserByUsername(user.getName());
                }
            }
            if (k != null) {
                JSFUtils.storeOnSession("currentUser", k);
                // ako je autentikacija uspesna (nema greske), onda se vrsi
                // inicijalizacija podataka o ulogovanom korisniku u sesiji
                // redirekcija na ADFAutenthication servlet koji dalje vrsi redirekciju na naslovnu stranu
                HttpServletResponse response = (HttpServletResponse)ctx.getExternalContext().getResponse();
                loadUser();
                sendForward(request, response, "/adfAuthentication?success_url=/faces/home.jspx");

            } else {
                FacesMessage msg =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Неуспело логовање. Проверите корисничко име и лозинку!");
                ctx.addMessage(null, msg);
            }
        } catch (FailedLoginException fle) {
            FacesMessage msg =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Неуспело логовање. Проверите корисничко име и лозинку!");

            ctx.addMessage(null, msg);
        } catch (LoginException le) {
            reportUnexpectedLoginError("LoginException", le);
        }
        return null;
    }


    private void sendForward(HttpServletRequest request, HttpServletResponse response, String forwardUrl) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardUrl);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException se) {
            reportUnexpectedLoginError("ServletException", se);
        } catch (IOException ie) {
            reportUnexpectedLoginError("IOException", ie);
        }
        ctx.responseComplete();
    }

    private void reportUnexpectedLoginError(String errType, Exception e) {
        FacesMessage msg =
            new FacesMessage(FacesMessage.SEVERITY_ERROR, " ", "Грешка при логовању!" + " " +
                             errType);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        e.printStackTrace();
    }


    /**
     * @return
     */
    public String performLogout() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();
        String url = ectx.getRequestContextPath() + "/adfAuthentication?logout=true&end_url=/faces/home.jspx";
        try {
            ectx.redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fctx.responseComplete();
    return null;
    }


    /**
     * @param x
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String x) throws Exception {
        java.security.MessageDigest d = null;
        d = java.security.MessageDigest.getInstance("SHA-1");
        d.reset();
        d.update(x.getBytes());
        return d.digest();
    }

    private void loadUser() {

        UsersSessionLocal userssSessionEJB;

        try {
            final Context ctx = new InitialContext();
            userssSessionEJB = (UsersSessionLocal)ctx.lookup("java:comp/env/ejb/local/UsersSession");
            List<User> users = userssSessionEJB.getUserFindByUsername(username);
            //  PasswordEncoder pe = PasswordEncoder.getInstance();
            if (users != null && users.size() > 0) {

                ADFContext.getCurrent().getSessionScope().put("currentUser", users.get(0));
                
                //                BASE64Decoder decoder = new BASE64Decoder();
                //                byte[] returnbyteArray;
                //                try {
                //                    returnbyteArray = decoder.decodeBuffer(users.get(0).getPassword());
                //                } catch (IOException e) {
                //                }
                // byte[] strDecodedPass = pe.base64ToByte(users.get(0).getPassword());
            } else {

                UserManagerAD Um = new UserManagerAD();
                String uuid = UUID.randomUUID().toString();

//                PasswordEncoder pe=  PasswordEncoder.getInstance();
//                                try {
//                
//                                    password = pe.encode(password, saltKey);
//                                } catch (NoSuchAlgorithmException e) {
//                                } catch (IOException e) {
//                                }
                // Um.setPass(uuid);
                User newUser = Um.getUserByUsername(username);
                newUser.setPassword(uuid);
                userssSessionEJB.persistUser(newUser);
                //List<User> usersAD = userssSessionEJB.getUserFindByUsername(username);
                ADFContext.getCurrent().getSessionScope().put("currentUser", newUser);
            }
        } catch (NamingException e) {
            System.out.println("Naming exception occurred: " + e.getMessage());
        }
    }
}
