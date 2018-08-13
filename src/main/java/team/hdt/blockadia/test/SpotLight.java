package team.hdt.blockadia.test;

import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;

public class SpotLight {

    private PointLight pointLight;

    private Vectors3f coneDirection;

    private float cutOff;

    public SpotLight(PointLight pointLight, Vectors3f coneDirection, float cutOffAngle) {
        this.pointLight = pointLight;
        this.coneDirection = coneDirection;
        setCutOffAngle(cutOffAngle);
    }

    public SpotLight(SpotLight spotLight) {
        this(new PointLight(spotLight.getPointLight()),
                new Vectors3f(spotLight.getConeDirection()),
                0);
        setCutOff(spotLight.getCutOff());
    }

    public PointLight getPointLight() {
        return pointLight;
    }

    public void setPointLight(PointLight pointLight) {
        this.pointLight = pointLight;
    }

    public Vectors3f getConeDirection() {
        return coneDirection;
    }

    public void setConeDirection(Vectors3f coneDirection) {
        this.coneDirection = coneDirection;
    }

    public float getCutOff() {
        return cutOff;
    }

    public void setCutOff(float cutOff) {
        this.cutOff = cutOff;
    }
    
    public final void setCutOffAngle(float cutOffAngle) {
        this.setCutOff((float)Math.cos(Math.toRadians(cutOffAngle)));
    }

}