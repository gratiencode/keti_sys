/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entities.Marchandise;
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
public class MarchandiseBean {

   @PersistenceContext(unitName="keti_sys-ejbPU")
    EntityManager em;
    
    public Marchandise createMarchandise(Marchandise m) {
        this.em.persist(m);
        return m;
    }

    public Marchandise updateMarchandise(Marchandise m) {
        this.em.merge(m);
        return m;
    }

    public void deleteMarchandise(Marchandise m) {
        this.em.remove(this.em.merge(m));
    }

    public Marchandise findMarchandise(String uid) {
        try {
            return (Marchandise) this.em.find(Marchandise.class, uid);
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Marchandise> findMarchandiseByName(String m) {
        try {
            Query q=em.createNamedQuery("Marchandise.findByNomMarchandise")
                    .setParameter("nomMarchandise",m);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Marchandise> findMarchandises() {
        try {
            Query q=em.createNamedQuery("Marchandise.findAll");
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
