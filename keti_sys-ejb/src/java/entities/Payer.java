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
@Table(name = "payer")
@XmlRootElement
@UuidGenerator(name = "UUID")
@NamedQueries({
    @NamedQuery(name = "Payer.findAll", query = "SELECT p FROM Payer p")
    , @NamedQuery(name = "Payer.findByUid", query = "SELECT p FROM Payer p WHERE p.uid = :uid")
    , @NamedQuery(name = "Payer.findByReference", query = "SELECT p FROM Payer p WHERE p.reference = :reference")
    , @NamedQuery(name = "Payer.findByMontantDette", query = "SELECT p FROM Payer p WHERE p.montantDette = :montantDette")
    , @NamedQuery(name = "Payer.findByMontantPaye", query = "SELECT p FROM Payer p WHERE p.montantPaye = :montantPaye")
    , @NamedQuery(name = "Payer.findByMontantRestant", query = "SELECT p FROM Payer p WHERE p.montantRestant = :montantRestant")
    , @NamedQuery(name = "Payer.findByDatePayement", query = "SELECT p FROM Payer p WHERE p.datePayement = :datePayement")
    , @NamedQuery(name = "Payer.findByLibelle", query = "SELECT p FROM Payer p WHERE p.libelle = :libelle")})
public class Payer implements Serializable {

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
    @Column(name = "reference")
    private int reference;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montant_dette")
    private Double montantDette;
    @Column(name = "montant_paye")
    private Double montantPaye;
    @Column(name = "montant_restant")
    private Double montantRestant;
    @Column(name = "date_payement")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePayement;
    @Size(max = 255)
    @Column(name = "libelle")
    private String libelle;
    @JoinColumn(name = "compte_id_debit", referencedColumnName = "uid")
    @ManyToOne
    private Comptefin compteIdDebit;
    @JoinColumn(name = "compte_id_credit", referencedColumnName = "uid")
    @ManyToOne
    private Comptefin compteIdCredit;
    @JoinColumn(name = "creancier_id", referencedColumnName = "uid")
    @ManyToOne
    private Creancier creancierId;
    @JoinColumn(name = "depense_id", referencedColumnName = "uid")
    @ManyToOne
    private Depense depenseId;
    @JoinColumn(name = "client_id", referencedColumnName = "uid")
    @ManyToOne
    private Tiers clientId;

    public Payer() {
    }
    
@PrePersist
    private void undashWithLower() {
        this.uid = this.uid.replace("-", "").toLowerCase();
    }
    public Payer(String uid) {
        this.uid = uid;
    }

    public Payer(String uid, int reference) {
        this.uid = uid;
        this.reference = reference;
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

    public Double getMontantDette() {
        return montantDette;
    }

    public void setMontantDette(Double montantDette) {
        this.montantDette = montantDette;
    }

    public Double getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(Double montantPaye) {
        this.montantPaye = montantPaye;
    }

    public Double getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(Double montantRestant) {
        this.montantRestant = montantRestant;
    }

    public Date getDatePayement() {
        return datePayement;
    }

    public void setDatePayement(Date datePayement) {
        this.datePayement = datePayement;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Comptefin getCompteIdDebit() {
        return compteIdDebit;
    }

    public void setCompteIdDebit(Comptefin compteIdDebit) {
        this.compteIdDebit = compteIdDebit;
    }

    public Comptefin getCompteIdCredit() {
        return compteIdCredit;
    }

    public void setCompteIdCredit(Comptefin compteIdCredit) {
        this.compteIdCredit = compteIdCredit;
    }

    public Creancier getCreancierId() {
        return creancierId;
    }

    public void setCreancierId(Creancier creancierId) {
        this.creancierId = creancierId;
    }

    public Depense getDepenseId() {
        return depenseId;
    }

    public void setDepenseId(Depense depenseId) {
        this.depenseId = depenseId;
    }

    public Tiers getClientId() {
        return clientId;
    }

    public void setClientId(Tiers clientId) {
        this.clientId = clientId;
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
        if (!(object instanceof Payer)) {
            return false;
        }
        Payer other = (Payer) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Payer[ uid=" + uid + " ]";
    }
    
}
