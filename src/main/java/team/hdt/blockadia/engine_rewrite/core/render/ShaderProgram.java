package team.hdt.blockadia.engine_rewrite.core.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class ShaderProgram {

    private int programID, fragmentShaderID, vertexShaderID;
    private String fragmentFile, vertexFile;

    public ShaderProgram(String fragmentFile, String vertexFile) {
        this.fragmentFile = fragmentFile;
        this.vertexFile = vertexFile;
    }

    public void create(){
        programID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShaderID, readFile(vertexFile));
        GL20.glCompileShader(vertexShaderID);

        if(GL20.glGetShaderi(vertexShaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.out.println("vertex shader failed to be created:" + GL20.glGetShaderInfoLog(vertexShaderID));
        }

        fragmentShaderID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(fragmentShaderID, readFile(vertexFile));
        GL20.glCompileShader(fragmentShaderID);

        if(GL20.glGetShaderi(vertexShaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.out.println("fragment shader failed to be created:" + GL20.glGetShaderInfoLog(fragmentShaderID));
        }

        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);

        GL20.glLinkProgram(programID);

        if(GL20.glGetProgrami(fragmentShaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.err.println("fragment shader:" +  GL20.glGetShaderInfoLog(fragmentShaderID));
        }

        GL20.glValidateProgram(programID);

    }

    public abstract void bindAllAttributes();

    public void bindAttributes(int index, String location){
        GL20.glBindAttribLocation(programID,index,location);
    }

    public void bind(){
        GL20.glUseProgram(programID);
    }

    public void remove(){
        GL20.glDetachShader(programID,vertexShaderID);
        GL20.glDetachShader(programID,fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteProgram(fragmentShaderID);
        GL20.glDeleteProgram(programID);

    }

    private CharSequence readFile(String file) {
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try{
            reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null){
                stringBuilder.append(line).append("\n");
            }
        }catch(IOException e){
            e.getStackTrace();
        }
        return stringBuilder.toString();
    }
}
