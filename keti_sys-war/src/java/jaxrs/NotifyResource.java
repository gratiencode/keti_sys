/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import java.io.IOException;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

/**
 * REST Web Service
 *
 * @author eroot
 */
@Path("notify")
@Singleton
public class NotifyResource {

    @Context
    private UriInfo context;


   

    public NotifyResource() {
    }
    private static final Logger LOG = Logger.getLogger(NotifyResource.class.getName());
    private volatile SseBroadcaster sseBroadcaster;
    @Context
    private Sse sse;
 
    @PostConstruct
    public void initialize() {
        this.sseBroadcaster = this.sse.newBroadcaster();
    }
 
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String broadcastMessage(String message) {
        final OutboundSseEvent event = sse.newEventBuilder()
            .name("message:")
            .mediaType(MediaType.TEXT_PLAIN_TYPE)
            .data(String.class, message)
            .build();
        sseBroadcaster.broadcast(event);
        return "Message '" + message + "' has been broadcast.";
    }
 
     @GET
   @Path("/subscribe")
   @Produces(MediaType.SERVER_SENT_EVENTS)
   public void subscribe(@Context SseEventSink sink) throws IOException{
      sink.send(this.sse.newEvent("Bienvenue!"));
        LOG.info("Send notification");
        this.sseBroadcaster.register(sink);
   }

}
