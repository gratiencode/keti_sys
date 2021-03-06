/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.Succursale;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import stateless.SuccursaleBean;
import util.AuthenticatedUser;
import util.Role;
import util.Secured;

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

    @Context
    ResourceContext contextx;

    /**
     * Creates a new instance of SuccursalsResource
     */
    public SuccursalsResource() {
    }

    @PostConstruct
    public void init() {

    }
    private static final Logger LOG = Logger.getLogger(SuccursalsResource.class.getName());

    /**
     * Retrieves representation of an instance of jaxrs.SuccursalsResource
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/save/point")
    @Secured({Role.Administrator, Role.Associe, Role.Directeur, Role.Comptable})
    public Response saveJson(Succursale suc) {
        Succursale s = sbn.createSuccursale(suc);
        return Response.ok(s).build();
    }
    
    @PATCH
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/group/delete")
    @Secured({Role.Administrator, Role.Associe})
    public Response groupDelete(List<Succursale> gdl){
        for(Succursale s:gdl){
            sbn.deleteSuccursale(s);
        }
        return Response.ok(gdl).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/sync")
    @Secured({Role.Administrator, Role.Associe, Role.Directeur, Role.Comptable,Role.Caissier})
    public Response sync(List<Succursale> sucs) {
        List<Succursale> rst = new ArrayList<>();
        for (Succursale suc : sucs) {
            Succursale s=null;
            if (sbn.findSuccursale(suc.getUid()) == null) {
                s = sbn.createSuccursale(suc);
            } else {
                s = sbn.updateSuccursale(s);
            }
            rst.add(s);
        }
        return Response.ok(rst).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/list")
    @Secured({Role.Administrator, Role.Associe, Role.Directeur, Role.Comptable})
    public List<Succursale> getJson() {
        return sbn.findSuccursales();
    }

    /**
     * Sub-resource locator method for {uid}
     */
    @Path("{uid}")
    public SuccursalResource getSuccursalResource(@PathParam("uid") String uid) {
        return SuccursalResource.getInstance(uid, sbn);
    }
}
