#version 140

in vec2 position;
in mat4 modelViewMatrix;
in vec2 textureOffset;
in vec2 textureOffset1;

out vec2 pass_TextureCoords;
out vec2 pass_TextureCoords1;

uniform mat4 projectionMatrix;
uniform float numberOfRows;

void main() {
	gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 0.0, 1.0);

	vec2 textureCoords = position;
	textureCoords /= numberOfRows;
	pass_TextureCoords = textureCoords + textureOffset;
	pass_TextureCoords1 = textureCoords + textureOffset1;
}
