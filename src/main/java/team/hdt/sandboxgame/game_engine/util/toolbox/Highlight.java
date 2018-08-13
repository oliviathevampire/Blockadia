package team.hdt.sandboxgame.game_engine.util.toolbox;

import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;

public class Highlight {

	private static final float SMALLEST = 0.1f;
	private static final float SCALE = 0.2f;

	private Vectors3f info;
	private Colour colour;
	private boolean show = false;

	public Highlight() {
		info = new Vectors3f();
		this.colour = new Colour(1, 1, 1);
	}

	public Vectors3f getInfo() {
		return info;
	}

	public boolean isShown() {
		return show/* && !MyKeyboard.getKeyboard().isKeyDown(Keyboard.KEY_H)*/;
	}

	public void hide() {
		this.show = false;
	}

	/*public void followEntity(Entity entity, Colour newColour) {
		this.show = true;
		this.targetTransform = entity.getTransform();
		info.z = SMALLEST + (calcuateHighlightWidth(entity.getBoundingBox()) * SCALE);
		this.colour.setColour(newColour);
	}*/

	public void setColour(Colour newColour) {
		this.colour.setColour(newColour);
	}

	public void update() {
		/*if (targetTransform != null) {
			Vectors3f position = targetTransform.getPosition();
			info.x = position.x;
			info.y = position.z;
		}*/
	}

	public Colour getColour() {
		return colour;
	}

	/*private float calcuateHighlightWidth(EntityBox box) {
		return (box.getSizes().x + box.getSizes().z) / 4f;
	}*/

}
