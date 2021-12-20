/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.Marchandise;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import stateless.MarchandiseBean;
import util.Role;
import util.Secured;

/**
 * REST Web Service
 *
 * @author eroot
 */
@Path("/goods")
public class MarchandisesResource {

    @Context
    private UriInfo context;
    @EJB MarchandiseBean mb;

    /**
     * Creates a new instance of MarchandisesResource
     */
    public MarchandisesResource() {
    }

    /**
     * Retrieves representation of an instance of jaxrs.MarchandisesResource
     * @return an instance of entities.Marchandise
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator,Role.Associe,Role.Comptable,Role.Directeur,Role.Caissier})
    @Path("/list")
    public List<Marchandise> getGoods() {
        return mb.findMarchandises();
    }

    
    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator,Role.Associe,Role.Comptable,Role.Directeur,Role.Caissier})
    @Path("/sync")
    public Response syncContacts(List<Marchandise> contacts) {
        for(Marchandise c:contacts){
            Marchandise cc=mb.findMarchandise(c.getUid());
            if(cc==null){
               mb.createMarchandise(c);
            }else{
               mb.updateMarchandise(c);
            }
           
        }
        return Response.ok(contacts).build();
    }
    /**
     * Sub-resource locator method for {uid}
     */
    @Path("{uid}")
    public MarchandiseResource getMarchandiseResource(@PathParam("uid") String uid) {
        return MarchandiseResource.getInstance(uid);
    }
}
