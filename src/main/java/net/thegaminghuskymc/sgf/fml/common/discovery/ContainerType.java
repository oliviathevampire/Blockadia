package net.thegaminghuskymc.sgf.fml.common.discovery;

import net.thegaminghuskymc.sgf.fml.common.ModContainer;

import java.util.List;

public enum ContainerType
{
    JAR(JarDiscoverer.class),
    DIR(DirectoryDiscoverer.class);

    private ITypeDiscoverer discoverer;

    private ContainerType(Class<? extends ITypeDiscoverer> discovererClass)
    {
        try
        {
            this.discoverer = discovererClass.newInstance();
        }
        catch (ReflectiveOperationException e)
        {
            throw new RuntimeException(e);
        }
    }

    public List<ModContainer> findMods(ModCandidate candidate, ASMDataTable table)
    {
        return discoverer.discover(candidate, table);
    }
}