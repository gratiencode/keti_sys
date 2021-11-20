/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.Tiers;
import entities.User;
import java.util.List;
import javax.ejb.EJB;
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
import stateless.TiersBean;
import util.AuthenticatedUser;
import util.Role;
import util.Secured;

/**
 * REST Web Service
 *
 * @author eroot
 */
@Path("/tiers")
public class ClientsResource {

    @Context
    private UriInfo context;
    
    @EJB
    TiersBean tb;
    
    @Inject
    @AuthenticatedUser
    User user;

    /**
     * Creates a new instance of ClientsResource
     */
    public ClientsResource() {
    }

    /**
     * Retrieves representation of an instance of jaxrs.ClientsResource
     * @param el
     * @return an instance of entities.Tiers
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/save/point")
    @Secured({Role.Administrator,Role.Associe,Role.Directeur})
    public Response createTiers(Tiers el) {
        Tiers t=tb.createTiers(el);
        return Response.ok(t).build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/list/{type}")
    @Secured({Role.Administrator,Role.Associe,Role.Comptable,Role.Directeur})
    public List<Tiers> listTiers(@PathParam("type") String type) {
        return tb.findTiersByType(type);
    }

    /**
     * Sub-resource locator method for {uid}
     */
    @Path("{uid}")
    public ClientResource getClientResource(@PathParam("uid") String uid) {
        return ClientResource.getInstance(uid,tb);
    }
}
