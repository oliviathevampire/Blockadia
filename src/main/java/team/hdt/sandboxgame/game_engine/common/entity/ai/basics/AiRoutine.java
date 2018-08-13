package team.hdt.sandboxgame.game_engine.common.entity.ai.basics;

public interface AiRoutine {
	
	/**
	 * @return true if finished.
	 */
	public boolean update();
	public void interrupt();
	public String getDescription();

}
