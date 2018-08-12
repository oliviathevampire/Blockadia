package team.hdt.sandboxgame.game_engine.common.entity.ai.basics;

import aiComponent.Ai;
import aiComponent.AiProvidingComponent;
import growth.GrowthComponent;

public class AgeDependentAI implements Ai {

	private final GrowthComponent growth;
	
	private final AiRoutine[] routines;

	public AgeDependentAI(GrowthComponent growth, AiRoutine... routines) {
		this.routines = routines;
		this.growth = growth;
	}

	@Override
	public boolean carryOut() {
		routines[growth.getStageNumber()].update();
		return false;
	}
	
	@Override
	public String getDescription() {
		return routines[growth.getStageNumber()].getDescription();
	}

	@Override
	public void interrupt() {
		routines[growth.getStageNumber()].interrupt();
	}

	@Override
	public float getPriority() {
		return 0;
	}

	@Override
	public AiProvidingComponent getComponent() {
		return null;
	}

}
