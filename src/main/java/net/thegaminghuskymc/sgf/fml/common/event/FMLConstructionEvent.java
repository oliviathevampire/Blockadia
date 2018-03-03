package net.thegaminghuskymc.sgf.fml.common.event;

import com.google.common.collect.ListMultimap;
import net.thegaminghuskymc.sgf.fml.common.LoaderState;
import net.thegaminghuskymc.sgf.fml.common.ModClassLoader;
import net.thegaminghuskymc.sgf.fml.common.discovery.ASMDataTable;

public class FMLConstructionEvent extends FMLStateEvent
{
    private ModClassLoader modClassLoader;
    private ASMDataTable asmData;
    private ListMultimap<String,String> reverseDependencies;

    @SuppressWarnings("unchecked")
    public FMLConstructionEvent(Object... eventData)
    {
        this.modClassLoader = (ModClassLoader)eventData[0];
        this.asmData = (ASMDataTable) eventData[1];
        this.reverseDependencies = (ListMultimap<String, String>) eventData[2];
    }

    public ModClassLoader getModClassLoader()
    {
        return modClassLoader;
    }

    @Override
    public LoaderState.ModState getModState()
    {
        return LoaderState.ModState.CONSTRUCTED;
    }

    public ASMDataTable getASMHarvestedData()
    {
        return asmData;
    }

    public ListMultimap<String, String> getReverseDependencies()
    {
        return reverseDependencies;
    }
}