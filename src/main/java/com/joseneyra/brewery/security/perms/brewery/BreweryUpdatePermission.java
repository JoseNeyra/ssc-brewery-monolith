package com.joseneyra.brewery.security.perms.brewery;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)             // Tells the Java compiler to retain this annotation at runtime. Annotations need it work properly
@PreAuthorize("hasAuthority('brewery.update')")
public @interface BreweryUpdatePermission {
}
