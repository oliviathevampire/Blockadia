package net.thegaminghuskymc.sgf.fml.relauncher;

public enum Side
{
    CLIENT,
    SERVER;

    public boolean isServer()
    {
        return !this.isClient();
    }

    public boolean isClient()
    {
        return this == CLIENT;
    }
}