package team.hdt.blockadia.engine_rewrite.core.render;

public class MainShader extends ShaderProgram {

    public static final String VERTEX = "./src/engine_rewrite/core/render/shader/vertexshader";
    public static final String FRAGMENT = "./src/engine_rewrite/core/render/shader/fragmentshader";

    private static final int MAX_LIGHTS = 4;

    private int location_transformationMatrix;
    private int location_projectiontionMatrix;
    private int location_viewMatrix;
    private int[] location_lightPosition;
    private int[] location_lightColour;
    private int[] location_attenuation;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_useFakeLighting;
    private int location_skyColour;
    private int location_numberOfRows;
    private int location_offset;



    @Override
    public void bindAllAttributes() {
        super.bindAttributes(0,"vertices");
    }

    public MainShader() {
        super(FRAGMENT, VERTEX);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectiontionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_useFakeLighting = super.getUniformLocation("useFakeLighting");
        location_skyColour = super.getUniformLocation("skyColour");
        location_numberOfRows = super.getUniformLocation("numberOfRows");
        location_offset = super.getUniformLocation("offset");

        location_lightPosition = new int[MAX_LIGHTS];
        location_lightColour = new int[MAX_LIGHTS];
        location_attenuation = new int[MAX_LIGHTS];
        for (int i = 0; i < MAX_LIGHTS; i++) {
            location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
            location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
            location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
        }
    }
}
