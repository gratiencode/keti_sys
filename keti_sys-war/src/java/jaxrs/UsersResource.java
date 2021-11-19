/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.User;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
import stateless.UserBean;
import util.AuthenticatedUser;
import util.Role;
import util.Secured;

/**
 * REST Web Service
 *
 * @author eroot
 */
@Path("/user")
@Stateless
public class UsersResource {

    @Context
    private UriInfo context;
    @EJB
    UserBean usb;
    
    @Inject
    @AuthenticatedUser
    User user;

    /**
     * Creates a new instance of UsersResource
     */
    public UsersResource() {
    }

    /**
     * Retrieves representation of an instance of jaxrs.UsersResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    @POST
    @Path("/save/point")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured({Role.Administrator,Role.Associe,Role.Directeur})
    public Response createUser(User us){
       us.setCreatedAt(new Date());
       User u= usb.createSecured(us);
       return Response.ok(u).build();
    }

    /**
     * Sub-resource locator method for {uid}
     */
    @Path("{uid}")
    public UserResource getUserResource(@PathParam("uid") String uid) {
        return UserResource.getInstance(usb,uid);
    }
}
