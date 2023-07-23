package com.joseneyra.brewery.security.perms.order;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)             // Tells the Java compiler to retain this annotation at runtime. Annotations need it work properly
@PreAuthorize("hasAuthority('admin.order.pickup') OR " +
        "hasAuthority('customer.order.pickup') AND @beerOrderAuthenticationManager.customerIdMatches(authentication, #customerId)")
public @interface OrderPickUpPermission {
}
