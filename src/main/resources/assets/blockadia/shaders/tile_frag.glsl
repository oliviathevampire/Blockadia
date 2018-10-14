#version 330

in vec2 pass_TextureCoords;
in vec2 pass_TextureCoords1;

layout (location = 0) out vec4 out_Color;
layout (location = 1) out vec4 out_BrightColor;

uniform sampler2D tileTexture;
uniform sampler2D tileGlowTexture;

void main() {
	vec4 color = texture(tileTexture, pass_TextureCoords);
	vec4 glowColor = texture(tileGlowTexture, pass_TextureCoords);

	if(color.a < 0.5) {
		discard;
	}

	vec4 overlayColor = texture(tileTexture, pass_TextureCoords1);
	vec4 overlayGlowColor = texture(tileGlowTexture, pass_TextureCoords1);
	out_Color = mix(color, overlayColor,	overlayColor.a);
	out_BrightColor = mix(glowColor, overlayGlowColor, overlayGlowColor.a);
}
