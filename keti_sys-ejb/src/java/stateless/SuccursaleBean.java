/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entities.Succursale;
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
public class SuccursaleBean {
@PersistenceContext(unitName="keti_sys-ejbPU")
    EntityManager em;
    
    public Succursale createSuccursale(Succursale m) {
        this.em.persist(m);
        return m;
    }

    public Succursale updateSuccursale(Succursale m) {
        this.em.merge(m);
        return m;
    }

    public void deleteSuccursale(Succursale m) {
        this.em.remove(this.em.merge(m));
    }

    public Succursale findSuccursale(String uid) {
        try {
            return (Succursale) this.em.find(Succursale.class, uid);
        } catch (NoResultException e) {
            return null;
        }
    }

    public Succursale findSuccursales(String directeur) {
        try {
            Query q=em.createNamedQuery("Succursale.findByDirecteur")
                    .setParameter("directeur",directeur);
            return (Succursale) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
     public List<Succursale> findSuccursales() {
        try {
            Query q=em.createNamedQuery("Succursale.findAll");
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
