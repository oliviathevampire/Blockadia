package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.animation;

import java.util.ArrayList;

public class ModelSkeletonAnimation {

    /**
     * the animation name
     */
    private final String name;

    /**
     * the keyframes for this animation
     */
    private final ArrayList<KeyFrame> keyFrames;

    /**
     * animation length
     */
    private final long duration;

    public ModelSkeletonAnimation(String name, long duration, ArrayList<KeyFrame> keyFrames) {
        this.name = name;
        this.duration = duration;
        this.keyFrames = keyFrames;
    }

    public String getName() {
        return (name);
    }

    /**
     * get the keyframe list of this animation
     */
    public ArrayList<KeyFrame> getKeyFrames() {
        return (this.keyFrames);
    }

    /**
     * get the time of the last keyframe for this animation
     */
    public final long getDuration() {
        return (this.duration);
    }
}
