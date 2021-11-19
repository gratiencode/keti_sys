/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javax.ws.rs.NameBinding
 *  util.Role
 *  util.Secured
 */
package util;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.ws.rs.NameBinding;
import util.Role;

@NameBinding
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE, ElementType.METHOD})
public @interface Secured {
    public Role[] value() default {};
}

