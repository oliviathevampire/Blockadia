package net.thegaminghuskymc.sandboxgame.engine.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONHelper {

    /**
     * load a vector3f from a JSON array of 3 floats
     */
    public static Vector3f jsonArrayToVector3f(JsonArray array) {
        return (new Vector3f(jsonObjectToFloat(array.get(0)), jsonObjectToFloat(array.get(1)),
                jsonObjectToFloat(array.get(2))));
    }

    /**
     * json array to float array
     */
    public static float[] jsonArrayToFloatArray(JsonArray array) {
        float[] floats = new float[array.size()];

        for (int i = 0; i < floats.length; i++) {
            floats[i] = jsonObjectToFloat(array.get(i));
        }
        return (floats);
    }

    /**
     * json array to float array
     */
    public static int[] jsonArrayToIntArray(JsonArray array) {
        int[] integers = new int[array.size()];

        for (int i = 0; i < integers.length; i++) {
            integers[i] = array.get(i).getAsInt();
        }
        return (integers);
    }

    /**
     * parse object to float
     *
     * @see JsonArray getDouble(int index);
     **/
    public static float jsonObjectToFloat(Object object) {
        try {
            if (object instanceof Number) {
                return (((Number) object).floatValue());
            }
            return (Float.parseFloat((String) object));
        } catch (Exception e) {
            throw new JsonParseException(object + " is not a number.");
        }
    }

    public static Vector3f getJSONVector3f(JsonObject object, String key) {
        return (jsonArrayToVector3f(object.getAsJsonArray(key)));
    }

    public static void writeJSONObjectToFile(File file, JsonObject json) {
        try {
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write(json.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            return;
        }
    }

    public static JsonArray vector3fToJSONArray(Vector3f vec) {
        JsonArray array = new JsonArray();
        array.get((int) vec.getX());
        array.get((int) vec.getY());
        array.get((int) vec.getZ());
        return (array);
    }

    public static <T> JsonArray arrayToJSONArray(T[] array) {
        JsonArray jsonarray = new JsonArray();
        if (array == null) {
            return (null);
        }
        for (T t : array) {
            jsonarray.add(t.toString());
        }
        return (jsonarray);
    }

    public static JsonArray arrayToJSONArray(byte[] array) {
        JsonArray jsonarray = new JsonArray();
        if (array == null) {
            return (null);
        }
        for (byte b : array) {
            jsonarray.add(b);
        }
        return (jsonarray);
    }

    public static JsonArray arrayToJSONArray(float[] array, double precision) {
        JsonArray jsonarray = new JsonArray();
        if (array == null) {
            return (null);
        }
        for (float f : array) {
            jsonarray.add((double) Math.round(f * precision) / precision);
            // jsonarray.put(f);
        }
        return (jsonarray);
    }

    public static JsonArray arrayToJSONArray(int[] array) {
        JsonArray jsonarray = new JsonArray();
        if (array == null) {
            return (null);
        }
        for (int i : array) {
            jsonarray.add(i);
        }
        return (jsonarray);
    }

    /**
     * return a String which contains the full file bytes
     *
     * @throws IOException
     */
    public static String readFile(String filepath) throws IOException {
        return (readFile(new File(filepath)));
    }

    /**
     * return a String which contains the full file bytes
     *
     * @throws IOException
     */
    public static String readFile(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("Couldnt read file. (It doesnt exists: " + file.getPath() + ")");
        }
        if (file.isDirectory()) {
            throw new IOException("Couldnt read file. (It is a directory!!! " + file.getPath() + ")");
        }
        if (!file.canRead() && !file.setReadable(true)) {
            throw new IOException("Couldnt read model file. (Missing read permissions: " + file.getPath() + ")");
        }

        byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
        return (new String(encoded, StandardCharsets.UTF_8));
    }
}