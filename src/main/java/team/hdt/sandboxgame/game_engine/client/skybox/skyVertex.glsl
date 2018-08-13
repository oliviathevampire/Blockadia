#version 330

layout(location = 0) in vec3 in_position;

out float height;
out vec2 pass_texCoords;

uniform mat4 pvMatrix;
uniform float skyboxSize;
uniform float scroll;
uniform float time;
uniform float segCount;

const float tiling = 3.3;
const float scrollFactor = 0.461;

void main(void){

	vec3 worldPosition = in_position * skyboxSize;
	worldPosition.z -= skyboxSize * 0.95;
	gl_Position = pvMatrix * vec4(worldPosition, 1.0);
	height = worldPosition.y;
	float texX = floor(gl_VertexID/2.0) / segCount*0.5;
	pass_texCoords = vec2(texX, in_position.y * 0.5 + 0.5) * tiling;
	pass_texCoords.x -= scroll * tiling * scrollFactor;
	
	pass_texCoords -= time;
}