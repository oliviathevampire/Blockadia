package team.hdt.blockadia.game_engine.core.util.math;

import ga.pheonix.utillib.utils.vectors.Vectors3f;

public class Transform {

    public Vectors3f position;
    public Vectors3f rotation;
    public Vectors3f scale;

    public Transform() {
        this(0, 0, 1);
    }

    /**
     * create a new Transform and create a new Vector for each argument
     *
     * @param position 'sets float argument to - new Vectors3f(position, position, position)'
     * @param rotation 'sets float argument to - new Vectors3f(rotation, rotation, rotation)'
     * @param scale    'sets float argument to - new Vectors3f(scale, scale, scale)'
     */
    public Transform(float position, float rotation, float scale) {
        this(new Vectors3f(position), new Vectors3f(rotation), new Vectors3f(scale));
    }

    public Transform(Vectors3f position, Vectors3f rotation, Vectors3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Vectors3f getPosition() {
        return position;
    }

    public void setPosition(Vectors3f position) {
        this.position = position;
    }

    public Vectors3f getRotation() {
        return rotation;
    }

    public void setRotation(Vectors3f rotation) {
        this.rotation = rotation;
    }

    public Vectors3f getScale() {
        return scale;
    }

    public void setScale(Vectors3f scale) {
        this.scale = scale;
    }
}