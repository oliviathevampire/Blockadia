package team.hdt.sandboxgame.game_engine.client.rendering.fontRendering;

public interface FontVariablesCalculator {
	
	public float calculateAntialiasValue(float size);
	public float calculateEdgeValue(float size);

}
