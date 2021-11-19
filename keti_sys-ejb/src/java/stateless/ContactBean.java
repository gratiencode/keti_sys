/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;


import entities.Contacts;
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
public class ContactBean {

    @PersistenceContext(unitName="keti_sys-ejbPU")
    EntityManager em;
    
    public Contacts createContacts(Contacts m) {
        this.em.persist(m);
        return m;
    }

    public Contacts updateContacts(Contacts m) {
        this.em.merge(m);
        return m;
    }

    public void deleteContacts(Contacts m) {
        this.em.remove(this.em.merge(m));
    }

    public Contacts findContacts(String uid) {
        try {
            return (Contacts) this.em.find(Contacts.class, uid);
        } catch (NoResultException e) {
            return null;
        }
    }

    public Contacts findContactsByPhone(String phone) {
        try {
            Query q=em.createNamedQuery("Contacts.findByPhone")
                    .setParameter("phone",phone);
            return (Contacts) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
}
