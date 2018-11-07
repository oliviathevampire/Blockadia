package team.hdt.blockadia.engine_rewrite;

import team.hdt.blockadia.engine_rewrite.core.render.MasterRenderer;
import team.hdt.blockadia.engine_rewrite.core.render.ShaderProgram;

public class Blockadia {

    public static String name = "Blockadia";
    public static int width = 1280;
    public static int height = 720;
    public static Display display;
    public static MasterRenderer masterRenderer;
    public static ShaderProgram shaderProgram;
    public Blockadia instance;

    public static void main(String[] args) {
        display = new Display(name, width, height);
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Blockadia.name = name;
    }

    public Blockadia getInstance() {
        return instance;
    }
}
