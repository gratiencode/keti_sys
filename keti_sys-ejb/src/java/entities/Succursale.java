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
@Table(name = "succursale")
@XmlRootElement
@UuidGenerator(name = "UUID")
@NamedQueries({
    @NamedQuery(name = "Succursale.findAll", query = "SELECT s FROM Succursale s")
    , @NamedQuery(name = "Succursale.findByUid", query = "SELECT s FROM Succursale s WHERE s.uid = :uid")
    , @NamedQuery(name = "Succursale.findByNomSuccursale", query = "SELECT s FROM Succursale s WHERE s.nomSuccursale = :nomSuccursale")
    , @NamedQuery(name = "Succursale.findByAdresse", query = "SELECT s FROM Succursale s WHERE s.adresse = :adresse")
    , @NamedQuery(name = "Succursale.findByDirecteur", query = "SELECT s FROM Succursale s WHERE s.directeur = :directeur")})
public class Succursale implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "uid")
    @GeneratedValue(generator = "UUID")
    private String uid;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nom_succursale")
    private String nomSuccursale;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "adresse")
    private String adresse;
    @Size(max = 255)
    @Column(name = "directeur")
    private String directeur;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursaleId")
    private List<Comptefin> comptefinList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "succursalleId")
    private List<Voyage> voyageList;

    public Succursale() {
    }

    @PrePersist
    private void undashWithLower() {
        this.uid = this.uid.replace("-", "").toLowerCase();
    }

    public Succursale(String uid) {
        this.uid = uid;
    }

    public Succursale(String uid, String nomSuccursale, String adresse) {
        this.uid = uid;
        this.nomSuccursale = nomSuccursale;
        this.adresse = adresse;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNomSuccursale() {
        return nomSuccursale;
    }

    public void setNomSuccursale(String nomSuccursale) {
        this.nomSuccursale = nomSuccursale;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDirecteur() {
        return directeur;
    }

    public void setDirecteur(String directeur) {
        this.directeur = directeur;
    }

    @JsonbTransient
    public List<Comptefin> getComptefinList() {
        return comptefinList;
    }

    public void setComptefinList(List<Comptefin> comptefinList) {
        this.comptefinList = comptefinList;
    }

    @JsonbTransient
    public List<Voyage> getVoyageList() {
        return voyageList;
    }

    public void setVoyageList(List<Voyage> voyageList) {
        this.voyageList = voyageList;
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
        if (!(object instanceof Succursale)) {
            return false;
        }
        Succursale other = (Succursale) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Succursale[ uid=" + uid + " ]";
    }

}
