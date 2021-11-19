package jaxrs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.interfaces.DecodedJWT;
import entities.User;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.security.interfaces.ECKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import stateless.UserBean;
import util.AuthenticatedUser;
import util.Role;
import util.Secured;
import util.ServerCrypt;

@Secured
@Provider
@Priority(value = 1000)
@Stateless
public class RequestInterceptorFilter
        implements ContainerRequestFilter {

    @Context
    private ResourceInfo restInfo;
    private static final String REALM = "keti";
    private static final String AUTH_SCHEME = "Bearer";

    @EJB
    UserBean usb;

    @Inject
    @AuthenticatedUser
    Event<String> userAuthenticatedEvent;

    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        String authorHeader = crc.getHeaderString("Authorization");
        if (!this.isTokenAuthOriginal(authorHeader)) {
            crc.abortWith(Response.status((Response.Status) Response.Status.UNAUTHORIZED).header("WWW-Authenticate", "Bearerrealm=\"keti\"").build());
            return;
        }
        Logger.getLogger(this.getClass().getName()).info("Token entrant = " + authorHeader + " " + crc.getUriInfo().getPath());
        String token = authorHeader.substring("Bearer".length()).trim();
        String uid = null;
        try {
            uid = this.validateToken(token);
            if (uid == null) {
                Logger.getLogger(this.getClass().getName()).info("token ---> xx " + uid);
                crc.abortWith(Response.status((Response.Status) Response.Status.FORBIDDEN).header("WWW-Authenticate", "Bearerrealm=\"keti\"").build());
                return;
            }
            Logger.getLogger(this.getClass().getName()).info("token ---> xx " + uid);
            this.userAuthenticatedEvent.fire(uid);
        } catch (GeneralSecurityException ex) {
            crc.abortWith(Response.status((Response.Status) Response.Status.UNAUTHORIZED).header("WWW-Authenticate", "Bearerrealm=\"keti\"").build());
            Logger.getLogger(RequestInterceptorFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        Class restClass = this.restInfo.getResourceClass();
        List roleDeclass = this.getRoles((AnnotatedElement) restClass);
        Method restMeth = this.restInfo.getResourceMethod();
        List roleDeMethod = this.getRoles((AnnotatedElement) restMeth);
        try {
            if (roleDeMethod.isEmpty()) {
                this.checkPermission(uid, roleDeclass);
            } else {
                this.checkPermission(uid, roleDeMethod);
            }
        } catch (Exception e) {
            crc.abortWith(Response.status((Response.Status) Response.Status.FORBIDDEN).header("WWW-Authenticate", "Bearerrealm=\"keti\"").build());
        }
    }

    private List<Role> getRoles(AnnotatedElement annotated) {
        if (annotated == null) {
            return new ArrayList<Role>();

        }
        Secured secured = (Secured) annotated.getAnnotation(Secured.class
        );
        if (secured == null) {
            return new ArrayList<Role>();
        }
        Role[] allowed = secured.value();
        return Arrays.asList(allowed);
    }

    private Role checkPermission(String uuid, List<Role> roles) throws Exception {
        User eng = this.usb.findUser(uuid);
        String priv = eng.getRole();
        for (Role r : roles) {
            if (!r.name().equals(priv)) {
                continue;
            }
            return r;
        }
        Logger.getLogger(this.getClass().getName()).info("Check permission ... ");
        Logger.getLogger(this.getClass().getName()).info(eng.toString());
        throw new Exception("User no permis");
    }

    private boolean isTokenAuthOriginal(String authorHeader) {
        return authorHeader != null && authorHeader.toLowerCase().startsWith("Bearer".toLowerCase() + " ");
    }

    private String validateToken(String token) throws IOException, GeneralSecurityException {
        try {
            JWT djwt = JWT.decode(token);
            ECKey eck = (ECKey) (ServerCrypt.getECPublicKeyFromString(ServerCrypt.ECPBKey));
            JWTVerifier ver = JWT.require((Algorithm) Algorithm.ECDSA256((ECKey) eck)).withIssuer(djwt.getIssuer()).build();
            DecodedJWT jjwt = ver.verify(token);
            String uid = jjwt.getSubject();
            return uid;
        } catch (InvalidClaimException e) {
            return null;
        }
    }
}
