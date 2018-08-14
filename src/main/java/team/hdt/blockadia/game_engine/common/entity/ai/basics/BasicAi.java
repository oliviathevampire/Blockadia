package team.hdt.blockadia.game_engine.common.entity.ai.basics;

import team.hdt.blockadia.game_engine.common.entity.ai.Ai;
import team.hdt.blockadia.game_engine.common.entity.ai.AiProvidingComponent;

public class BasicAi implements Ai {

    private final AiRoutine aiRoutine;

    private final AiProvidingComponent component;
    private final int priority;

    public BasicAi(AiRoutine aiRoutine, int priority, AiProvidingComponent component) {
        this.aiRoutine = aiRoutine;
        this.priority = priority;
        this.component = component;
    }

    @Override
    public void interrupt() {
        aiRoutine.interrupt();
    }

    @Override
    public String getDescription() {
        return aiRoutine.getDescription();
    }

    @Override
    public boolean carryOut() {
        return aiRoutine.update();
    }

    @Override
    public float getPriority() {
        return priority;
    }

    @Override
    public AiProvidingComponent getComponent() {
        return component;
    }

}
