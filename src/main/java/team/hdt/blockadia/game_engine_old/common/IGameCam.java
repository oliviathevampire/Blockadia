package team.hdt.blockadia.game_engine_old.common;

import team.hdt.blockadia.game_engine.core.util.math.vectors.Vectors3f;
import team.hdt.blockadia.game_engine_old.util.BinaryReader;
import team.hdt.blockadia.game_engine_old.util.BinaryWriter;

public interface IGameCam extends CameraInterface {


    public void loadState(BinaryReader reader) throws Exception;

    public void saveState(BinaryWriter writer);

    public void resetPosition();

    public void focusOn(Vectors3f point);

    public void enable(boolean enabled);

    public void setTargetEntity(Vectors3f entityPos);

}
