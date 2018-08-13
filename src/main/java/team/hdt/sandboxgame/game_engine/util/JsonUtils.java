package team.hdt.sandboxgame.game_engine.util;

import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;

public class JsonUtils {

    public static void readJson(File jsonFile) {
        StringReader isr = new StringReader(jsonFile.getAbsolutePath());
        BufferedReader reader = new BufferedReader(isr);
        JsonReader jsonReader = new JsonReader(reader);
        jsonReader.setLenient(true);
        System.out.print(jsonReader.toString());
    }

}
