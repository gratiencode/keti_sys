/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Date;
import java.util.List;

/**
 *
 * @author eroot
 */
public class TrackResult {
    private String plaqueVehicule;
    private String succursaleName;
    private String etat;
    private String typeVehicule;
    private Date dateVoyage;
    private int trackingNumber;
    private List<String> cargaison;

    public TrackResult(String plaqueVehicule, String succursaleName, String etat, Date dateVoyage, int trackingNumber, List<String> cargaison) {
        this.plaqueVehicule = plaqueVehicule;
        this.succursaleName = succursaleName;
        this.etat = etat;
        this.dateVoyage = dateVoyage;
        this.trackingNumber = trackingNumber;
        this.cargaison = cargaison;
    }

    public TrackResult() {
    }

    public String getPlaqueVehicule() {
        return plaqueVehicule;
    }

    public void setPlaqueVehicule(String plaqueVehicule) {
        this.plaqueVehicule = plaqueVehicule;
    }

    public String getSuccursaleName() {
        return succursaleName;
    }

    public void setSuccursaleName(String succursaleName) {
        this.succursaleName = succursaleName;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Date getDateVoyage() {
        return dateVoyage;
    }

    public void setDateVoyage(Date dateVoyage) {
        this.dateVoyage = dateVoyage;
    }

    public int getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(int trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public List<String> getCargaison() {
        return cargaison;
    }

    public void setCargaison(List<String> cargaison) {
        this.cargaison = cargaison;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.trackingNumber;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TrackResult other = (TrackResult) obj;
        if (this.trackingNumber != other.trackingNumber) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TrackResult{" + "plaqueVehicule=" + plaqueVehicule + ", succursaleName=" + succursaleName + ", etat=" + etat + ", dateVoyage=" + dateVoyage + ", trackingNumber=" + trackingNumber + ", cargaison=" + cargaison + '}';
    }

    public String getTypeVehicule() {
        return typeVehicule;
    }

    public void setTypeVehicule(String typeVehicule) {
        this.typeVehicule = typeVehicule;
    }
    
    
    
    
}
