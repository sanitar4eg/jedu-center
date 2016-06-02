package edu.netcracker.center.web.mvc;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleMapping {

    /**
     * A list of required security roles (e.g. "ROLE_ADMIN).
     */
    String[] value() default {};
}
