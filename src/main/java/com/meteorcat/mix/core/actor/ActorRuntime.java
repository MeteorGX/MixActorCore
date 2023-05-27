package com.meteorcat.mix.core.actor;

import java.lang.annotation.*;

/**
 * Actor runtime component
 * @author MeteorCat
 */
@Inherited
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ActorRuntime { }

