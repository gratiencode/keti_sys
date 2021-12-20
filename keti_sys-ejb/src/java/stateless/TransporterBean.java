/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entities.Transporter;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
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
public class TransporterBean {

    @PersistenceContext(unitName="keti_sys-ejbPU")
    EntityManager em;
    
    public Transporter createTransporter(Transporter m) {
        this.em.persist(m);
        return m;
    }

    public Transporter updateTransporter(Transporter m) {
        this.em.merge(m);
        return m;
    }

    public void deleteTransporter(Transporter m) {
        this.em.remove(this.em.merge(m));
    }

    public Transporter findTransporter(String uid) {
        try {
            return (Transporter) this.em.find(Transporter.class, uid);
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Transporter> findTransporterByDate(Calendar cal) {
        try {
            Query q=em.createNamedQuery("Transporter.findByDateTransport")
                    .setParameter("dateTransport",cal,TemporalType.TIMESTAMP);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
       
    public List<Transporter> findTransporterByTracking(int track) {
        try {
            Query q=em.createNamedQuery("Transporter.findByTracking")
                    .setParameter("tracking",track);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    
    public List<Transporter> findTransporters() {
        try {
            Query q=em.createNamedQuery("Transporter.findAll");
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
