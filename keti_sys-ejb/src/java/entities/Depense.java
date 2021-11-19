/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.UuidGenerator;

/**
 *
 * @author eroot
 */
@Entity
@Table(name = "depense")
@XmlRootElement 
@UuidGenerator(name = "UUID")
@NamedQueries({
    @NamedQuery(name = "Depense.findAll", query = "SELECT d FROM Depense d")
    , @NamedQuery(name = "Depense.findByUid", query = "SELECT d FROM Depense d WHERE d.uid = :uid")
    , @NamedQuery(name = "Depense.findByLibelle", query = "SELECT d FROM Depense d WHERE d.libelle = :libelle")
    , @NamedQuery(name = "Depense.findByMontantFixe", query = "SELECT d FROM Depense d WHERE d.montantFixe = :montantFixe")})
public class Depense implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
   @Column(name = "uid")
    @GeneratedValue(generator="UUID")
     private String uid;
    
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "libelle")
    private String libelle;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montant_fixe")
    private Double montantFixe;
    @OneToMany(mappedBy = "depenseId")
    private List<Payer> payerList;
    @PrePersist
    private void undashWithLower() {
        this.uid = this.uid.replace("-", "").toLowerCase();
    }
    public Depense() {
    }

    public Depense(String uid) {
        this.uid = uid;
    }

    public Depense(String uid, String libelle) {
        this.uid = uid;
        this.libelle = libelle;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getMontantFixe() {
        return montantFixe;
    }

    public void setMontantFixe(Double montantFixe) {
        this.montantFixe = montantFixe;
    }

    @JsonbTransient
    public List<Payer> getPayerList() {
        return payerList;
    }

    public void setPayerList(List<Payer> payerList) {
        this.payerList = payerList;
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
        if (!(object instanceof Depense)) {
            return false;
        }
        Depense other = (Depense) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Depense[ uid=" + uid + " ]";
    }
    
}
