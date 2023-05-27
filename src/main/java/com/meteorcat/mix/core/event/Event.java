package com.meteorcat.mix.core.event;

import java.util.concurrent.ScheduledFuture;

/**
 * @author MeteorCat
 */
public class Event implements IEvent {

    /**
     * Callback
     */
    private final Runnable runnable;

    /**
     * Future
     */
    private ScheduledFuture<?> future;


    /**
     * Construct Method
     * @param runnable callback
     */
    public Event(Runnable runnable) {
        this.runnable = runnable;
        this.future = null;
    }


    /**
     * Construct Method
     *
     * @param runnable callback
     * @param future   future
     */
    public Event(Runnable runnable, ScheduledFuture<?> future) {
        this.runnable = runnable;
        this.future = future;
    }


    /**
     * get event callback
     * @return Runnable
     */
    @Override
    public Runnable getTuple() {
        return runnable;
    }


    /**
     * get event future
     * @return ScheduledFuture<?>
     */
    @Override
    public ScheduledFuture<?> getFuture() {
        return future;
    }

    /**
     * set event future
     * @param future ScheduledFuture<?>
     */
    @Override
    public void setFuture(ScheduledFuture<?> future) {
        this.future = future;
    }


    /**
     * inherit hash code
     * @return int
     */
    @Override
    public int hashCode() {
        return runnable.hashCode();
    }


    /**
     * inherit equal method
     * @param obj instance
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Event){
            Event event = (Event) obj;
            return event.runnable.equals(runnable);
        }
        return false;
    }
}
