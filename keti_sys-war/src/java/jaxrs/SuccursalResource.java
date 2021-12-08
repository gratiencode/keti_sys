/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.Succursale;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import stateless.SuccursaleBean;
import util.Role;
import util.Secured;

/**
 * REST Web Service
 *
 * @author eroot
 */
public class SuccursalResource {

    private String uid;
    SuccursaleBean sb;
    Succursale s;

    /**
     * Creates a new instance of SuccursalResource
     */
    private SuccursalResource(String uid, SuccursaleBean sb) {
        this.uid = uid;
        this.sb=sb;
        s=this.sb.findSuccursale(uid);
    }

    /**
     * Get instance of the SuccursalResource
     */
    public static SuccursalResource getInstance(String uid,SuccursaleBean sb) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of SuccursalResource class.
        return new SuccursalResource(uid,sb);
    }

    /**
     * Retrieves representation of an instance of jaxrs.SuccursalResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        return Response.ok(s).build();
    }

    /**
     * PUT method for updating or creating an instance of SuccursalResource
     * @param content representation for the resource
     */
    @Path("/update")
    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator,Role.Associe,Role.Directeur,Role.Comptable})
    public Response putJson(Succursale content) {
        s.setAdresse(content.getAdresse());
        s.setDirecteur(content.getDirecteur());
        s.setNomSuccursale(content.getNomSuccursale());
        Succursale ss=sb.updateSuccursale(s);
        return Response.ok(ss).build();
    }

    /**
     * DELETE method for resource SuccursalResource
     */
    @Path("/delete")
    @DELETE
    @Secured({Role.Administrator,Role.Associe,Role.Directeur})
    public void delete() {
    }
}
