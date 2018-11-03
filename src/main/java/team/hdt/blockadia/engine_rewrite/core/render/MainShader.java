package team.hdt.blockadia.engine_rewrite.core.render;

public class MainShader extends ShaderProgram {

    public static final String VERTEX = "./src/engine_rewrite/core/render/shader/vertexshader.shad";
    public static final String FRAGMENT = "./src/engine_rewrite/core/render/shader/fragmentshader.shad";


    @Override
    public void bindAllAttributes() {
        super.bindAttributes(0,"vertices");
    }

    public MainShader() {
        super(FRAGMENT, VERTEX);
    }
}
