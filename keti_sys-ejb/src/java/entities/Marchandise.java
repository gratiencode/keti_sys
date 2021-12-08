/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

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
@Table(name = "marchandise")
@XmlRootElement
@UuidGenerator(name = "UUID")
@NamedQueries({
    @NamedQuery(name = "Marchandise.findAll", query = "SELECT m FROM Marchandise m")
    , @NamedQuery(name = "Marchandise.findByUid", query = "SELECT m FROM Marchandise m WHERE m.uid = :uid")
    , @NamedQuery(name = "Marchandise.findByNomMarchandise", query = "SELECT m FROM Marchandise m WHERE m.nomMarchandise = :nomMarchandise")
    , @NamedQuery(name = "Marchandise.findByDescription", query = "SELECT m FROM Marchandise m WHERE m.description = :description")})
public class Marchandise implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nom_marchandise")
    private String nomMarchandise;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prix")
    private Double prix;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name="uid") 
    @GeneratedValue(generator="UUID")
     private String uid; 
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMarchandise")
    private List<Transporter> transporterList;

    public Marchandise() {
    }

     @PrePersist
    private void undashWithLower() {
        this.uid = this.uid.replace("-", "").toLowerCase();
    }
    
    public Marchandise(String uid) {
        this.uid = uid;
    }

    public Marchandise(String uid, String nomMarchandise) {
        this.uid = uid;
        this.nomMarchandise = nomMarchandise;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNomMarchandise() {
        return nomMarchandise;
    }

    public void setNomMarchandise(String nomMarchandise) {
        this.nomMarchandise = nomMarchandise;
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
        if (!(object instanceof Marchandise)) {
            return false;
        }
        Marchandise other = (Marchandise) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Marchandise[ uid=" + uid + " ]";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }
    
}
