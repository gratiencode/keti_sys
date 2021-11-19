/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javax.inject.Qualifier
 *  util.AuthenticatedUser
 */
package util;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

@Qualifier
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
public @interface AuthenticatedUser {
}

