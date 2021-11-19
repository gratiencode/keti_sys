/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jaxrs;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author eroot
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(jaxrs.AuthResource.class);
        resources.add(jaxrs.AuthsResource.class);
        resources.add(jaxrs.CorsFilter.class);
        resources.add(jaxrs.RequestInterceptorFilter.class);
        resources.add(jaxrs.SuccursalResource.class);
        resources.add(jaxrs.SuccursalsResource.class);
        resources.add(jaxrs.UserResource.class);
        resources.add(jaxrs.UsersResource.class);
    }
    
}
