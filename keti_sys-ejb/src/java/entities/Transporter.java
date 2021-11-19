/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "transporter")
@XmlRootElement
@UuidGenerator(name = "UUID")
@NamedQueries({
    @NamedQuery(name = "Transporter.findAll", query = "SELECT t FROM Transporter t")
    , @NamedQuery(name = "Transporter.findByUid", query = "SELECT t FROM Transporter t WHERE t.uid = :uid")
    , @NamedQuery(name = "Transporter.findByQuantite", query = "SELECT t FROM Transporter t WHERE t.quantite = :quantite")
    , @NamedQuery(name = "Transporter.findByPriceToPay", query = "SELECT t FROM Transporter t WHERE t.priceToPay = :priceToPay")
    , @NamedQuery(name = "Transporter.findByPaidPrice", query = "SELECT t FROM Transporter t WHERE t.paidPrice = :paidPrice")
    , @NamedQuery(name = "Transporter.findByDateTransport", query = "SELECT t FROM Transporter t WHERE t.dateTransport = :dateTransport")
    , @NamedQuery(name = "Transporter.findByObservation", query = "SELECT t FROM Transporter t WHERE t.observation = :observation")
    , @NamedQuery(name = "Transporter.findByTracking", query = "SELECT t FROM Transporter t WHERE t.tracking = :tracking")})
public class Transporter implements Serializable {

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
    @Column(name = "quantite")
    private double quantite;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price_to_pay")
    private double priceToPay;
    @Basic(optional = false)
    @NotNull
    @Column(name = "paid_price")
    private double paidPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_transport")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTransport;
    @Lob
    @Column(name = "photo")
    private byte[] photo;
    @Size(max = 255)
    @Column(name = "observation")
    private String observation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tracking")
    private int tracking;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transId")
    private List<Retirer> retirerList;
    @JoinColumn(name = "id_marchandise", referencedColumnName = "uid")
    @ManyToOne(optional = false)
    private Marchandise idMarchandise;
    @JoinColumn(name = "id_tiers", referencedColumnName = "uid")
    @ManyToOne(optional = false)
    private Tiers idTiers;
    @JoinColumn(name = "id_vehicule", referencedColumnName = "uid")
    @ManyToOne(optional = false)
    private Vehicule idVehicule;

    public Transporter() {
    }
    
    @PrePersist
    private void undashWithLower() {
        this.uid = this.uid.replace("-", "").toLowerCase();
    }
    public Transporter(String uid) {
        this.uid = uid;
    }

    public Transporter(String uid, double quantite, double priceToPay, double paidPrice, Date dateTransport, int tracking) {
        this.uid = uid;
        this.quantite = quantite;
        this.priceToPay = priceToPay;
        this.paidPrice = paidPrice;
        this.dateTransport = dateTransport;
        this.tracking = tracking;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public double getPriceToPay() {
        return priceToPay;
    }

    public void setPriceToPay(double priceToPay) {
        this.priceToPay = priceToPay;
    }

    public double getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(double paidPrice) {
        this.paidPrice = paidPrice;
    }

    public Date getDateTransport() {
        return dateTransport;
    }

    public void setDateTransport(Date dateTransport) {
        this.dateTransport = dateTransport;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public int getTracking() {
        return tracking;
    }

    public void setTracking(int tracking) {
        this.tracking = tracking;
    }

    @JsonbTransient
    public List<Retirer> getRetirerList() {
        return retirerList;
    }

    public void setRetirerList(List<Retirer> retirerList) {
        this.retirerList = retirerList;
    }

    public Marchandise getIdMarchandise() {
        return idMarchandise;
    }

    public void setIdMarchandise(Marchandise idMarchandise) {
        this.idMarchandise = idMarchandise;
    }

    public Tiers getIdTiers() {
        return idTiers;
    }

    public void setIdTiers(Tiers idTiers) {
        this.idTiers = idTiers;
    }

    public Vehicule getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(Vehicule idVehicule) {
        this.idVehicule = idVehicule;
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
        if (!(object instanceof Transporter)) {
            return false;
        }
        Transporter other = (Transporter) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Transporter[ uid=" + uid + " ]";
    }
    
}
