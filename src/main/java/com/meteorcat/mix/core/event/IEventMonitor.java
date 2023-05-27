package com.meteorcat.mix.core.event;

import java.util.concurrent.TimeUnit;

/**
 * Event monitor interface
 * @author MeteorCat
 */
public interface IEventMonitor<_Owner,_Event> {


    /**
     * execute event
     * @param owner Event owner
     * @param event Event name
     */
    void execute(_Owner owner,_Event event);


    /**
     * Monitor event schedule
     * @param owner Event owner
     * @param event Event name
     * @param delay Event delay
     * @param unit Event timeunit
     */
    void schedule(_Owner owner,_Event event, long delay, TimeUnit unit);


    /**
     * Monitor event schedule fixed rate
     * @param owner Event owner
     * @param event Event name
     * @param initialDelay Event initialization delay
     * @param period Event each delay
     * @param unit Event timeunit
     */
    void scheduleAtFixedRate(_Owner owner,_Event event, long initialDelay, long period, TimeUnit unit);

    /**
     * Monitor event schedule with fixed delay
     * @param owner Event owner
     * @param event Event name
     * @param initialDelay Event initialization delay
     * @param delay Event each delay
     * @param unit Event timeunit
     */
    void scheduleWithFixedDelay(_Owner owner,_Event event, long initialDelay, long delay, TimeUnit unit);



}
