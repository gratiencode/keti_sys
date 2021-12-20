/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.Voyage;
import java.util.ArrayList;
import java.util.List;
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
import stateless.VoyageBean;
import util.Role;
import util.Secured;

/**
 * REST Web Service
 *
 * @author eroot
 */
@Path("/travel")
public class VoyagesResource {

    @Context
    private UriInfo context;
    @EJB
    VoyageBean vb;

    /**
     * Creates a new instance of VoyagesResource
     */
    public VoyagesResource() {
    }

    /**
     * Retrieves representation of an instance of jaxrs.VoyagesResource
     *
     * @return an instance of entities.Voyage
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    @Secured({Role.Administrator, Role.Associe, Role.Comptable, Role.Directeur, Role.Caissier})
    public List<Voyage> getJson() {
        return vb.findVoyages();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list/{vid}")
    @Secured({Role.Administrator, Role.Associe, Role.Comptable, Role.Directeur, Role.Caissier})
    public List<Voyage> getByVehicule(@PathParam("vid") String vid) {
        return vb.findVoyageByVehicule(vid);
    }

    @PATCH
    @Path("/sync")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator, Role.Associe, Role.Comptable, Role.Directeur, Role.Caissier})
    public Response sync(List<Voyage> voyages) {
        List<Voyage> lrst = new ArrayList<>();
        voyages.forEach(v -> {
            Voyage vy = vb.findVoyage(v.getUid());
            lrst.add(vy == null ? vb.createVoyage(v) : vb.updateVoyage(v));
        });
        return Response.ok(lrst).build();
    }

    @POST
    @Path("/save/point")
    @Consumes("application/json")
    @Produces("application/json")
    @Secured({Role.Administrator, Role.Associe, Role.Caissier, Role.Comptable, Role.Directeur})
    public Response createVoyage(Voyage v) {
        return Response.ok(v).build();
    }

    /**
     * Sub-resource locator method for {uid}
     */
    @Path("{uid}")
    public VoyageResource getVoyageResource(@PathParam("uid") String uid) {
        return VoyageResource.getInstance(vb, uid);
    }
}
