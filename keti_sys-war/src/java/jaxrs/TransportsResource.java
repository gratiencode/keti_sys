/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import entities.Succursale;
import entities.Transporter;
import entities.Vehicule;
import entities.Voyage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
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
import stateless.TransporterBean;
import stateless.VoyageBean;
import util.Role;
import util.Secured;
import util.TrackResult;

/**
 * REST Web Service
 *
 * @author eroot
 */
@Path("/loads")
public class TransportsResource {

    @Context
    private UriInfo context;
    @EJB
    TransporterBean tb;
    @EJB
    VoyageBean vb;

    /**
     * Creates a new instance of TransportsResource
     */
    public TransportsResource() {
    }

    /**
     * Retrieves representation of an instance of jaxrs.TransportsResource
     *
     * @return an instance of entities.Transporter
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    @Secured({Role.Administrator, Role.Associe, Role.Caissier, Role.Comptable, Role.Directeur})
    public List<Transporter> getJson() {
        return tb.findTransporters();
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/save/point")
    @Secured({Role.Administrator, Role.Directeur, Role.Associe, Role.Caissier, Role.Comptable})
    public Response saveTrans(Transporter n) {
        return Response.ok(tb.createTransporter(n)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/trans/track/{tnumber}")
    public List<TrackResult> trackLuggage(@PathParam("tnumber") String number) {
        List<TrackResult> rsult = new ArrayList<>();
        try {
            int tn = Integer.parseInt(number);
            List<Transporter> tracks = tb.findTransporterByTracking(tn);
            LOG.info(tn+" Loads--> "+tracks.size());
            List<String> trax = new ArrayList<>();
            Vehicule v = tracks.get(0).getIdVehicule();
            List<Voyage> voyages = vb.findVoyageByVehicule(v.getUid());
            voyages.forEach((Voyage vy) -> {
                Succursale sc = vy.getSuccursalleId();
                TrackResult trv = new TrackResult();
                trv.setDateVoyage(vy.getDateArriver());
                trv.setEtat(vy.getEtat());
                trv.setPlaqueVehicule(v.getPlaque());
                trv.setTypeVehicule(v.getTypeVehicule() + " " + v.getModeleVehicule());
                trv.setSuccursaleName(sc.getNomSuccursale() + " " + sc.getAdresse());
                trv.setTrackingNumber(tn);
                tracks.forEach((Transporter tr) -> {
                    trax.add(tr.getIdMarchandise().getNomMarchandise() + " " + tr.getQuantite() + " " + tr.getIdMarchandise().getDescription());
                });
                trv.setCargaison(trax);
                LOG.info(number+" "+trv.getPlaqueVehicule());
                rsult.add(trv);
            });
        } catch (Exception e) {

        }
        return rsult;
    }
    private static final Logger LOG = Logger.getLogger(TransportsResource.class.getName());

    @PATCH
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/sync")
    @Secured({Role.Administrator, Role.Associe, Role.Caissier, Role.Comptable, Role.Directeur})
    public Response sync(List<Transporter> trs) {
        List<Transporter> trss = new ArrayList<>();
        trs.forEach((Transporter t) -> {
            Transporter tr = tb.findTransporter(t.getUid());
            trss.add(tr == null ? tb.createTransporter(t) : tb.updateTransporter(t));
        });
        return Response.ok(trss).build();
    }
    
    @PATCH
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/mark/withdraws")
    @Secured({Role.Administrator, Role.Associe, Role.Caissier, Role.Comptable, Role.Directeur})
    public Response changeToRetrait(List<Transporter> trs) {
        List<Transporter> trss = new ArrayList<>();
        trs.forEach((Transporter t) -> {
            Transporter tr = tb.findTransporter(t.getUid());
            tr.setObservation("Déjà rétiré"); 
            trss.add(tb.updateTransporter(t));
        });
        return Response.ok(trss).build();
    }

    /**
     * Sub-resource locator method for {uid}
     */
    @Path("{uid}")
    public TransportResource getTransportResource(@PathParam("uid") String uid) {
        return TransportResource.getInstance(tb, uid);
    }
}
