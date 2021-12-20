/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.Contacts;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author eroot
 */
public class ContactResource {

    private String uid;

    /**
     * Creates a new instance of ContactResource
     */
    private ContactResource(String uid) {
        this.uid = uid;
    }

    /**
     * Get instance of the ContactResource
     */
    public static ContactResource getInstance(String uid) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of ContactResource class.
        return new ContactResource(uid);
    }

    /**
     * Retrieves representation of an instance of jaxrs.ContactResource
     * @return an instance of entities.Contacts
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Contacts getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ContactResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(Contacts content) {
    }

    /**
     * DELETE method for resource ContactResource
     */
    @DELETE
    public void delete() {
    }
}
