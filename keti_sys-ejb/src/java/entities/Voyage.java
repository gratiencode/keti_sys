/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
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
@Table(name = "voyage")
@XmlRootElement
@UuidGenerator(name = "UUID")
@NamedQueries({
    @NamedQuery(name = "Voyage.findAll", query = "SELECT v FROM Voyage v")
    , @NamedQuery(name = "Voyage.findByUid", query = "SELECT v FROM Voyage v WHERE v.uid = :uid")
    , @NamedQuery(name = "Voyage.findByDateArriver", query = "SELECT v FROM Voyage v WHERE v.dateArriver = :dateArriver")
    , @NamedQuery(name = "Voyage.findByEtat", query = "SELECT v FROM Voyage v WHERE v.etat = :etat")})
public class Voyage implements Serializable {

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
    @Column(name = "date_arriver")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(
        shape = Shape.STRING,
        pattern = "yyyy-MM-dd'T'HH:mm:ss"
    )
    private Date dateArriver;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "etat")
    private String etat;
    @JoinColumn(name = "succursalle_id", referencedColumnName = "uid")
    @ManyToOne(optional = false)
    private Succursale succursalleId;
    @JoinColumn(name = "vehicule_id", referencedColumnName = "uid")
    @ManyToOne(optional = false)
    private Vehicule vehiculeId;

    public Voyage() {
    }

    public Voyage(String uid) {
        this.uid = uid;
    }

    @PrePersist
    private void undashWithLower() {
        this.uid = this.uid.replace("-", "").toLowerCase();
    }

    public Voyage(String uid, Date dateArriver, String etat) {
        this.uid = uid;
        this.dateArriver = dateArriver;
        this.etat = etat;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getDateArriver() {
        return dateArriver;
    }

    public void setDateArriver(Date dateArriver) {
        this.dateArriver = dateArriver;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Succursale getSuccursalleId() {
        return succursalleId;
    }

    public void setSuccursalleId(Succursale succursalleId) {
        this.succursalleId = succursalleId;
    }

    public Vehicule getVehiculeId() {
        return vehiculeId;
    }

    public void setVehiculeId(Vehicule vehiculeId) {
        this.vehiculeId = vehiculeId;
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
        if (!(object instanceof Voyage)) {
            return false;
        }
        Voyage other = (Voyage) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Voyage[ uid=" + uid + " ]";
    }

}
