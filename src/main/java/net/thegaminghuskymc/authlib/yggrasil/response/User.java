package net.thegaminghuskymc.authlib.yggrasil.response;

import net.thegaminghuskymc.authlib.properties.PropertyMap;

public class User {
    private String id;
    private PropertyMap properties;

    public String getId() {
        return id;
    }

    public PropertyMap getProperties() {
        return properties;
    }
}