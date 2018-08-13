package team.hdt.blockadia.game_engine.common.entity.ai;

public interface Ai {
	
	/**
	 * @return {@code true} if finished, otherwise {@code false}.
	 */
	public boolean carryOut();
	public float getPriority();
	public AiProvidingComponent getComponent();
	public void interrupt();
	public String getDescription();

}
