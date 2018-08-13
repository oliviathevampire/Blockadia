#version 330

layout(location = 0) in vec2 position;
layout(location = 1) in mat4 modelViewMatrix;
layout(location = 5) in vec4 colour;//4 bytes
layout(location = 6) in float alpha;

out vec2 textureCoords;
out vec4 pass_colour;

uniform mat4 projectionMatrix;
uniform vec3 lighting;

void main(void){

	textureCoords = position + vec2(0.5, 0.5);
	textureCoords.y = 1.0 - textureCoords.y;
	pass_colour = vec4(colour.rgb * lighting, alpha);

	gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 0.0, 1.0);

}