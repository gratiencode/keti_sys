/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javax.ws.rs.container.ContainerRequestContext
 *  javax.ws.rs.container.ContainerResponseContext
 *  javax.ws.rs.container.ContainerResponseFilter
 *  javax.ws.rs.core.MultivaluedMap
 *  javax.ws.rs.ext.Provider
 *  jaxrs.CorsFilter
 */
package jaxrs;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

@Provider
public class CorsFilter
implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext req, ContainerResponseContext res) throws IOException {
        res.getHeaders().add("Access-Control-Allow-Origin", "*");
        res.getHeaders().add("Access-Control-Allow-Credentials", "true");
        res.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization,x-requested-with");
        res.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH");
        res.getHeaders().add("X-Frame-Options", "*");
    }
}

