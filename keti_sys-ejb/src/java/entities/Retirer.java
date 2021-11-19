/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.UuidGenerator;

/**
 *
 * @author eroot
 */
@Entity
@Table(name = "retirer")
@XmlRootElement 
@UuidGenerator(name = "UUID")
@NamedQueries({
    @NamedQuery(name = "Retirer.findAll", query = "SELECT r FROM Retirer r")
    , @NamedQuery(name = "Retirer.findByUid", query = "SELECT r FROM Retirer r WHERE r.uid = :uid")
    , @NamedQuery(name = "Retirer.findByReference", query = "SELECT r FROM Retirer r WHERE r.reference = :reference")
    , @NamedQuery(name = "Retirer.findByQuantiteRet", query = "SELECT r FROM Retirer r WHERE r.quantiteRet = :quantiteRet")
    , @NamedQuery(name = "Retirer.findByQuantiteRest", query = "SELECT r FROM Retirer r WHERE r.quantiteRest = :quantiteRest")
    , @NamedQuery(name = "Retirer.findByValeurRet", query = "SELECT r FROM Retirer r WHERE r.valeurRet = :valeurRet")
    , @NamedQuery(name = "Retirer.findByValeurRest", query = "SELECT r FROM Retirer r WHERE r.valeurRest = :valeurRest")
    , @NamedQuery(name = "Retirer.findByDateRet", query = "SELECT r FROM Retirer r WHERE r.dateRet = :dateRet")})
public class Retirer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
   @Column(name = "uid") 
    @GeneratedValue(generator="UUID")
     private String uid;
    @PrePersist
    private void undashWithLower() {
        this.uid = this.uid.replace("-", "").toLowerCase();
    }
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "reference")
    private int reference;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "quantite_ret")
    private Double quantiteRet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantite_rest")
    private double quantiteRest;
    @Column(name = "valeur_ret")
    private Double valeurRet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valeur_rest")
    private double valeurRest;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_ret")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRet;
    @JoinColumn(name = "tiers_id", referencedColumnName = "uid")
    @ManyToOne(optional = false)
    private Tiers tiersId;
    @JoinColumn(name = "trans_id", referencedColumnName = "uid")
    @ManyToOne(optional = false)
    private Transporter transId;

    public Retirer() {
    }

    public Retirer(String uid) {
        this.uid = uid;
    }

    public Retirer(String uid, int reference, double quantiteRest, double valeurRest, Date dateRet) {
        this.uid = uid;
        this.reference = reference;
        this.quantiteRest = quantiteRest;
        this.valeurRest = valeurRest;
        this.dateRet = dateRet;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public Double getQuantiteRet() {
        return quantiteRet;
    }

    public void setQuantiteRet(Double quantiteRet) {
        this.quantiteRet = quantiteRet;
    }

    public double getQuantiteRest() {
        return quantiteRest;
    }

    public void setQuantiteRest(double quantiteRest) {
        this.quantiteRest = quantiteRest;
    }

    public Double getValeurRet() {
        return valeurRet;
    }

    public void setValeurRet(Double valeurRet) {
        this.valeurRet = valeurRet;
    }

    public double getValeurRest() {
        return valeurRest;
    }

    public void setValeurRest(double valeurRest) {
        this.valeurRest = valeurRest;
    }

    public Date getDateRet() {
        return dateRet;
    }

    public void setDateRet(Date dateRet) {
        this.dateRet = dateRet;
    }

    public Tiers getTiersId() {
        return tiersId;
    }

    public void setTiersId(Tiers tiersId) {
        this.tiersId = tiersId;
    }

    public Transporter getTransId() {
        return transId;
    }

    public void setTransId(Transporter transId) {
        this.transId = transId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uid != null ? uid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Retirer)) {
            return false;
        }
        Retirer other = (Retirer) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Retirer[ uid=" + uid + " ]";
    }
    
}
