/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.Tiers;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import stateless.TiersBean;
import util.Role;
import util.Secured;

/**
 * REST Web Service
 *
 * @author eroot
 */
public class ClientResource {

    private String uid;
    Tiers t;
    TiersBean tb;

    /**
     * Creates a new instance of ClientResource
     */
    private ClientResource(String uid,TiersBean tb) {
        this.uid = uid;
        t=tb.findTiers(uid);
        this.tb=tb;
    }

    /**
     * Get instance of the ClientResource
     */
    public static ClientResource getInstance(String uid,TiersBean tb) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of ClientResource class.
        return new ClientResource(uid,tb);
    }

    /**
     * Retrieves representation of an instance of jaxrs.ClientResource
     * @return an instance of entities.Tiers
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/view")
    @Secured({Role.Administrator,Role.Associe,Role.Comptable,Role.Directeur})
    public Response getJson() {
        return Response.ok(t).build();
    }

    /**
     * PUT method for updating or creating an instance of ClientResource
     * @param content representation for the resource
     * @return 
     */
    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update")
    @Secured({Role.Administrator,Role.Associe,Role.Comptable,Role.Directeur})
    public Response putJson(Tiers content) {
        t.setAdresse(content.getAdresse());
        t.setNom(content.getNom());
        t.setPrenom(content.getPrenom());
        t.setTypetiers(content.getTypetiers());
        Tiers tr=tb.updateTiers(t);
        return Response.ok(tr).build();
    }

    /**
     * DELETE method for resource ClientResource
     */
    @DELETE
    @Path("/delete")
    @Secured({Role.Administrator,Role.Associe,Role.Directeur})
    public void delete() {
        tb.deleteTiers(t);
    }
}
