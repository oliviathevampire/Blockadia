package team.hdt.blockadia.engine.core_rewrite.gfx.renderer;

import org.joml.Vector2f;
import team.hdt.blockadia.engine.core_rewrite.Blockadia;
import team.hdt.blockadia.engine.core_rewrite.game.item.Item;
import team.hdt.blockadia.engine.core_rewrite.game.item.ItemStack;
import team.hdt.blockadia.engine.core_rewrite.gfx.gui.Gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ItemStackRenderer {

	public static void renderItemInGUI(Gui gui, ItemStack stack, float xPos, float yPos) {
		renderItemInGUI(gui, stack, xPos, yPos, 1);
	}

	public static void renderItemInGUI(Gui gui, ItemStack stack, float xPos, float yPos, float scale) {
		Item item = stack.getItem();
		if (!stack.isEmpty()) {
			for (int i = 0; i < 2; i++) {
				Vector2f textureCoords = Objects.requireNonNull(item).getTextureCoords(i);
				if (textureCoords != null) {
					gui.drawTexturedRect(item.getTexture(), xPos, yPos, 16, 16, textureCoords.x * 16, textureCoords.y * 16, item.getTextureWidth() * 16);
				}
			}
		}
	}

	public static void renderTooltip(Gui gui, float xPos, float yPos, ItemStack stack) {
		if (!stack.isEmpty()) {
			List<String> tooltip = new ArrayList<String>();
			Item item = stack.getItem();
			tooltip.add(Objects.requireNonNull(item).getLocalizedName());
			item.addTooltipInformation(tooltip);
			 tooltip.add("Count: " + stack.getCount());
			 tooltip.add("Name: item." + item.getRegistryName());
			renderTooltip(gui, xPos, yPos, tooltip);
		}
	}

	public static void renderTooltip(Gui gui, float xPos, float yPos, String tooltip) {
		renderTooltip(gui, xPos, yPos, Collections.singletonList(tooltip));
	}

	public static void renderTooltip(Gui gui, float xPos, float yPos, List<String> tooltip) {
		int backgroundColor = 0xB4000000;
		float scale = 2f / 3f;
		float linePadding = 1;
		float textWidth = calculateTooltipWidth(tooltip, 1) * scale;
		float textHeight = tooltip.size() * 16 * scale;
		float renderX = xPos + 6;
		float renderY = yPos - textHeight / 2 + 3;
		gui.drawRect(renderX - 1, renderY - 1, textWidth + 2, textHeight + 2 + (tooltip.size() - 1) * linePadding, backgroundColor);
		for (int i = 0; i < tooltip.size(); i++) {
			String text = tooltip.get(i);
			Blockadia.getInstance().getRenderer().setAmbientLight(1.0F, 0F, 0F);
			// renderer.renderFont(text, font, renderX, renderY + i * font.getCharSize() * scale + i * linePadding, i > 0 ? 0xff666666 : 0xffffffff, scale);
		}
	}

	public static float calculateTooltipWidth(List<String> tooltip, float scale) {
		// double width = 0;
		// for (String s : tooltip) {
		// double widthTemp = Renderer.getStringWidth(font, s, scale);
		// if (widthTemp > width) {
		// width = widthTemp;
		// }
		// }
		// return width;
		return 32;
	}
}