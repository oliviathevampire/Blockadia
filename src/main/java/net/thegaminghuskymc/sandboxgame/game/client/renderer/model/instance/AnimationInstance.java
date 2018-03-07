package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.instance;

import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.animation.KeyFrame;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.animation.ModelSkeletonAnimation;

import java.util.ArrayList;

public class AnimationInstance {

    /**
     * state of the animation (loop, freeze...)
     */
    private static final int STATE_RUNNING = 0;
    private static final int STATE_LOOP = 1;
    private static final int STATE_FREEZE = 2;
    private static final int STATE_STOP = 3;
    /**
     * the animation
     */
    private final ModelSkeletonAnimation animation;
    /**
     * the begin time
     */
    private long time;
    private byte state;

    public AnimationInstance(ModelSkeletonAnimation animation) {
        this.animation = animation;
        this.restart();
    }

    public final void restart() {
        this.state = STATE_RUNNING;
        this.time = 0;
    }

    /**
     * upate the animation instance, return true if the animation is ended
     */
    public void update(long dt) {
        if (this.state == STATE_STOP || this.state == STATE_FREEZE) {
            return;
        }

        this.setTime(this.time + dt);
    }

    /**
     * freeze the animation
     */
    public void freeze() {
        this.state = STATE_FREEZE;
    }

    /**
     * loop the animation
     */
    public void loop() {
        this.state = STATE_LOOP;
    }

    /**
     * stop the animation roughly
     */
    public void stop() {
        this.state = STATE_STOP;
    }

    /**
     * end the animation loop by ending the current loop
     */
    public void stopLoop() {
        this.state = STATE_RUNNING;
    }

    public boolean isStopped() {
        return (this.state == STATE_STOP);
    }

    /**
     * return the previous and next frame for this animation
     */
    public KeyFrame[] getFrames() {
        ArrayList<KeyFrame> frames = this.animation.getKeyFrames();
        if (frames == null || frames.isEmpty()) {
            return (null);
        }
        KeyFrame prev = frames.get(0);
        KeyFrame next = frames.get(0);
        for (int i = 1; i < frames.size(); i++) {
            next = frames.get(i);
            if (next.getTime() >= this.time) {
                break;
            }
            prev = next;
        }
        return (new KeyFrame[]{prev, next});

    }

    public long getTime() {
        return (this.time);
    }

    public void setTime(long t) {
        long duration = this.animation.getDuration();
        this.time = t;
        if (this.time >= duration) {
            if (this.state == STATE_LOOP) {
                if (this.time > duration) {
                    this.time %= duration;
                }
            } else {
                this.state = STATE_STOP;
            }
        }
    }
}
