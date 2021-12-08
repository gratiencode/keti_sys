/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entities.Vehicule;
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
public class VehiculeBean {

    @PersistenceContext(unitName = "keti_sys-ejbPU")
    EntityManager em;

    public Vehicule createVehicule(Vehicule m) {
        this.em.persist(m);
        return m;
    }

    public Vehicule updateVehicule(Vehicule m) {
        this.em.merge(m);
        return m;
    }

    public void deleteVehicule(Vehicule m) {
        this.em.remove(this.em.merge(m));
    }

    public Vehicule findVehicule(String uid) {
        try {
            return (Vehicule) this.em.find(Vehicule.class, uid);
        } catch (NoResultException e) {
            return null;
        }
    }

    public Vehicule findVehicules(String plack) {
        try {
            Query q = em.createNamedQuery("Vehicule.findByPlaque")
                    .setParameter("plaque", plack);
            return (Vehicule) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Vehicule> findVehicules() {
        try {
            Query q = em.createNamedQuery("Vehicule.findAll");
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
