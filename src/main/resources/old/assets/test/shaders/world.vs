#version 330

in vec3 attr_pos;
in vec2 attr_tex_offset;
in vec2 attr_tex_coord;
in vec3 attr_color;

out vec3 out_pos;
out vec2 out_tex_offset;
out vec2 out_tex_coord;
out vec3 out_color;
out vec2 out_texture_info;

uniform mat4 uniform_transform;
uniform mat4 uniform_camera_view;
uniform mat4 uniform_camera_projection;
uniform vec2 uniform_texture_info;

void main()
{
    out_pos = attr_pos;
    out_tex_offset = attr_tex_offset;
	out_tex_coord = attr_tex_coord;
	out_color = attr_color;

	out_texture_info = uniform_texture_info;

    gl_Position = uniform_camera_projection * uniform_camera_view * uniform_transform * vec4(attr_pos, 1);
}