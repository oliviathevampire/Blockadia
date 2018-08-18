package team.hdt.blockadia.game_engine_old.common.entity.ai.basics;

public interface AiRoutine {

    /**
     * @return true if finished.
     */
    public boolean update();

    public void interrupt();

    public String getDescription();

}
