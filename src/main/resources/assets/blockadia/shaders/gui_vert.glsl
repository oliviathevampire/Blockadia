#version 140

in vec2 position;

out vec2 pass_TextureCoords;

uniform mat4 projectionMatrix;
uniform mat4 transformationMatrix;

uniform vec4 textureOffsets;
uniform float textureSize;

void main() {
	gl_Position = projectionMatrix * transformationMatrix * vec4(position, 0.0, 1.0);

	pass_TextureCoords = vec2(position.x / textureSize * textureOffsets.z, position.y / textureSize * textureOffsets.w) + textureOffsets.xy / textureSize;
}
