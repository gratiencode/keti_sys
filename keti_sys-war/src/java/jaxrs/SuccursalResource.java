/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

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
public class SuccursalResource {

    private String uid;

    /**
     * Creates a new instance of SuccursalResource
     */
    private SuccursalResource(String uid) {
        this.uid = uid;
    }

    /**
     * Get instance of the SuccursalResource
     */
    public static SuccursalResource getInstance(String uid) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of SuccursalResource class.
        return new SuccursalResource(uid);
    }

    /**
     * Retrieves representation of an instance of jaxrs.SuccursalResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of SuccursalResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    /**
     * DELETE method for resource SuccursalResource
     */
    @DELETE
    public void delete() {
    }
}
