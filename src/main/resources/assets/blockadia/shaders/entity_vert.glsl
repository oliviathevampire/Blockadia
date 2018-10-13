#version 140

in vec2 position;

out vec2 pass_TextureCoords;

uniform mat4 projectionMatrix;
uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;
uniform float numberOfRows;
uniform vec2 textureOffset;

void main() {
	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 0.0, 1.0);

	vec2 textureCoords = position;
	textureCoords /= numberOfRows;
	pass_TextureCoords = textureCoords + textureOffset;
}
