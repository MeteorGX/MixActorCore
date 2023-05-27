package com.meteorcat.mix.core.actor;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Actor instance tuple
 * @author MeteorCat
 */
public class ActorTuple {

    /**
     * actor value
     */
    private final Integer value;


    /**
     * actor instance
     */
    private final Object instance;


    /**
     * actor entry
     */
    private final Method method;


    /**
     * construct method
     * @param value actor value
     * @param instance actor instance
     * @param method actor entry
     */
    public ActorTuple(int value, Object instance, Method method) {
        this.value = value;
        this.instance = instance;
        this.method = method;
    }


    /**
     * get actor value
     * @return Integer
     */
    public Integer getValue() {
        return value;
    }

    /**
     * get actor instance
     * @return Object
     */
    public Object getInstance() {
        return instance;
    }

    /**
     * get actor entry
     * @return Method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * inherit hash code
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    /**
     * inherit equal method
     * @param obj instance
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ActorTuple){
            ActorTuple other = (ActorTuple) obj;
            return other.value.equals(value);
        }
        return false;
    }
}
