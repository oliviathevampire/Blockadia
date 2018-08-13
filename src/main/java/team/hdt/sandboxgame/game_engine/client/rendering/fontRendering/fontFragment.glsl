#version 330

in vec2 pass_textureCoords;

out vec4 out_colour;

uniform sampler2D fontTexture;
uniform vec4 colour;
uniform vec3 borderColour;
uniform vec2 borderSizes;
uniform vec2 edgeData;

//I want to use the glsl smoothstep function, but for some unknown reason it doesn't work on my laptop, but only when exported as a jar. Works fine in Eclipse!
float smoothlyStep(float edge0, float edge1, float x){
    float t = clamp((x - edge0) / (edge1 - edge0), 0.0, 1.0);
    return t * t * (3.0 - 2.0 * t);
}

void main(void){

    float dist = texture(fontTexture, pass_textureCoords).a;
    float alpha = smoothlyStep((1.0 - edgeData.x) - edgeData.y, 1.0 - edgeData.x, dist);
    float outlineAlpha = smoothlyStep((1.0 - borderSizes.x) - borderSizes.y, 1.0 - borderSizes.x, dist);
    float overallAlpha = alpha + (1.0 - alpha) * outlineAlpha;
	vec3 overallColour = mix(borderColour, colour.rgb, alpha / (overallAlpha+0.000001));

    out_colour = vec4(overallColour, overallAlpha);
    out_colour.a *= colour.a;

	
}