#version 330

in vec2 pass_TextureCoords;

out vec4 out_Color;

uniform int textureType;
uniform vec4 color;
uniform sampler2D guiTexture;

void main() {
	if (textureType == 0) {
		out_Color = texture(guiTexture, pass_TextureCoords);
	} else if (textureType == 1) {
		out_Color = color;
	}
}
