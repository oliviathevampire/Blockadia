package team.hdt.blockadia.game_engine.client.rendering.fontRendering;

public class TextBuilder {
	
	private String text;
	private boolean centered = false;
	private boolean rightAligned = false;
	private boolean justify = false;
	private boolean indent = false;
	private float textSize = 1;
	private FontType font = FontType.GILL;
	
	protected TextBuilder(String text){
		this.text = text;
	}
	
	public TextBuilder center(){
		this.centered = true;
		this.rightAligned = false;
		return this;
	}
	
	public TextBuilder rightAlign(){
		this.centered = false;
		this.rightAligned = true;
		return this;
	}
	
	public TextBuilder justify(){
		this.justify = true;
		return this;
	}
	
	public TextBuilder indent(){
		this.indent = true;
		return this;
	}
	
	public TextBuilder setFont(FontType font){
		this.font = font;
		return this;
	}
	
	public TextBuilder setFontSize(float size){
		this.textSize = size;
		return this;
	}
	
	public Text create(){
		return new Text(text, font, textSize, centered, rightAligned, justify, indent);
	}

}
