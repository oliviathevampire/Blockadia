package net.thegaminghuskymc.sandboxgame.engine;

import com.google.gson.JsonObject;
import net.thegaminghuskymc.sandboxgame.engine.util.JSONHelper;

import java.io.File;
import java.io.IOException;

public class Config {

    private final String filepath;
    private JsonObject values;

    protected Config(String filepath) {
        this.values = new JsonObject();
        this.filepath = filepath;
    }

    public final String getFilepath() {
        return (this.filepath);
    }

    public final JsonObject getValues() {
        return (this.values);
    }

    /**
     * @param hierarchy : the hierarchy of this object (since topest layer of the json
     *                  object)
     * @return : the associated objects, or null if doesn't exists
     */
    public final JsonObject getObject(String... hierarchy) {
        JsonObject jsonObject = this.values;
        for (String child : hierarchy) {
            jsonObject = jsonObject.getAsJsonObject(child);
        }
        return (jsonObject);
    }

    public final String getString(String key, String defaultValue, String... hierarchy) {
        JsonObject jsonObject = this.getObject(hierarchy);
        if (jsonObject != null) {
            try {
                return (jsonObject.get(key).getAsString());
            } catch (Exception e) {
            }
        }
        return (defaultValue);
    }

    public final double getDouble(String key, double defaultValue, String... hierarchy) {
        JsonObject jsonObject = this.getObject(hierarchy);
        if (jsonObject != null) {
            try {
                return (jsonObject.get(key).getAsDouble());
            } catch (Exception e) {
            }
        }
        return (defaultValue);
    }

    public final float getFloat(String key, float defaultValue, String... hierarchy) {
        JsonObject jsonObject = this.getObject(hierarchy);
        if (jsonObject != null) {
            try {
                return (jsonObject.get(key).getAsFloat());
            } catch (Exception e) {
            }
        }
        return (defaultValue);
    }

    public final int getInt(String key, int defaultValue, String... hierarchy) {
        JsonObject jsonObject = this.getObject(hierarchy);
        if (jsonObject != null) {
            try {
                return (jsonObject.get(key).getAsInt());
            } catch (Exception e) {
            }
        }
        return (defaultValue);
    }

    /**
     * load the config
     *
     * @return true if the config was loaded from it file, and the config is filled
     * with the values, else it returns false and the config is asusmed
     * empty
     */
    public final boolean load() {
        File file = new File(this.filepath);
        if (!file.exists()) {
            return (false);
        }
        try {
            String src = JSONHelper.readFile(file);
            this.values = new JsonObject().get(src).getAsJsonObject();
            return (true);
        } catch (Exception e) {
            Logger.get().log(Logger.Level.ERROR, "Couldn't load config", e.getLocalizedMessage());
            this.values = new JsonObject();
        }
        return (false);
    }

    /**
     * save the config to it associated file
     */
    public final void save() {
        try {
            File file = new File(this.filepath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            JSONHelper.writeJSONObjectToFile(file, this.values);
        } catch (IOException e) {
            Logger.get().log(Logger.Level.ERROR, "Couldn't load config", e.getLocalizedMessage());
        }
    }
}
