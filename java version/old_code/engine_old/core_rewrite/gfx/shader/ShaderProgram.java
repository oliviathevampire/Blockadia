package team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.shader;

import org.joml.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.util.Identifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

/**
 * <em><b>Copyright (c) 2018 Ocelot5836.</b></em>
 * 
 * <br>
 * </br>
 * 
 * The root for any kind of shader. Handles all of the gl stuff and allows the user to load objects to the shaders form java code.
 * 
 * @author Ocelot5836
 */
public abstract class ShaderProgram {

	// protected static Game game = Game.getGame();

	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;

	private FloatBuffer matrix3Buffer = BufferUtils.createFloatBuffer(3 * 3);
	private FloatBuffer matrix4Buffer = BufferUtils.createFloatBuffer(4 * 4);

	/**
	 * The default implementation of a shader program containing what any shader program needs.
	 * 
	 * @param domain
	 *            The domain the shader is in
	 * @param baseName
	 *            Takes the name and looks for files named baseName_vertex.glsl and baseName_fragment.glsl. The folder these are also searched for is res/assets/domain/shaders.
	 */
	public ShaderProgram(String domain, String baseName) {
		this(new Identifier(domain, "shaders/" + baseName + "_vert.glsl"), new Identifier(domain, "shaders/" + baseName + "_frag.glsl"));
	}

	/**
	 * The default implementation of a shader program containing what any shader program needs.
	 * 
	 * @param vertexFile
	 *            The vertex shader file location found as /vertexFile.glsl
	 * @param fragmentFile
	 *            The fragment shader file location found as /fragmentFile.glsl
	 */
	public ShaderProgram(Identifier vertexFile, Identifier fragmentFile) {
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}

	/**
	 * Binds all the attributes to the shader files.
	 */
	protected abstract void bindAttributes();

	/**
	 * Sets all the uniform location integers to the locations of the variables in the shaders.
	 */
	protected abstract void getAllUniformLocations();

	protected void bindAttribute(int attribute, String variableName) {
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}

	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(programID, uniformName);
	}

	protected void loadFloat(int location, float value) {
		if (GL20.glGetUniformf(programID, location) != value) {
			GL20.glUniform1f(location, value);
		}
	}

	protected void loadInt(int location, int value) {
		if (GL20.glGetUniformi(programID, location) != value) {
			GL20.glUniform1i(location, value);
		}
	}

	protected void loadBoolean(int location, boolean value) {
		if (GL20.glGetUniformf(programID, location) != (value ? 1 : 0)) {
			GL20.glUniform1f(location, value ? 1 : 0);
		}
	}

	protected void loadVector(int location, Vector2f vector) {
		this.loadVector(location, vector.x, vector.y);
	}

	protected void loadVector(int location, float x, float y) {
		float[] uniformValues = new float[2];
		GL20.glGetUniformfv(programID, location, uniformValues);
		if (uniformValues[0] != x || uniformValues[1] != y) {
			GL20.glUniform2f(location, x, y);
		}
	}

	protected void loadVector(int location, Vector3f vector) {
		this.loadVector(location, vector.x, vector.y, vector.z);
	}

	protected void loadVector(int location, float x, float y, float z) {
		float[] uniformValues = new float[3];
		GL20.glGetUniformfv(programID, location, uniformValues);
		if (uniformValues[0] != x || uniformValues[1] != y || uniformValues[2] != z) {
			GL20.glUniform3f(location, x, y, z);
		}
	}

	protected void loadVector(int location, Vector4f vector) {
		this.loadVector(location, vector.x, vector.y, vector.z, vector.w);
	}

	protected void loadVector(int location, float x, float y, float z, float w) {
		float[] uniformValues = new float[4];
		GL20.glGetUniformfv(programID, location, uniformValues);
		if (uniformValues[0] != x || uniformValues[1] != y || uniformValues[2] != z || uniformValues[3] != w) {
			GL20.glUniform4f(location, x, y, z, w);
		}
	}

	protected void loadMatrix(int location, Matrix3f matrix) {
		GL20.glUniformMatrix3fv(location, false, matrix.get(matrix3Buffer));
	}

	// TODO use this later maybe
	// System.out.println(matrix4Buffer.get(0) + " " + matrix4Buffer.get(4) + " " + matrix4Buffer.get(8) + " " + matrix4Buffer.get(12) + "\n" + matrix4Buffer.get(1) + " " + matrix4Buffer.get(5) + " " + matrix4Buffer.get(9) + " " + matrix4Buffer.get(13) + "\n" + matrix4Buffer.get(2) + " " + matrix4Buffer.get(6) + " " + matrix4Buffer.get(10) + " " + matrix4Buffer.get(14) + "\n" + matrix4Buffer.get(3) + " " + matrix4Buffer.get(7) + " " + matrix4Buffer.get(11) + " " + matrix4Buffer.get(15) + "\n");
	protected void loadMatrix(int location, Matrix4f matrix) {
		GL20.glUniformMatrix4fv(location, false, matrix.get(matrix4Buffer));
	}

	public void start() {
		GL20.glUseProgram(programID);
	}

	public void stop() {
		GL20.glUseProgram(0);
	}

	public void cleanUp() {
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}

	private int loadShader(Identifier location, int type) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(location.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("//\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not load shader " + location);
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader!");
			System.exit(-1);
		}
		System.out.println("[" + this.getClass().getName() + "] " + GL20.glGetShaderInfoLog(shaderID));
		return shaderID;
	}
}