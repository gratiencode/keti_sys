/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entities.Tiers;
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
public class TiersBean {

    @PersistenceContext(unitName="keti_sys-ejbPU")
    EntityManager em;
    
    public Tiers createTiers(Tiers m) {
        this.em.persist(m);
        return m;
    }

    public Tiers updateTiers(Tiers m) {
        this.em.merge(m);
        return m;
    }

    public void deleteTiers(Tiers m) {
        this.em.remove(this.em.merge(m));
    }

    public Tiers findTiers(String uid) {
        try {
            return (Tiers) this.em.find(Tiers.class, uid);
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Tiers> findTiersByType(String type) {
        try {
            Query q=em.createNamedQuery("Tiers.findByTypetiers")
                    .setParameter("typetiers",type);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Tiers> findTiersBy(String nom,String prenom) {
        try {
            Query q=em.createNamedQuery("Tiers.findByNomPrenom")
                    .setParameter("nom",nom).setParameter("prenom", prenom);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
