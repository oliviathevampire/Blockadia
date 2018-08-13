package team.hdt.blockadia.test;

import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;

public class SceneLight {

    private Vectors3f ambientLight;
    
    private Vectors3f skyBoxLight;

    private PointLight[] pointLightList;
    
    private SpotLight[] spotLightList;
    
    private DirectionalLight directionalLight;

    public Vectors3f getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(Vectors3f ambientLight) {
        this.ambientLight = ambientLight;
    }

    public PointLight[] getPointLightList() {
        return pointLightList;
    }

    public void setPointLightList(PointLight[] pointLightList) {
        this.pointLightList = pointLightList;
    }

    public SpotLight[] getSpotLightList() {
        return spotLightList;
    }

    public void setSpotLightList(SpotLight[] spotLightList) {
        this.spotLightList = spotLightList;
    }

    public DirectionalLight getDirectionalLight() {
        return directionalLight;
    }

    public void setDirectionalLight(DirectionalLight directionalLight) {
        this.directionalLight = directionalLight;
    }

    public Vectors3f getSkyBoxLight() {
        return skyBoxLight;
    }

    public void setSkyBoxLight(Vectors3f skyBoxLight) {
        this.skyBoxLight = skyBoxLight;
    }
    
}