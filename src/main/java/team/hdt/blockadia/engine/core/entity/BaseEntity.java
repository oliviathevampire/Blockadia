package team.hdt.blockadia.engine.core.entity;

import ga.pheonix.utillib.utils.anouncments.Nonnull;
import ga.pheonix.utillib.utils.anouncments.Nullable;
import ga.pheonix.utillib.utils.entity.AllEntityAspects;
import ga.pheonix.utillib.utils.entity.SharePeacefulAspects;
import ga.pheonix.utillib.utils.entity.SharedHostileAspects;
import ga.pheonix.utillib.utils.model.TexturedModel;
import ga.pheonix.utillib.utils.vectors.Vectors3f;

public class BaseEntity {
    public static int ID;
    public float positionX;
    public float positionY;
    public float positionZ;
    public float rotationXZ;
    public float rotationXY;
    public float rotationZY;
    public TexturedModel model;
    public boolean CanburnInDay;
    @Nullable
    public boolean IsAquatic;
    @Nonnull
    public boolean isItem;
    public float scale = AllEntityAspects.scale;

    public BaseEntity(TexturedModel model, int id, String type) {
        this.model = model;
        this.ID = id;
        this.isItem = false;
        switch (type) {
            case ("hostile"):
                setHostileDefault();
            case ("peaceful"):
                setPassiveDefault();
        }

    }

    public BaseEntity(TexturedModel model, int id, boolean canburnInDay, boolean isAquatic) {
        this.model = model;
        this.ID = id;
        this.CanburnInDay = canburnInDay;
        this.IsAquatic = isAquatic;
        this.isItem = false;

    }

    public BaseEntity(TexturedModel model, int id, boolean isItem) {
        this.model = model;
        this.ID = id;
        this.isItem = isItem;
    }

    public BaseEntity(TexturedModel model, int id, boolean isItem, boolean canburnInDay, float scale) {
        this.model = model;
        this.ID = id;
        this.isItem = isItem;
        this.CanburnInDay = canburnInDay;
        if ((scale == 0)) {
            this.scale = 1.0F;
        } else {
            this.scale = scale;
        }
    }

    public static int getID() {
        return ID;
    }

    public void setPassiveDefault() {
        this.CanburnInDay = SharePeacefulAspects.burnInDay;
        this.IsAquatic = SharePeacefulAspects.liveInWater;
    }

    public void setHostileDefault() {
        this.CanburnInDay = SharedHostileAspects.burnInDay;
        this.IsAquatic = SharedHostileAspects.liveInWater;
    }

    public void move(float x, float y, float z) {
        this.positionX = x;
        this.positionY = y;
        this.positionZ = z;
    }

    public void rotation(float rotationXY, float rotationXZ, float rotationZY) {
        this.rotationXY = rotationXY;
        this.rotationXZ = rotationXZ;
        this.rotationZY = rotationZY;
    }

    public TexturedModel getModel() {
        return model;
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

    public Vectors3f getPosition() {
        return new Vectors3f(positionX, positionY, positionZ);
    }

    public Vectors3f getRotation() {
        return new Vectors3f(rotationXY, rotationXZ, rotationZY);
    }

}