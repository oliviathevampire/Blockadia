package team.hdt.sandboxgame.game_engine;

import team.hdt.sandboxgame.game_engine.util.JsonUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonTestThing {

    public static void main(String[] args) {
        Path path = Paths.get("src", "main", "resources", "assets", "sandboxgame", "gui_pack", "crafting_table.json");
        JsonUtils.readJson(path.toFile());
    }

}