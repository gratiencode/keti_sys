/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.Vehicule;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import stateless.VehiculeBean;
import util.Role;
import util.Secured;

/**
 * REST Web Service
 *
 * @author eroot
 */
public class VehiculeResource {

    private String uid;
    VehiculeBean vb;
    Vehicule v;

    /**
     * Creates a new instance of VehiculeResource
     */
    private VehiculeResource(String uid,VehiculeBean vb) {
        this.vb=vb;
        this.uid = uid;
        v=vb.findVehicule(uid);
    }

    /**
     * Get instance of the VehiculeResource
     */
    public static VehiculeResource getInstance(String uid,VehiculeBean vb) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of VehiculeResource class.
        return new VehiculeResource(uid,vb);
    }

    /**
     * Retrieves representation of an instance of jaxrs.VehiculeResource
     * @return an instance of entities.Vehicule
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator,Role.Associe,Role.Directeur,Role.Comptable})
    public Vehicule getJson() {
       return v;
    }

    /**
     * PUT method for updating or creating an instance of VehiculeResource
     * @param content representation for the resource
     */
    @Path("update")
    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator,Role.Associe,Role.Directeur,Role.Comptable})
    public Response putJson(Vehicule content) {
        v.setMarque(content.getMarque());
        v.setModeleVehicule(content.getModeleVehicule());
        v.setNombreDeRoue(content.getNombreDeRoue());
        v.setPlaque(content.getPlaque());
        v.setTypeVehicule(content.getTypeVehicule());
        Vehicule updateVehicule = vb.updateVehicule(v);
        return Response.ok(updateVehicule).build();
    }

    /**
     * DELETE method for resource VehiculeResource
     */
    @DELETE
    @Secured({Role.Administrator,Role.Associe,Role.Directeur})
    @Path("/delete")
    public void delete() {
        vb.deleteVehicule(v);
    }
}
