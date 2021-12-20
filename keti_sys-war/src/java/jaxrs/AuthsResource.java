/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import entities.Session;
import entities.User;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
import stateless.SessionBean;
import stateless.SuccursaleBean;
import stateless.UserBean;
import util.Credentials;
import util.LoginResult;
import util.ServerCrypt;
import util.Token;

/**
 * REST Web Service
 *
 * @author eroot
 */
@Path("/auth")
@Stateless
public class AuthsResource {

    @Context
    private UriInfo context;
    @EJB
    UserBean usb;
    @EJB
    SessionBean sb;

    /**
     * Creates a new instance of AuthsResource
     */
    public AuthsResource() {
    }

     private User authenicate(String username, String pswd) {
        User us = this.usb.login(username, pswd);
        return us;
    }

    private String decodeToken(String token) throws IOException {
        try {
            JWT djwt = JWT.decode(token);
            String uid = djwt.getSubject();
            return uid;
        }
        catch (InvalidClaimException e) {
            return null;
        }
    }

    private String genToken(String uid) throws GeneralSecurityException, IOException, NullPointerException {
        try {
            ECKey eck = (ECKey)(ServerCrypt.getECPrivateKeyFromString(ServerCrypt.ECPVKey));
            Algorithm algo = Algorithm.ECDSA256((ECKey)eck);
            String token = JWT.create().withExpiresAt(Date.from(LocalDateTime.now().plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant()))
                    .withIssuedAt(new Date()).
                    withIssuer(this.context.getAbsolutePath().toString())
                    .withSubject(uid)
                    .withJWTId(UUID.randomUUID().toString())
                    .sign(algo);
            User u = this.usb.findUser(uid);
            if (u != null) {
                Session ss = new Session();
                ss.setDateDebut(new Date());
                ss.setToken(token);
                ss.setDateFin(Date.from(LocalDateTime.now().plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant()));
                ss.setIdUser(u);
                this.sb.createSession(ss);
            }
            return token;
        }
        catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AuthsResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    /**
     * Cette fonction sert de connexion d'un utilisateur existant au systeme
     * @param cred
     * @return 
     */
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path(value="/signin")
    public Response signin(Credentials cred){
        try {
            String username = cred.getUsername();
            String password = cred.getPassword();
            User u = this.authenicate(username, password);
            if (u == null) {
                return Response.ok("Utilisateur ou mot de passe incorecte").build();
            }
            if(!u.getActif()){
              return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            String token = this.genToken(u.getUid());
            Token tkn=new Token();
            tkn.setToken(token);
            LoginResult rst=new LoginResult(u.getRole(), tkn);
            rst.setSuccursale(u.getIdSucursale()); 
            return Response.ok(rst).build();
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(AuthsResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AuthsResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(AuthsResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
    
    @POST
    @Produces(value={"application/json"})
    @Consumes(value={"application/json"})
    @Path(value="/refresh/token")
    public Response refreshToken(Token token) {
        try {
            User en;
            Logger.getLogger(this.getClass().getName()).info("Refreshing... ");
            String tokn = token.getToken();
            String uid = this.decodeToken(tokn);
            Logger.getLogger(this.getClass().getName()).info("Refreshing... uid " + uid);
            String newToken = null;
            if (uid != null && (en = this.usb.findUser(uid)) != null) {
                User u = usb.findUser(uid);
                this.sb.deleteSessions(u.getUid());
                newToken = this.genToken(uid);
                Logger.getLogger(this.getClass().getName()).info("le parametre initial cxxxxxxxxx !" + newToken);
            }
            return Response.ok(newToken).build();
        }
        catch (IOException ex) {
            Logger.getLogger(AuthsResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (GeneralSecurityException ex) {
            Logger.getLogger(AuthsResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException e) {
            Logger.getLogger(AuthsResource.class.getName()).log(Level.SEVERE, null, e);
        }
        return Response.status((Response.Status)Response.Status.NO_CONTENT).build();
    }
    /**
     * Retrieves representation of an instance of jaxrs.AuthsResource
     * @return an instance of User
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list/{suc}")
    public List<User> getUsers(@PathParam("suc") String suc) {
        return usb.findUserByPhone(suc);
    }

    /**
     * Sub-resource locator method for {uid}
     */
    @Path("{uid}")
    public AuthResource getAuthResource(@PathParam("uid") String uid) {
        return AuthResource.getInstance(uid);
    }
}
