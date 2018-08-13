#version 330

in float height;
in vec2 pass_texCoords;

out vec4 out_colour;

const float lowerLimit = -20.0;
const float upperLimit = 44.0;

uniform vec3 horizonColour;
uniform vec3 skyColour;
uniform sampler2D nightSky;
uniform float starBrightness;

void main(void){

    float factor = (height - lowerLimit) / (upperLimit - lowerLimit);
    factor = clamp(factor, 0.0, 1.0);
    vec3 finalColour = mix(horizonColour, skyColour, factor);
	out_colour = vec4(finalColour, 1.0);
	
	if(starBrightness > 0.001){
		vec4 starColour = texture(nightSky, pass_texCoords);
		out_colour.rgb = mix(out_colour.rgb, starColour.rgb, starColour.a * starBrightness * factor);
	}
	
}