package team.hdt.sandboxgame.game_engine.client.entity;

import team.hdt.sandboxgame.game_engine.client.entity.util.AllEntityAspects;
import team.hdt.sandboxgame.game_engine.client.entity.util.SharePeacefulAspects;
import team.hdt.sandboxgame.game_engine.client.entity.util.SharedHostileAspects;
import team.hdt.sandboxgame.game_engine.client.rendering.TexturedModel;
import team.hdt.sandboxgame.game_engine.common.util.interfaces.Nullable;

public class BaseEntity {
    public float positionX;
    public float positionY;
    public float positionZ;
    public float rotationXZ;
    public float rotationXY;
    public float rotationZY;
    public TexturedModel model;
    public static int ID;
    public boolean CanburnInDay;
    @Nullable
    public boolean IsAquatic;
    public float scale = AllEntityAspects.scale;

    public BaseEntity(TexturedModel model, int id, String type) {
        this.model = model;
        this.ID = id;
        switch (type){
            case ("hostile"):
                setHostileDefalt();
            case ("peaceful"):
                setPassiveDefalt();
        }

    }
    public BaseEntity(TexturedModel model, int id, boolean canburnInDay, boolean isAquatic) {
        this.model = model;
        this.ID = id;
        this.CanburnInDay = canburnInDay;
        this.IsAquatic = isAquatic;

    }
    public void setPassiveDefalt() {
        this.CanburnInDay = SharePeacefulAspects.burnInDay;
        this.IsAquatic = SharePeacefulAspects.liveInWater;
    }
    public void setHostileDefalt(){
        this.CanburnInDay = SharedHostileAspects.burnInDay;
        this.IsAquatic = SharedHostileAspects.liveInWater;
    }
    public void move(float x,float y , float z){
        this.positionX = x;
        this.positionY = y;
        this.positionZ = z;
    }

    public void rotation(float rotationXY,float rotationXZ, float rotationZY) {
        this.rotationXY = rotationXY;
        this.rotationXZ = rotationXZ;
        this.rotationZY = rotationZY;
    }

    public TexturedModel getModel() {
        return model;
    }

    public static int getID() {
        return ID;
    }

    public boolean CanburnInDay() {
        return CanburnInDay;
    }

    public boolean isAquatic() {
        return IsAquatic;
    }

    public float getScale() {
        return scale;
    }
}
