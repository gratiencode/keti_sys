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
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import stateless.UserBean;
import util.Role;
import util.Secured;

/**
 * REST Web Service
 *
 * @author eroot
 */
public class UserResource {

    private String uid;
    UserBean usb;
    User user;

    /**
     * Creates a new instance of UserResource
     */
    private UserResource(UserBean usb,String uid) {
        this.uid = uid;
        user=usb.findUser(uid);
    }

    /**
     * Get instance of the UserResource
     */
    public static UserResource getInstance(UserBean usb,String uid) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of UserResource class.
        return new UserResource(usb,uid);
    }

    /**
     * Retrieves representation of an instance of jaxrs.UserResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator,Role.Associe,Role.Directeur,Role.Comptable})
    public Response getJson() {
        return Response.ok(user).build();
    }

    /**
     * PUT method for updating or creating an instance of UserResource
     * @param content representation for the resource
     * @return 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator,Role.Associe,Role.Directeur})
    @Path("/update")
    public Response putJson(User content) {
        user.setIdSucursale(content.getIdSucursale());
        user.setNom(content.getNom());
        user.setPassword(content.getPassword());
        user.setPrenom(content.getPrenom());
        user.setRole(content.getRole());
        user.setUsername(content.getUsername());
        return Response.ok(user).build();
    }

    /**
     * DELETE method for resource UserResource
     */
    @DELETE
    public void delete() {
    }
}
