package com.meteorcat.mix.core.actor;

import java.lang.annotation.*;

/**
 * Actor controller annotation
 * @author MeteorCat
 */
@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ActorController {

    /**
     * Require Class
     * @return Class<?>[]
     */
    Class<?>[] value() default {};
}
