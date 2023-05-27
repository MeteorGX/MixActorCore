package com.meteorcat.mix.core.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import static org.springframework.core.io.buffer.DefaultDataBufferFactory.DEFAULT_INITIAL_CAPACITY;

/**
 * Event Monitor
 *
 * @author MeteorCat
 */
public class EventMonitor<_Owner, _Event> extends ScheduledThreadPoolExecutor implements IEventMonitor<_Owner, _Event> {


    /**
     * Events
     */
    private final Map<_Owner, Map<_Event, Event>> events = new ConcurrentHashMap<>();


    /**
     * Construct Method
     *
     * @param corePoolSize thread size
     */
    public EventMonitor(int corePoolSize) {
        super(corePoolSize);
    }

    /**
     * Construct Method
     *
     * @param corePoolSize  thread size
     * @param threadFactory ThreadFactory
     */
    public EventMonitor(int corePoolSize, ThreadFactory threadFactory) {
        super(corePoolSize, threadFactory);
    }

    /**
     * Construct Method
     *
     * @param corePoolSize thread size
     * @param handler      RejectedExecutionHandler
     */
    public EventMonitor(int corePoolSize, RejectedExecutionHandler handler) {
        super(corePoolSize, handler);
    }

    /**
     * Construct Method
     *
     * @param corePoolSize  thread size
     * @param threadFactory ThreadFactory
     * @param handler       RejectedExecutionHandler
     */
    public EventMonitor(int corePoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, threadFactory, handler);
    }


    /**
     * event exists?
     *
     * @param owner Event owner
     * @return boolean
     */
    public boolean containsKey(_Owner owner) {
        return events.containsKey(owner);
    }

    /**
     * event exists?
     *
     * @param owner Event owner
     * @param event Event name
     * @return boolean
     */
    public boolean containsKey(_Owner owner, _Event event) {
        return events.containsKey(owner) && events.get(owner).containsKey(event);
    }


    /**
     * get event runnable
     *
     * @param owner Event owner
     * @return Optional<Map < _Event, Runnable>>
     */
    public Optional<Map<_Event, Event>> getEvent(_Owner owner) {
        return containsKey(owner) ? Optional.of(events.get(owner)) : Optional.empty();
    }


    /**
     * add event runnable
     *
     * @param owner    Event owner
     * @param event    Event name
     * @param e        Runnable
     * @param ownerCap Event capacity
     */
    public void putEvent(_Owner owner, _Event event, Event e, int ownerCap) {
        if (!events.containsKey(owner)) {
            events.put(owner, new HashMap<>(ownerCap));
        }
        events.get(owner).put(event, e);
    }


    /**
     * add event runnable
     *
     * @param owner Event owner
     * @param event Event name
     * @param e     Runnable
     */
    public void putEvent(_Owner owner, _Event event, Event e) {
        putEvent(owner, event, e, DEFAULT_INITIAL_CAPACITY);
    }


    /**
     * get event runnable
     *
     * @param owner Event owner
     * @param event Event name
     * @return Runnable
     */
    public Optional<Event> getEvent(_Owner owner, _Event event) {
        if (!events.containsKey(owner)) {
            return Optional.empty();
        }
        Map<_Event, Event> runnable = events.get(owner);
        return Optional.ofNullable(runnable.get(event));
    }


    /**
     * Remove event runnable
     *
     * @param owner Event owner
     * @param event Event name
     */
    public void remove(_Owner owner, _Event event) {
        synchronized (owner) {
            Optional<Event> runnableOptional = getEvent(owner, event);
            runnableOptional.ifPresent(e -> {
                if (e.getFuture() != null) {
                    e.getFuture().cancel(true);
                }
                events.get(owner).remove(event);
            });
        }
    }


    /**
     * Remove event runnable
     *
     * @param owner Event owner
     */
    public void remove(_Owner owner) {
        synchronized (owner) {
            Optional<Map<_Event, Event>> runnableOptional = getEvent(owner);
            runnableOptional.ifPresent(list -> {
                for (Map.Entry<_Event, Event> e : list.entrySet()) {
                    ScheduledFuture<?> future = e.getValue().getFuture();
                    if (future != null) {
                        future.cancel(true);
                    }
                    events.get(owner).remove(e.getKey());
                }
            });
        }
    }


    /**
     * Event execute
     *
     * @param owner Event owner
     * @param event Event name
     */
    @Override
    public void execute(_Owner owner, _Event event) {
        Optional<Event> e = getEvent(owner, event);
        if (e.isEmpty()) {
            return;
        }
        execute(() -> {
            synchronized (owner) {
                e.get().getTuple().run();
            }
        });
    }

    /**
     * Monitor event schedule
     *
     * @param owner Event owner
     * @param event Event name
     * @param delay Event delay
     * @param unit  Event timeunit
     */
    @Override
    public void schedule(_Owner owner, _Event event, long delay, TimeUnit unit) {
        Optional<Event> e = getEvent(owner, event);
        if (e.isEmpty()) {
            return;
        }
        Event runnable = e.get();
        runnable.setFuture(schedule(()->{
            synchronized (owner){
                runnable.getTuple().run();
            }
        },delay,unit));
    }


    /**
     * Monitor event schedule fixed rate
     * @param owner Event owner
     * @param event Event name
     * @param initialDelay Event initialization delay
     * @param period Event each delay
     * @param unit Event timeunit
     */
    @Override
    public void scheduleAtFixedRate(_Owner owner, _Event event, long initialDelay, long period, TimeUnit unit) {
        Optional<Event> e = getEvent(owner, event);
        if (e.isEmpty()) {
            return;
        }
        Event runnable = e.get();
        runnable.setFuture(scheduleAtFixedRate(()->{
            synchronized (owner){
                runnable.getTuple().run();
            }
        },initialDelay,period,unit));
    }

    /**
     * Monitor event schedule with fixed delay
     * @param owner Event owner
     * @param event Event name
     * @param initialDelay Event initialization delay
     * @param delay Event each delay
     * @param unit Event timeunit
     */
    @Override
    public void scheduleWithFixedDelay(_Owner owner, _Event event, long initialDelay, long delay, TimeUnit unit) {
        Optional<Event> e = getEvent(owner, event);
        if (e.isEmpty()) {
            return;
        }
        Event runnable = e.get();
        runnable.setFuture(scheduleWithFixedDelay(()->{
            synchronized (owner){
                runnable.getTuple().run();
            }
        },initialDelay,delay,unit));
    }
}
