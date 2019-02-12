package team.hdt.blockadia.engine.core_rewrite.game;

import team.hdt.blockadia.engine.core_rewrite.Blockadia;

import java.awt.*;

public class HUD {

	public static void renderHealthBar(Graphics g) {

		/** Render daytime shading **/
		g.setColor(new Color(0.0F, 0, 0.0F, 0F));
		g.fillRect(0, 0, Blockadia.WIDTH, Blockadia.HEIGHT);
		
		/** Render health bar **/
		g.setColor(Color.DARK_GRAY);
		g.fillRect(10, 10, 150, 15);
		g.setColor(Color.RED);
		//TODO: Fix this
		//Game.getPlayer().setHealth(MathUtils.clamp(Game.getPlayer().getHealth(), 0, Game.getPlayer().getMaxHealth()));
		//g.fillRect(10, 10, (int) (Game.getPlayer().getHealth() * 1.5), 15);
		g.setColor(Color.GRAY);
		g.drawRect(10, 10, 150, 15);
		
		/** Render mana bar **/
		g.setColor(Color.DARK_GRAY);
		g.fillRect(10, 30, 150, 15);
		g.setColor(Color.BLUE);
		//TODO: make this more dynamic
		g.fillRect(10, 30, 150, 15);
		g.setColor(Color.GRAY);
		g.drawRect(10, 30, 150, 15);
		
		/** Render stamina bar **/
		g.setColor(Color.DARK_GRAY);
		g.fillRect(10, 49, 150, 15);
		g.setColor(Color.GREEN);
		//TODO: make this more dynamic
		g.fillRect(10, 49, 150, 15);
		g.setColor(Color.GRAY);
		g.drawRect(10, 49, 150, 15);
	}
}
