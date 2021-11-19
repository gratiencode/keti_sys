/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  beans.stateless.EngagerBean
 *  entities.Engager
 *  javax.ejb.EJB
 *  javax.enterprise.context.RequestScoped
 *  javax.enterprise.event.Observes
 *  javax.enterprise.inject.Produces
 *  jaxrs.AuthUserProducer
 *  util.AuthenticatedUser
 */
package jaxrs;


import entities.User;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import stateless.UserBean;
import util.AuthenticatedUser;

@RequestScoped
public class AuthUserProducer {
    @EJB
    UserBean usb;
    @Produces
    @RequestScoped
    @AuthenticatedUser
    private User authenticatedUser;

    public void handleAuthenticationEvent(@Observes @AuthenticatedUser String uid) {
        //if (!uid.contains("has expired")) {
            this.authenticatedUser = this.findEmployee(uid);
       // }
    }

    private User findEmployee(String uid) {
        User u=usb.findUser(uid);
        return u;
    }
}

