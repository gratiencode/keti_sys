/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.Contacts;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import util.Role;
import util.Secured;

/**
 * REST Web Service
 *
 * @author eroot
 */
@Path("/contacts")
public class ContactsResource {

    @Context
    private UriInfo context;
    @EJB ContactBean cb;

    /**
     * Creates a new instance of ContactsResource
     */
    public ContactsResource() {
    }

    /**
     * Retrieves representation of an instance of jaxrs.ContactsResource
     * @return an instance of entities.Contacts
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Contacts getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator,Role.Associe,Role.Comptable,Role.Directeur})
    @Path("alter")
    public List<Contacts> getJson(List<Contacts> contacts) {
        for(Contacts c:contacts){
           cb.updateContacts(c);
        }
        return contacts;
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator,Role.Associe,Role.Comptable,Role.Directeur})
    @Path("/list/{tierId}")
    public List<Contacts> getJson(@PathParam("tierId") String tier) {
        return cb.findContactsByTier(tier);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator,Role.Associe,Role.Comptable,Role.Directeur})
    @Path("/list")
    public List<Contacts> getContacts() {
        return cb.findContacts();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator,Role.Associe,Role.Comptable,Role.Directeur})
    @Path("/save/point")
    public Response saveContacts(List<Contacts> contacts) {
        for(Contacts c:contacts){
           cb.createContacts(c);
        }
        return Response.ok(contacts).build();
    }
    
    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator,Role.Associe,Role.Comptable,Role.Directeur})
    @Path("/sync")
    public Response syncContacts(List<Contacts> contacts) {
        for(Contacts c:contacts){
            Contacts cc=cb.findContacts(c.getUid());
            if(cc==null){
                cb.createContacts(c);
            }else{
               cb.updateContacts(c);  
            }
           
        }
        return Response.ok(contacts).build();
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator,Role.Associe,Role.Comptable,Role.Directeur})
    @Path("/delete")
    public Response delteContacts(List<Contacts> contacts) {
        for(Contacts c:contacts){
            Contacts cc=cb.findContacts(c.getUid());
            if(cc!=null){
                cb.deleteContacts(c);
            }
           
        }
        return Response.ok().build();
    }


    /**
     * Sub-resource locator method for {uid}
     */
    @Path("{uid}")
    public ContactResource getContactResource(@PathParam("uid") String uid) {
        return ContactResource.getInstance(uid);
    }
}
