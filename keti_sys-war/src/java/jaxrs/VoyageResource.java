/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.Voyage;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import stateless.VoyageBean;
import util.Role;
import util.Secured;

/**
 * REST Web Service
 *
 * @author eroot
 */
public class VoyageResource {

    private String uid;
    Voyage v;
    VoyageBean vb;

    /**
     * Creates a new instance of VoyageResource
     */
    private VoyageResource(VoyageBean vb,String uid) {
        this.uid = uid;
        this.vb=vb;
        this.v=this.vb.findVoyage(this.uid);
    }

    /**
     * Get instance of the VoyageResource
     */
    public static VoyageResource getInstance(VoyageBean vb,String uid) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of VoyageResource class.
        return new VoyageResource(vb,uid);
    }

    /**
     * Retrieves representation of an instance of jaxrs.VoyageResource
     * @return an instance of entities.Voyage
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator,Role.Associe,Role.Caissier,Role.Comptable,Role.Directeur})
    public Voyage getJson() {
       return v;
    }

    /**
     * PUT method for updating or creating an instance of VoyageResource
     * @param content representation for the resource
     */
    @PATCH
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator,Role.Associe,Role.Caissier,Role.Comptable,Role.Directeur})
    public Response putJson(Voyage content) {
        v.setDateArriver(content.getDateArriver());
        v.setEtat(content.getEtat());
       return Response.ok(vb.updateVoyage(v)).build(); 
    }

    /**
     * DELETE method for resource VoyageResource
     */
    @DELETE
    @Path("delete")
    @Secured({Role.Administrator,Role.Associe,Role.Directeur})
    public void delete() {
        vb.deleteVoyage(v);
    }
}
