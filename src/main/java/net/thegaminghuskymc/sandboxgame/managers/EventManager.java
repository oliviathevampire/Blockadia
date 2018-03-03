package net.thegaminghuskymc.sandboxgame.managers;

import net.thegaminghuskymc.sandboxgame.Logger;
import net.thegaminghuskymc.sandboxgame.events.Event;
import net.thegaminghuskymc.sandboxgame.events.Listener;

import java.util.ArrayList;
import java.util.HashMap;

public class EventManager extends GenericManager<EventHandler> {

    private static EventManager instance;

    private HashMap<Class<? extends Event>, ArrayList<Listener>> eventListeners;

    public EventManager(ResourceManager resourceManager) {
        super(resourceManager);
        instance = this;
    }

    public static final EventManager instance() {
        return (instance);
    }

    /**
     * raise an event
     */
    public void invokeEvent(Event event) {
        ArrayList<Listener> listeners = this.eventListeners.get(event.getClass());
        event.run(listeners);
    }

    /**
     * a listener to the mouse hovering the gui
     *
     * @param listener
     */
    public final <T extends Event> void addListener(Listener listener) {
        if (listener == null) {
            return;
        }
        ArrayList<Listener> lst = this.eventListeners.get(listener.getEventClass());
        if (lst == null) {
            lst = new ArrayList<Listener>();
            this.eventListeners.put(listener.getEventClass(), lst);
            super.registerObject(new EventHandler(listener.getEventClass(), lst));
        }

        lst.add(listener);
        Logger.get().log(Logger.Level.FINE, "Added an event listener : " + listener.getClass().getSimpleName() + " on : "
                + listener.getEventClass().getSimpleName());
    }

    /**
     * remove a listener
     */
    public <T extends Event> void removeListener(Listener<T> callback) {
        ArrayList<Listener> event = this.eventListeners.get(callback.getEventClass());
        if (event == null) {
            Logger.get().log(Logger.Level.ERROR, "Tried to remove an event callback on an un-existing event! "
                    + callback.getEventClass().getSimpleName());
            return;
        }
        event.remove(callback);
        Logger.get().log(Logger.Level.FINE, "Removed event callback : " + callback.getClass().getSimpleName() + " on : "
                + callback.getEventClass().getSimpleName());
    }

    @Override
    protected void onObjectRegistered(EventHandler object) {
    }

    @Override
    public void onInitialized() {
        this.eventListeners = new HashMap<Class<? extends Event>, ArrayList<Listener>>();
    }

    @Override
    public void onLoaded() {
    }

    @Override
    protected void onDeinitialized() {
        this.eventListeners.clear();
        this.eventListeners = null;
    }

    @Override
    protected void onUnloaded() {
        this.eventListeners.clear();
    }
}

@SuppressWarnings({"rawtypes"})
class EventHandler {

    private final ArrayList<Listener> callbacks;
    private final Class<? extends Event> event;

    public EventHandler(Class<? extends Event> event, ArrayList<Listener> lst) {
        this.event = event;
        this.callbacks = lst;
    }

    @Override
    public boolean equals(Object handler) {
        return (((EventHandler) handler).getEvent() == this.getEvent());
    }

    public Class<? extends Event> getEvent() {
        return (this.event);
    }

    public ArrayList<Listener> getCallbacks() {
        return (this.callbacks);
    }

}