package com.meteorcat.mix.core.event;

import java.util.concurrent.ScheduledFuture;

/**
 * Event tuple interface
 * @author MeteorCat
 */
public interface IEvent {

    /**
     * Get event tuple
     * @return T
     */
    Runnable getTuple();


    /**
     * Get event future
     * @return ScheduledFuture<?>
     */
    ScheduledFuture<?> getFuture();

    /**
     * Set event future
     * @param future ScheduledFuture<?>
     */
    void setFuture(ScheduledFuture<?> future);

}
