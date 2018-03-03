package net.thegaminghuskymc.sgf.fml.common;

import net.thegaminghuskymc.sgf.fml.common.event.*;

public enum LoaderState
{
    NOINIT("Uninitialized",null),
    LOADING("Loading",null),
    CONSTRUCTING("Constructing mods",FMLConstructionEvent.class),
    PREINITIALIZATION("Pre-initializing mods", FMLPreInitializationEvent.class),
    INITIALIZATION("Initializing mods", FMLInitializationEvent.class),
    POSTINITIALIZATION("Post-initializing mods", FMLPostInitializationEvent.class),
    AVAILABLE("Mod loading complete", FMLLoadCompleteEvent.class),
    ERRORED("Mod Loading errored",null);


    private Class<? extends FMLStateEvent> eventClass;
    private String name;

    private LoaderState(String name, Class<? extends FMLStateEvent> event)
    {
        this.name = name;
        this.eventClass = event;
    }

    public LoaderState transition(boolean errored)
    {
        if (errored)
        {
            return ERRORED;
        }
        return values()[ordinal() < values().length ? ordinal()+1 : ordinal()];
    }

    public boolean hasEvent()
    {
        return eventClass != null;
    }

    public FMLStateEvent getEvent(Object... eventData)
    {
        try
        {
            return eventClass.getConstructor(Object[].class).newInstance((Object)eventData);
        }
        catch (ReflectiveOperationException e)
        {
            throw new RuntimeException(e);
        }
    }
    public LoaderState requiredState()
    {
        if (this == NOINIT) return NOINIT;
        return LoaderState.values()[this.ordinal()-1];
    }
    
    public String getPrettyName()
    {
        return name;
    }
    
    public enum ModState
    {
        UNLOADED       ("Unloaded",         "U"),
        LOADED         ("Loaded",           "L"),
        CONSTRUCTED    ("Constructed",      "C"),
        PREINITIALIZED ("Pre-initialized",  "H"),
        ADDRESOURCES ("Add-Resources",  "A"),
        INITIALIZED    ("Initialized",      "I"),
        POSTINITIALIZED("Post-initialized", "J"),
        AVAILABLE      ("Available",        "A"),
        DISABLED       ("Disabled",         "D"),
        ERRORED        ("Errored",          "E");

        private String label;
        private String marker;

        private ModState(String label, String marker)
        {
            this.label = label;
            this.marker = marker;
        }

        @Override
        public String toString()
        {
            return this.label;
        }

        public String getMarker()
        {
            return this.marker;
        }
    }
}