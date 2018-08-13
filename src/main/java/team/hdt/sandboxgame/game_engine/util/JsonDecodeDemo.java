package team.hdt.sandboxgame.game_engine.util;


import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

class JsonDecodeDemo {

   public static void main(String[] args){

       Path path = Paths.get("src", "main", "resources", "assets", "sandboxgame", "gui_packs");

       try {
           JsonReader reader = new JsonReader(new FileReader(path.toFile()));
           reader.setLenient(true);
           JsonParser parser = new JsonParser();

           try {
               Object obj = parser.parse(path.toString());
               System.out.println(obj);
           } catch(JsonParseException pe){
               System.out.println(pe.getLocalizedMessage());
           }
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }
   }
}