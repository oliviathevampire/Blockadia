package team.hdt.blockadia.engine.core.util;

import team.hdt.blockadia.engine.core.util.math.vectors.Matrix4fs;
import team.hdt.blockadia.engine.core.util.math.vectors.Vectors2f;
import team.hdt.blockadia.engine.core.util.math.vectors.Vectors3f;
import team.hdt.blockadia.engine.core.util.math.vectors.Vectors4f;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shader {
    public int program;
    public int vertex_shader;
    public int fragment_shader;

    public Map<String, Integer> attributes;

    public Shader(String shader_name) {
        this.program = glCreateProgram();
        this.attributes = new HashMap<>();

        attach_vertex_shader(shader_name + ".vs");
        attach_fragment_shader(shader_name + ".fs");

        compile();
    }

    public Shader compile() {
        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Unable to link shader program:");
            System.err.println(glGetProgramInfoLog(program, glGetShaderi(fragment_shader, GL_INFO_LOG_LENGTH)));
            destroy();
        }

        return this;
    }

    public void bind() {
        glUseProgram(program);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void destroy() {
        unbind();

        glDetachShader(program, vertex_shader);
        glDetachShader(program, fragment_shader);

        glDeleteShader(vertex_shader);
        glDeleteShader(fragment_shader);

        glDeleteProgram(program);
    }

    public void set_uniform(String name, Matrix4fs value) {
        glUniformMatrix4fv(glGetUniformLocation(program, name), false, ExtendedBufferUtil.create_flipped_buffer(value));
    }

    public void set_uniform(String name, Vectors2f value) {
        glUniform2f(glGetUniformLocation(program, name), value.x, value.y);
    }

    public void set_uniform(String name, Vectors3f value) {
        glUniform3f(glGetUniformLocation(program, name), value.x, value.y, value.z);
    }

    public void set_uniform(String name, Vectors4f value) {
        glUniform4f(glGetUniformLocation(program, name), value.x, value.y, value.z, value.w);
    }

    public void set_uniform(String name, float value) {
        glUniform1f(glGetUniformLocation(program, name), value);
    }

    public void set_uniform(String name, int value) {
        glUniform1i(glGetUniformLocation(program, name), value);
    }

    public Shader save_attr(String attr_name) {
        attributes.put(attr_name, glGetAttribLocation(program, attr_name));
        return this;
    }

    public Shader save_attrs(String... attrs_names) {
        for (String attr_name : attrs_names) {
            save_attr(attr_name);
        }
        return this;
    }

    public int attr(String attr_name) {
        if (!attributes.containsKey(attr_name)) {
            try {
                throw new Exception("This attribute not saved in map");
            } catch (Exception e) {
                destroy();
            }
        }
        return attributes.get(attr_name);
    }

    public boolean has_attr(String attr_name) {
        return attributes.containsKey(attr_name);
    }

    private void attach_vertex_shader(String shader_name) {
        vertex_shader = glCreateShader(GL_VERTEX_SHADER);

        glShaderSource(vertex_shader, ResourceUtil.load_shader(shader_name));
        glCompileShader(vertex_shader);

        if (glGetShaderi(vertex_shader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Unable to create vertex shader:");
            System.err.println(glGetShaderInfoLog(vertex_shader, glGetShaderi(vertex_shader, GL_INFO_LOG_LENGTH)));
            destroy();
        }

        glAttachShader(program, vertex_shader);
    }

    private void attach_fragment_shader(String shader_name) {
        fragment_shader = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(fragment_shader, ResourceUtil.load_shader(shader_name));
        glCompileShader(fragment_shader);

        if (glGetShaderi(fragment_shader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Unable to create fragment shader:");
            System.err.println(glGetShaderInfoLog(fragment_shader, glGetShaderi(fragment_shader, GL_INFO_LOG_LENGTH)));
            destroy();
        }

        glAttachShader(program, fragment_shader);
    }
}