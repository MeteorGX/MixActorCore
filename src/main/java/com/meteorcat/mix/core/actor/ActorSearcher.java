package com.meteorcat.mix.core.actor;

import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Search Actor Component
 *
 * @author MeteorCat
 */
public final class ActorSearcher {


    /**
     * Search ActorControllers
     *
     * @param context Spring context
     * @param clazz   Require class
     * @return Set<Class < ?>>
     */
    public static Set<Class<?>> searchControllers(ApplicationContext context, Class<?> clazz) {
        Set<Class<?>> classes = new LinkedHashSet<>();
        if (context == null) {
            return classes;
        }

        String[] beanNames = context.getBeanNamesForAnnotation(ActorController.class);
        for (String beanName : beanNames) {
            Class<?> instance = context.getType(beanName);
            if (instance == null) {
                continue;
            }

            if (clazz == null) {
                classes.add(instance);
            } else {
                ActorController actorController = instance.getAnnotation(ActorController.class);
                if (actorController != null) {
                    Arrays.stream(actorController.value())
                            .filter(hit -> hit.equals(clazz))
                            .findFirst()
                            .ifPresent(hit -> classes.add(instance));
                }
            }
        }

        return classes;
    }


    /**
     * Search ActorMapping
     *
     * @param context  Spring context
     * @param classes  Actor controller
     * @param instance Runtime
     * @param <T>      Runtime Type
     * @return Map<Integer, ActorTuple>
     */
    public static <T> Map<Integer, ActorTuple> searchMapping(ApplicationContext context, Set<Class<?>> classes, T instance) throws IllegalAccessException {
        Map<Integer, ActorTuple> tuples = new LinkedHashMap<>();
        if (context == null) {
            return tuples;
        }

        for (Class<?> clazz : classes) {
            Object handler = context.getBean(clazz);
            if (instance != null) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field filed : fields) {
                    ActorRuntime runtime = filed.getAnnotation(ActorRuntime.class);
                    if (runtime != null) {
                        filed.setAccessible(true);
                        filed.set(handler, instance);
                    }
                }
            }


            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                ActorMapping mapping = method.getAnnotation(ActorMapping.class);
                if (mapping != null) {
                    int value = mapping.value();
                    if (tuples.containsKey(value)) {
                        throw new IllegalAccessException(String.format("ActorMapping clash by %d", value));
                    }
                    ActorTuple tuple = new ActorTuple(value, handler, method);
                    tuples.put(value, tuple);
                }
            }
        }
        return tuples;
    }


    /**
     * Search all actor mapping
     *
     * @param applicationContext Spring Context
     * @return Map<Integer, ProtoTuple>
     */
    public static <T> Map<Integer, ActorTuple> searchMapping(ApplicationContext applicationContext, Class<T> clazz, T instance) throws IllegalAccessException {
        Set<Class<?>> classes = searchControllers(applicationContext, clazz);
        return searchMapping(applicationContext, classes, instance);
    }


}
