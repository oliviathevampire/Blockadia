#version 140

out vec4 out_colour;

in vec2 textureCoords;
in vec4 pass_colour;

uniform sampler2D particleTexture;

void main(void){

	out_colour = texture(particleTexture, textureCoords) * pass_colour;

}