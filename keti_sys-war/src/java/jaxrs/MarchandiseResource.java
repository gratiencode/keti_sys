/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.Marchandise;
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
public class MarchandiseResource {

    private String uid;

    /**
     * Creates a new instance of MarchandiseResource
     */
    private MarchandiseResource(String uid) {
        this.uid = uid;
    }

    /**
     * Get instance of the MarchandiseResource
     */
    public static MarchandiseResource getInstance(String uid) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of MarchandiseResource class.
        return new MarchandiseResource(uid);
    }

    /**
     * Retrieves representation of an instance of jaxrs.MarchandiseResource
     * @return an instance of entities.Marchandise
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Marchandise getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of MarchandiseResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(Marchandise content) {
    }

    /**
     * DELETE method for resource MarchandiseResource
     */
    @DELETE
    public void delete() {
    }
}
