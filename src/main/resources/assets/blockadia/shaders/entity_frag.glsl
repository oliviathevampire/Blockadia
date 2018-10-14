#version 330

in vec2 pass_TextureCoords;

layout (location = 0) out vec4 out_Color;
layout (location = 1) out vec4 out_BrightColor;

uniform sampler2D entityTexture;
// uniform sampler2D entityGlowTexture;

void main() {
	out_Color = texture(entityTexture, pass_TextureCoords);
	// out_BrightColor = texture(tileGlowTexture, pass_TextureCoords);
	out_BrightColor = vec4(0.0, 0.0, 0.0, 0.0);
}
