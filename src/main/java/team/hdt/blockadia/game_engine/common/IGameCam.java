package team.hdt.blockadia.game_engine.common;

import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.blockadia.game_engine.util.BinaryReader;
import team.hdt.blockadia.game_engine.util.BinaryWriter;

public interface IGameCam extends CameraInterface {

	public void loadState(BinaryReader reader) throws Exception;

	public void saveState(BinaryWriter writer);

	public void resetPosition();

	public void focusOn(Vectors3f point);

	public void enable(boolean enabled);
	
	public void setTargetEntity(Vectors3f entityPos);

}
