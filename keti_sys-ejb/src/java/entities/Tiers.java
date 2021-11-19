/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.Payer;
import entities.Retirer;
import entities.Transporter;
import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "tiers")
@XmlRootElement 
@UuidGenerator(name = "UUID")
@NamedQueries({
    @NamedQuery(name = "Tiers.findAll", query = "SELECT t FROM Tiers t")
    , @NamedQuery(name = "Tiers.findByUid", query = "SELECT t FROM Tiers t WHERE t.uid = :uid")
    , @NamedQuery(name = "Tiers.findByNomPrenom", query = "SELECT t FROM Tiers t WHERE t.nom LIKE :nom AND t.prenom LIKE :prenom")
    , @NamedQuery(name = "Tiers.findByPrenom", query = "SELECT t FROM Tiers t WHERE t.prenom = :prenom")
    , @NamedQuery(name = "Tiers.findByAdresse", query = "SELECT t FROM Tiers t WHERE t.adresse = :adresse")
    , @NamedQuery(name = "Tiers.findByTypetiers", query = "SELECT t FROM Tiers t WHERE t.typetiers = :typetiers")})
public class Tiers implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Nom")
    private String nom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Prenom")
    private String prenom;
    @Size(max = 255)
    @Column(name = "Adresse")
    private String adresse;
    @Size(max = 255)
    @Column(name = "Type_tiers")
    private String typetiers;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tiersId")
    private List<Retirer> retirerList;
    @OneToMany(mappedBy = "clientId")
    private List<Payer> payerList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name="uid") 
    @GeneratedValue(generator="UUID")
     private String uid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTiers")
    private List<Transporter> transporterList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTiers")
    private List<Contacts> contactsList;

    public Tiers() {
    }
    
    @PrePersist
    private void undashWithLower() {
        this.uid = this.uid.replace("-", "").toLowerCase();
    }

    public Tiers(String uid) {
        this.uid = uid;
    }

    public Tiers(String uid, String nom, String prenom) {
        this.uid = uid;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getTypetiers() {
        return typetiers;
    }

    public void setTypetiers(String typetiers) {
        this.typetiers = typetiers;
    }

    @JsonbTransient
    public List<Transporter> getTransporterList() {
        return transporterList;
    }

    public void setTransporterList(List<Transporter> transporterList) {
        this.transporterList = transporterList;
    }

    @JsonbTransient
    public List<Contacts> getContactsList() {
        return contactsList;
    }

    public void setContactsList(List<Contacts> contactsList) {
        this.contactsList = contactsList;
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
        if (!(object instanceof Tiers)) {
            return false;
        }
        Tiers other = (Tiers) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Tiers[ uid=" + uid + " ]";
    }



    @JsonbTransient
    public List<Retirer> getRetirerList() {
        return retirerList;
    }

    public void setRetirerList(List<Retirer> retirerList) {
        this.retirerList = retirerList;
    }

    @JsonbTransient
    public List<Payer> getPayerList() {
        return payerList;
    }

    public void setPayerList(List<Payer> payerList) {
        this.payerList = payerList;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

   
    
}
