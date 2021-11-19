/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.User;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author eroot
 */
public class AuthResource {

    private String uid;

    /**
     * Creates a new instance of AuthResource
     */
    private AuthResource(String uid) {
        this.uid = uid;
    }

    /**
     * Get instance of the AuthResource
     */
    public static AuthResource getInstance(String uid) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of AuthResource class.
        return new AuthResource(uid);
    }

    /**
     * Retrieves representation of an instance of jaxrs.AuthResource
     * @return an instance of User
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of AuthResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(User content) {
    }

    /**
     * DELETE method for resource AuthResource
     */
    @DELETE
    public void delete() {
    }
}
