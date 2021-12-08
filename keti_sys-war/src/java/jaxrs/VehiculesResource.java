/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.Vehicule;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
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
import stateless.VehiculeBean;
import util.Role;
import util.Secured;

/**
 * REST Web Service
 *
 * @author eroot
 */
@Path("/vehicules")
public class VehiculesResource {
    
    @Context
    private UriInfo context;
    @EJB
    VehiculeBean vb;

    /**
     * Creates a new instance of VehiculesResource
     */
    public VehiculesResource() {
    }

    /**
     * Retrieves representation of an instance of jaxrs.VehiculesResource
     *
     * @return an instance of entities.Vehicule
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator, Role.Associe, Role.Comptable, Role.Directeur})
    public List<Vehicule> getJsons() {
        return vb.findVehicules();
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Secured({Role.Administrator, Role.Associe, Role.Comptable, Role.Directeur})
    @Path("save/point")
    public Response createVehicule(Vehicule v) {
        Vehicule createVehicule = vb.createVehicule(v);
        return Response.ok(createVehicule).build();
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Secured({Role.Administrator, Role.Associe, Role.Comptable, Role.Directeur})
    @Path("sync")
    public Response synch(List<Vehicule> vs) {
        List<Vehicule> vhs = new ArrayList<>();
        for (Vehicule v : vs) {
            Vehicule createVehicule;
            if (vb.findVehicule(v.getUid()) == null) {
                createVehicule = vb.createVehicule(v);
            } else {
                createVehicule = vb.updateVehicule(v);
            }
            vhs.add(createVehicule);
        }
        return Response.ok(vhs).build();
    }

    /**
     * Sub-resource locator method for {uid}
     */
    @Path("{uid}")
    public VehiculeResource getVehiculeResource(@PathParam("uid") String uid) {
        return VehiculeResource.getInstance(uid, vb);
    }
}
