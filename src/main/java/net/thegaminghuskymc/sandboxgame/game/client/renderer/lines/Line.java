package net.thegaminghuskymc.sandboxgame.game.client.renderer.lines;

import net.thegaminghuskymc.sandboxgame.engine.util.Color;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;

// line object
public class Line {

	public Vector3f posa, posb;
	public Color colora, colorb;

	public Line(Vector3f posa, Color colora, Vector3f posb, Color colorb) {
		this.set(posa, colora, posb, colorb);
	}

	public void set(Vector3f posa, Color colora, Vector3f posb, Color colorb) {
		this.posa = posa;
		this.posb = posb;
		this.colora = colora;
		this.colorb = colorb;

	}
}