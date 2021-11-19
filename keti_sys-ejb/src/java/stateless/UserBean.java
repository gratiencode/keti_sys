/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entities.User;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import util.ServerCrypt;

/**
 *
 * @author eroot
 */
@Stateless
@LocalBean
public class UserBean {

    @PersistenceContext(unitName = "keti_sys-ejbPU")
    EntityManager em;

    public User createUser(User m) {
        this.em.persist(m);
        return m;
    }
    
    public User createSecured(User m){
        m.setPassword(ServerCrypt.generateSecuredPassword(m.getPassword()));
        return createUser(m);
    }

    public User updateUser(User m) {
        this.em.merge(m);
        return m;
    }

    public void deleteUser(User m) {
        this.em.remove(this.em.merge(m));
    }

    public User findUser(String uid) {
        try {
            return (User) this.em.find(User.class, uid);
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<User> findUserByUsername(String uname) {
        try {
            Query q = em.createNamedQuery("User.findByUsername")
                    .setParameter("username", uname);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User prelogin(String username) {
        try {
            Query q = em.createNamedQuery("User.findByUsername")
                    .setParameter("username", username);
            return (User) q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public User login(String username, String paswd) {
        boolean log = false;
        try {
            User u = prelogin(username);
            log = ServerCrypt.checkSecuredPassword(username, u.getPassword());
            return log ? u : null;
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<User> findUserByPhone(String uidSucursal) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT u.uid, u.created_at, u.username, u.password, u.prenom, u.nom, u.id_sucursale, u.role ")
                    .append("FROM user u ").append("WHERE u.id_sucursale = ?");
            Query q = em.createNativeQuery(sb.toString(), User.class)
                    .setParameter(1, uidSucursal);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
