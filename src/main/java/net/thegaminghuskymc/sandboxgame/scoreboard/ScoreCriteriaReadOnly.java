package net.thegaminghuskymc.sandboxgame.scoreboard;

public class ScoreCriteriaReadOnly extends ScoreCriteria
{
    public ScoreCriteriaReadOnly(String name)
    {
        super(name);
    }

    public boolean isReadOnly()
    {
        return true;
    }
}