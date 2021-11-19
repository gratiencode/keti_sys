/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entities.Depense;
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
public class DepenseBean {

    @PersistenceContext(unitName="keti_sys-ejbPU")
    EntityManager em;
    
    public Depense createDepense(Depense m) {
        this.em.persist(m);
        return m;
    }

    public Depense updateDepense(Depense m) {
        this.em.merge(m);
        return m;
    }

    public void deleteDepense(Depense m) {
        this.em.remove(this.em.merge(m));
    }

    public Depense findDepense(String uid) {
        try {
            return (Depense) this.em.find(Depense.class, uid);
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Depense> findDepenseByLibelle(String libelle) {
        try {
            Query q=em.createNamedQuery("Depense.findByLibelle")
                    .setParameter("libelle",libelle);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
