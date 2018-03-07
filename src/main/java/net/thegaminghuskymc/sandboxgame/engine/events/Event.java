package net.thegaminghuskymc.sandboxgame.engine.events;

import java.util.ArrayList;

/**
 * abstract class for an engine event
 */
public abstract class Event {

    private static final int STATE_PRE = (1 << 0);
    private static final int STATE_POST = (1 << 1);
    private static final int STATE_CANCELLED = (1 << 2);

    private int state;

    public Event() {
        this.state = 0;
    }

    public final void reset() {
        this.setState(STATE_PRE);
        this.onReset();
    }

    /**
     * run this event with the given list of listener
     * <p>
     * if event was cancelled, nothing happens
     * <p>
     * if one of the listener cancel the event, it stop, and the listeners which
     * wasn't already called are ignored.
     *
     * @param listeners
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public final void run(ArrayList<Listener> listeners) {
        if (this.isCancelled()) {
            return;
        }

        if (listeners == null) {
            this.process();
            return;
        }

        for (Listener listener : listeners) {
            listener.pre(this);
            if (this.isCancelled()) {
                return;
            }
        }

        this.process();

        this.setState(STATE_POST);

        for (Listener listener : listeners) {
            listener.post(this);
            if (this.isCancelled()) {
                this.unprocess();
                return;
            }
        }
    }

    // TODO : slowly moves every event processing in this function, and implements
    // unprocessing

    /**
     * process this event, shouldn't be called actually,
     *
     * @see EventManager#invokeEvent(Event)
     */
    protected abstract void process();

    /**
     * undo this event (called after a 'process()')
     */
    protected abstract void unprocess();

    /**
     * reset the event
     */
    protected abstract void onReset();

    /**
     * cancel this event: if it has already been processed, try to undo it if it
     * hasnt been processed already, ignore processing.
     */
    public final void cancel() {
        this.setState(STATE_CANCELLED);
    }

    public final boolean isCancelled() {
        return (this.hasState(STATE_CANCELLED));
    }

    public String getName() {
        return (this.getClass().getSimpleName());
    }

    private final boolean hasState(int state) {
        return ((this.state & state) == state);
    }

    private final void setState(int state) {
        this.state = this.state | state;
    }
}
