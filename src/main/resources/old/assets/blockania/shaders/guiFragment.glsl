#version 330

in vec2 pass_textureCoords;
in vec2 pass_blurTextureCoords;

out vec4 out_colour;

uniform sampler2D guiTexture;
uniform sampler2D blurTexture;
uniform float alpha;
uniform float useOverrideColour;
uniform vec3 overrideColour;
uniform float usesBlur;

void main(void){

    out_colour = texture(guiTexture, pass_textureCoords);
    float brightness = (out_colour.r + out_colour.g + out_colour.b)/3.0;
    out_colour.rgb = mix(out_colour.rgb, overrideColour * brightness, useOverrideColour);
    
    if(usesBlur > 0.5){
    	vec4 blurColour = texture(blurTexture, pass_blurTextureCoords);
    	out_colour.rgb = mix(blurColour.rgb, out_colour.rgb, alpha);
    }else{
    	out_colour.a *= alpha;
    }


}