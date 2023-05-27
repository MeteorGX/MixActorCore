package com.meteorcat.mix.core.actor;

import java.lang.annotation.*;

/**
 * Actor entry annotation
 * @author MeteorCat
 */
@Inherited
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ActorMapping {

    /**
     *  Actor entry type
     * @return int
     */
    int value();

}
