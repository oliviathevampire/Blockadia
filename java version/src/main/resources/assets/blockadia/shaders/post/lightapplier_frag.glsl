#version 330

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D colorTexture;
uniform sampler2D lightTexture;

void main(void) {
	vec4 worldColor = texture(colorTexture, textureCoords);
	vec4 lightColor = texture(lightTexture, textureCoords);
	out_Color = worldColor * lightColor;
}
