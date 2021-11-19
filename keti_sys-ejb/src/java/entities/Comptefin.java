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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Compte_fin")
@XmlRootElement 
@UuidGenerator(name = "UUID")
@NamedQueries({
    @NamedQuery(name = "Comptefin.findAll", query = "SELECT c FROM Comptefin c")
    , @NamedQuery(name = "Comptefin.findByUid", query = "SELECT c FROM Comptefin c WHERE c.uid = :uid")
    , @NamedQuery(name = "Comptefin.findByLibelle", query = "SELECT c FROM Comptefin c WHERE c.libelle = :libelle")
    , @NamedQuery(name = "Comptefin.findByNumeroCompte", query = "SELECT c FROM Comptefin c WHERE c.numeroCompte = :numeroCompte")
    , @NamedQuery(name = "Comptefin.findByTypeDeCompte", query = "SELECT c FROM Comptefin c WHERE c.typeDeCompte = :typeDeCompte")
    , @NamedQuery(name = "Comptefin.findByNomBanque", query = "SELECT c FROM Comptefin c WHERE c.nomBanque = :nomBanque")
    , @NamedQuery(name = "Comptefin.findBySoldeMinimum", query = "SELECT c FROM Comptefin c WHERE c.soldeMinimum = :soldeMinimum")})
public class Comptefin implements Serializable {

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
    @Size(max = 255)
    @Column(name = "numero_compte")
    private String numeroCompte;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "type_de_compte")
    private String typeDeCompte;
    @Size(max = 255)
    @Column(name = "nom_banque")
    private String nomBanque;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "solde_minimum")
    private Double soldeMinimum;
    @JoinColumn(name = "sucursale_id", referencedColumnName = "uid")
    @ManyToOne(optional = false)
    private Succursale sucursaleId;
    @OneToMany(mappedBy = "compteIdDebit")
    private List<Payer> payerList;
    @OneToMany(mappedBy = "compteIdCredit")
    private List<Payer> payerList1;
    
    @PrePersist
    private void undashWithLower() {
        this.uid = this.uid.replace("-", "").toLowerCase();
    }
    

    public Comptefin() {
    }

    public Comptefin(String uid) {
        this.uid = uid;
    }

    public Comptefin(String uid, String libelle, String typeDeCompte) {
        this.uid = uid;
        this.libelle = libelle;
        this.typeDeCompte = typeDeCompte;
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

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getTypeDeCompte() {
        return typeDeCompte;
    }

    public void setTypeDeCompte(String typeDeCompte) {
        this.typeDeCompte = typeDeCompte;
    }

    public String getNomBanque() {
        return nomBanque;
    }

    public void setNomBanque(String nomBanque) {
        this.nomBanque = nomBanque;
    }

    public Double getSoldeMinimum() {
        return soldeMinimum;
    }

    public void setSoldeMinimum(Double soldeMinimum) {
        this.soldeMinimum = soldeMinimum;
    }

    public Succursale getSucursaleId() {
        return sucursaleId;
    }

    public void setSucursaleId(Succursale sucursaleId) {
        this.sucursaleId = sucursaleId;
    }

    @JsonbTransient
    public List<Payer> getPayerList() {
        return payerList;
    }

    public void setPayerList(List<Payer> payerList) {
        this.payerList = payerList;
    }

    @JsonbTransient
    public List<Payer> getPayerList1() {
        return payerList1;
    }

    public void setPayerList1(List<Payer> payerList1) {
        this.payerList1 = payerList1;
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
        if (!(object instanceof Comptefin)) {
            return false;
        }
        Comptefin other = (Comptefin) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Comptefin[ uid=" + uid + " ]";
    }
    
}
