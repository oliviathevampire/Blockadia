#version 330

in vec2 pass_TextureCoords;

out vec4 out_Color;

uniform vec4 color;

void main() {
	out_Color = color;
}
