#version 330

out vec4 out_Color;

in vec2 blurTextureCoords[5];

uniform sampler2D originalTexture;

void main(void) {
	out_Color = vec4(0.0);
	out_Color += texture(originalTexture, blurTextureCoords[0]) * 0.06136;
	out_Color += texture(originalTexture, blurTextureCoords[1]) * 0.24477;
	out_Color += texture(originalTexture, blurTextureCoords[2]) * 0.38774;
	out_Color += texture(originalTexture, blurTextureCoords[3]) * 0.24477;
    out_Color += texture(originalTexture, blurTextureCoords[4]) * 0.06136;
}
