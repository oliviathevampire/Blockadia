/*
package team.hdt.blockadia.game_engine_old.common.entity.ai.basics;

import team.hdt.blockadia.game_engine_old.common.entity.ai.Ai;
import team.hdt.blockadia.game_engine_old.common.entity.ai.AiProvidingComponent;

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
*/
