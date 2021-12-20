/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entities.Voyage;
import java.util.Calendar;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author eroot
 */
@Stateless
@LocalBean
public class VoyageBean {
    @PersistenceContext(unitName = "keti_sys-ejbPU")
    EntityManager em;

    public Voyage createVoyage(Voyage m) {
        this.em.persist(m);
        return m;
    }

    public Voyage updateVoyage(Voyage m) {
        this.em.merge(m);
        return m;
    }

    public void deleteVoyage(Voyage m) {
        this.em.remove(this.em.merge(m));
    }

    public Voyage findVoyage(String uid) {
        try {
            return (Voyage) this.em.find(Voyage.class, uid);
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Voyage> findVoyageByEtat(String etat) {
        try {
            Query q = em.createNamedQuery("Voyage.findByEtat")
                    .setParameter("etat", etat);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Voyage> findVoyageByVehicule(String vehicule) {
        try {
            StringBuilder sb=new StringBuilder();
            sb.append("SELECT v.uid, v.etat, v.date_arriver, v.succursalle_id, v.vehicule_id ")
                    .append("FROM voyage v ")
                    .append("WHERE v.vehicule_id = ?");
            Query q = em.createNativeQuery("Voyage.findByEtat")
                    .setParameter(1, vehicule);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Voyage> findVoyageByEtat(Calendar c) {
        try {
            Query q = em.createNamedQuery("Voyage.findByDateArriver")
                    .setParameter("dateArriver", c,TemporalType.TIMESTAMP);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Voyage> findVoyages() {
        try {
            Query q = em.createNamedQuery("Voyage.findAll");
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
