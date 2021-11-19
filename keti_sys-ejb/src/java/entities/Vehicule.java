/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.Transporter;
import entities.Voyage;
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
import org.eclipse.persistence.annotations.UuidGenerator;

/**
 *
 * @author eroot
 */
@Entity
@Table(name = "vehicule")
@XmlRootElement
@UuidGenerator(name = "UUID")
@NamedQueries({
    @NamedQuery(name = "Vehicule.findAll", query = "SELECT v FROM Vehicule v")
    , @NamedQuery(name = "Vehicule.findByUid", query = "SELECT v FROM Vehicule v WHERE v.uid = :uid")
    , @NamedQuery(name = "Vehicule.findByPlaque", query = "SELECT v FROM Vehicule v WHERE v.plaque = :plaque")
    , @NamedQuery(name = "Vehicule.findByModeleVehicule", query = "SELECT v FROM Vehicule v WHERE v.modeleVehicule = :modeleVehicule")
    , @NamedQuery(name = "Vehicule.findByMarque", query = "SELECT v FROM Vehicule v WHERE v.marque = :marque")
    , @NamedQuery(name = "Vehicule.findByNombreDeRoue", query = "SELECT v FROM Vehicule v WHERE v.nombreDeRoue = :nombreDeRoue")})
public class Vehicule implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "plaque")
    private String plaque;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "modele_vehicule")
    private String modeleVehicule;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "marque")
    private String marque;
    @Size(max = 255)
    @Column(name = "nombre_de_roue")
    private String nombreDeRoue;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "type_vehicule")
    private String typeVehicule;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vehiculeId")
    private List<Voyage> voyageList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "uid")
    @GeneratedValue(generator = "UUID")
    private String uid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVehicule")
    private List<Transporter> transporterList;

    public Vehicule() {
    }

    @PrePersist
    private void undashWithLower() {
        this.uid = this.uid.replace("-", "").toLowerCase();
    }

    public Vehicule(String uid) {
        this.uid = uid;
    }

    public Vehicule(String uid, String plaque, String modeleVehicule, String marque) {
        this.uid = uid;
        this.plaque = plaque;
        this.modeleVehicule = modeleVehicule;
        this.marque = marque;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getModeleVehicule() {
        return modeleVehicule;
    }

    public void setModeleVehicule(String modeleVehicule) {
        this.modeleVehicule = modeleVehicule;
    }

    public String getNombreDeRoue() {
        return nombreDeRoue;
    }

    public void setNombreDeRoue(String nombreDeRoue) {
        this.nombreDeRoue = nombreDeRoue;
    }

    @JsonbTransient
    public List<Transporter> getTransporterList() {
        return transporterList;
    }

    public void setTransporterList(List<Transporter> transporterList) {
        this.transporterList = transporterList;
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
        if (!(object instanceof Vehicule)) {
            return false;
        }
        Vehicule other = (Vehicule) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Vehicule[ uid=" + uid + " ]";
    }

    public String getTypeVehicule() {
        return typeVehicule;
    }

    public void setTypeVehicule(String typeVehicule) {
        this.typeVehicule = typeVehicule;
    }

    @JsonbTransient
    public List<Voyage> getVoyageList() {
        return voyageList;
    }

    public void setVoyageList(List<Voyage> voyageList) {
        this.voyageList = voyageList;
    }

    public String getPlaque() {
        return plaque;
    }

    public void setPlaque(String plaque) {
        this.plaque = plaque;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

}
