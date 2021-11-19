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
@Table(name = "creancier")
@XmlRootElement
@UuidGenerator(name = "UUID")
@NamedQueries({
    @NamedQuery(name = "Creancier.findAll", query = "SELECT c FROM Creancier c")
    , @NamedQuery(name = "Creancier.findByUid", query = "SELECT c FROM Creancier c WHERE c.uid = :uid")
    , @NamedQuery(name = "Creancier.findByNomCreancier", query = "SELECT c FROM Creancier c WHERE c.nomCreancier = :nomCreancier")
    , @NamedQuery(name = "Creancier.findByTypeCreancier", query = "SELECT c FROM Creancier c WHERE c.typeCreancier = :typeCreancier")})
public class Creancier implements Serializable {

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
    @Column(name = "nom_creancier")
    private String nomCreancier;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "type_creancier")
    private String typeCreancier;
    @OneToMany(mappedBy = "creancierId")
    private List<Payer> payerList;

    public Creancier() {
    }
    
    @PrePersist
    private void undashWithLower() {
        this.uid = this.uid.replace("-", "").toLowerCase();
    }

    public Creancier(String uid) {
        this.uid = uid;
    }

    public Creancier(String uid, String nomCreancier, String typeCreancier) {
        this.uid = uid;
        this.nomCreancier = nomCreancier;
        this.typeCreancier = typeCreancier;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNomCreancier() {
        return nomCreancier;
    }

    public void setNomCreancier(String nomCreancier) {
        this.nomCreancier = nomCreancier;
    }

    public String getTypeCreancier() {
        return typeCreancier;
    }

    public void setTypeCreancier(String typeCreancier) {
        this.typeCreancier = typeCreancier;
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
        if (!(object instanceof Creancier)) {
            return false;
        }
        Creancier other = (Creancier) object;
        if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Creancier[ uid=" + uid + " ]";
    }
    
}
