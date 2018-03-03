package net.thegaminghuskymc.sandboxgame.scoreboard;

import net.thegaminghuskymc.sandboxgame.stats.StatBase;

public class ScoreCriteriaStat extends ScoreCriteria
{
    private final StatBase stat;

    public ScoreCriteriaStat(StatBase statIn)
    {
        super(statIn.statId);
        this.stat = statIn;
    }
}