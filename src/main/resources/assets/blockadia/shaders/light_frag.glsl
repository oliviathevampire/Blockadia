#version 330

in vec2 pass_TextureCoords;

out vec4 out_Color;

uniform vec4 lightColor;
uniform float lightSize;

void main() {
	float distance = length(pass_TextureCoords);
	float factor = pow(0.01, distance) - 0.01;
	out_Color = vec4(lightColor.rgb, lightColor.a * factor);
}
