/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entities.Session;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author eroot
 */
@Stateless
@LocalBean
public class SessionBean {

    @PersistenceContext(unitName="keti_sys-ejbPU")
    EntityManager em;
    
    public Session createSession(Session m) {
        this.em.persist(m);
        return m;
    }

    public Session updateSession(Session m) {
        this.em.merge(m);
        return m;
    }

    public void deleteSession(Session m) {
        this.em.remove(this.em.merge(m));
    }

    public Session findSession(String uid) {
        try {
            return (Session) this.em.find(Session.class, uid);
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Session> findSessions() {
        try {
            Query q=em.createNamedQuery("Session.findAll");
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Session> findSessionsByUser(String userId) {
        try {
            Query q=em.createNativeQuery("SELECT s.uid, s.date_debut, date_fin, token, s.id_user FROM session s WHERE s.id_user = ?");
            q.setParameter(1, userId);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public void deleteSessions(String uuid){
        List<Session> ls=findSessionsByUser(uuid);
        for(Session s:ls){
            deleteSession(s);
        }
    }
}
