/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.Contacts;
import entities.Tiers;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import stateless.ContactBean;
import stateless.TiersBean;
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
    @EJB
    ContactBean cb;

//    @Inject
//    @AuthenticatedUser
//    User user;
    /**
     * Creates a new instance of ClientsResource
     */
    public ClientsResource() {
    }

    /**
     * Retrieves representation of an instance of jaxrs.ClientsResource
     *
     * @param el
     * @return an instance of entities.Tiers
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/save/point")
    @Secured({Role.Administrator, Role.Associe, Role.Directeur, Role.Comptable, Role.Caissier})
    public Response createTiers(Tiers el) {
        Tiers t = tb.createTiers(el);
        return Response.ok(t).build();
    }
    private static final Logger LOG = Logger.getLogger(ClientsResource.class.getName());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list/{type}")
    @Secured({Role.Administrator, Role.Associe, Role.Comptable, Role.Directeur, Role.Caissier})
    public List<Tiers> listTiersByType(@PathParam("type") String type) {
        return tb.findTiersByType(type);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    @Secured({Role.Administrator, Role.Associe, Role.Comptable, Role.Directeur, Role.Caissier})
    public List<Tiers> listTiers() {
        return tb.findTiers();
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator, Role.Associe, Role.Comptable, Role.Directeur})
    @Path("/sync")
    public Response syncTiers(List<Tiers> contacts) {
        for (Tiers c : contacts) {
            Tiers cc = tb.findTiers(c.getUid());
            if (cc == null) {
                tb.createTiers(c);
            } else {
                tb.updateTiers(c);
            }

        }
        return Response.ok(contacts).build();
    }

    /**
     * Sub-resource locator method for {uid}
     */
    @Path("{uid}")
    public ClientResource getClientResource(@PathParam("uid") String uid) {
        return ClientResource.getInstance(uid, tb);
    }
}
