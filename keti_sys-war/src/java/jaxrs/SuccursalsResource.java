/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.Succursale;
import entities.User;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import stateless.SuccursaleBean;
import util.AuthenticatedUser;

/**
 * REST Web Service
 *
 * @author eroot
 */
@Path("/sucursal")
@Stateless
public class SuccursalsResource {

    @Context
    private UriInfo context;
    @EJB
    SuccursaleBean sbn;
    
    @Inject
    @AuthenticatedUser
    User user;

    /**
     * Creates a new instance of SuccursalsResource
     */
    public SuccursalsResource() {
    }

    /**
     * Retrieves representation of an instance of jaxrs.SuccursalsResource
     * @return an instance of java.lang.String
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/save/point")
    public Response saveJson(Succursale suc) {
        Succursale s=sbn.createSuccursale(suc);
        return Response.ok(s).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public List<Succursale> getJson() {
        return sbn.findSuccursales();
    }
    
    /**
     * Sub-resource locator method for {uid}
     */
    @Path("{uid}")
    public SuccursalResource getSuccursalResource(@PathParam("uid") String uid) {
        return SuccursalResource.getInstance(uid);
    }
}
