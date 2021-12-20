/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.Transporter;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import stateless.TransporterBean;
import util.Role;
import util.Secured;

/**
 * REST Web Service
 *
 * @author eroot
 */
public class TransportResource {

    private String uid;
    Transporter t;
    TransporterBean tb;

    /**
     * Creates a new instance of TransportResource
     */
    private TransportResource(TransporterBean tb,String uid) {
        this.uid = uid;
        this.tb=tb;
        this.t=this.tb.findTransporter(uid);
    }

    /**
     * Get instance of the TransportResource
     */
    public static TransportResource getInstance(TransporterBean tb,String uid) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of TransportResource class.
        return new TransportResource(tb,uid);
    }

    /**
     * Retrieves representation of an instance of jaxrs TransportResource
     * @return an instance of entities.Transporter
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Transporter getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }


    /**
     * DELETE method for resource TransportResource
     */
    @DELETE
    @Path("delete")
    @Secured({Role.Administrator,Role.Associe,Role.Directeur})
    public void delete() {
        tb.deleteTransporter(t);
    }
}
