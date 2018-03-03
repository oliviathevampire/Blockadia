package net.timmy.timmylib.client;

import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.thegaminghuskymc.huskylib2.lib.utils.RenderHelper;
import net.thegaminghuskymc.huskylib2.lib.utils.StringHelper;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("deprecation")
public abstract class GuiBase extends GuiContainer {

    public static final SoundHandler guiSoundManager = FMLClientHandler.instance().getClient().getSoundHandler();
    protected boolean drawTitle;
    protected boolean drawInventory;
    protected int mouseX;
    protected int mouseY;
    protected int lastIndex;
    protected String name;
    protected ResourceLocation texture;
    protected List<?> tooltip;
    protected boolean tooltips;

    public GuiBase(Container container) {
        super(container);
        drawTitle = true;
        drawInventory = true;
        mouseX = 0;
        mouseY = 0;
        tooltip = new LinkedList<>();
        tooltips = true;
    }

    public GuiBase(Container container, ResourceLocation texture) {
        super(container);
        drawTitle = true;
        drawInventory = true;
        mouseX = 0;
        mouseY = 0;
        tooltip = new LinkedList<>();
        tooltips = true;
        this.texture = texture;
    }

    public void initGui() {
        super.initGui();
    }

    public void drawScreen(int x, int y, float partialTick) {
        super.drawScreen(x, y, partialTick);
        if (tooltips && mc.player.inventory.getItemStack() == null) {
            drawTooltip(tooltip);
        }
        mouseX = x - guiLeft;
        mouseY = y - guiTop;
    }

    protected void drawGuiContainerForegroundLayer(int x, int y) {
        if (drawTitle & (name != null))
            fontRenderer.drawString(StringHelper.localize(name), getCenteredOffset(StringHelper.localize(name)), 6, 0x404040);
        if (drawInventory)
            fontRenderer.drawString(I18n.translateToLocal("container.inventory"), 8, (ySize - 96) + 3, 0x404040);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTick, int x, int y) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        mouseX = x - guiLeft;
        mouseY = y - guiTop;
        GlStateManager.pushMatrix();
        GlStateManager.translate(guiLeft, guiTop, 0.0F);
        GlStateManager.popMatrix();
    }

    protected boolean onMouseWheel(int mouseX, int mouseY, int wheelMovement) {
        return false;
    }

    protected void mouseClickMove(int mX, int mY, int lastClick, long timeSinceClick) {
        Slot slot = getSlotAtPosition(mX, mY);
        ItemStack itemstack = mc.player.inventory.getItemStack();
        if (dragSplitting && slot != null && itemstack != null) {
            if (lastIndex != slot.slotNumber) {
                lastIndex = slot.slotNumber;
                handleMouseClick(slot, slot.slotNumber, 0, ClickType.PICKUP);
            }
        } else {
            lastIndex = -1;
            super.mouseClickMove(mX, mY, lastClick, timeSinceClick);
        }
    }

    public Slot getSlotAtPosition(int xCoord, int yCoord) {
        for (int k = 0; k < inventorySlots.inventorySlots.size(); k++) {
            Slot slot = inventorySlots.inventorySlots.get(k);
            if (isMouseOverSlot(slot, xCoord, yCoord))
                return slot;
        }

        return null;
    }

    public boolean isMouseOverSlot(Slot theSlot, int xCoord, int yCoord) {
        return isPointInRegion(theSlot.xPos, theSlot.yPos, 16, 16, xCoord, yCoord);
    }

    public void bindTexture(ResourceLocation texture) {
        mc.renderEngine.bindTexture(texture);
    }

    public void drawButton(TextureAtlasSprite icon, int x, int y, int mode) {
        drawIcon(icon, x, y);
    }

    public void drawItemStack(ItemStack stack, int x, int y, boolean drawOverlay, String overlayTxt) {
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, 32F);
        zLevel = 200F;
        itemRender.zLevel = 200F;
        FontRenderer font = null;
        if (stack != null)
            font = stack.getItem().getFontRenderer(stack);
        if (font == null)
            font = fontRenderer;
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
//        if(drawOverlay)
//            itemRender.renderItemOverlayIntoGUI(font, stack, x, y - (draggedStack != null ? 8 : 0), overlayTxt);
        zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
        GlStateManager.popMatrix();
        GlStateManager.disableLighting();
    }

    public void drawFluid(int x, int y, FluidStack fluid, int width, int height) {
        if (fluid == null) {
            return;
        } else {
            RenderHelper.setBlockTextureSheet();
            int colour = fluid.getFluid().getColor(fluid);
            GlStateManager.color(colour >> 16 & 0xff, colour >> 8 & 0xff, colour & 0xff, colour >> 24 & 0xff);
            drawTiledTexture(x, y, RenderHelper.getTexture(fluid.getFluid().getStill(fluid)), width, height);
            return;
        }
    }

    public void drawTiledTexture(int x, int y, TextureAtlasSprite icon, int width, int height) {
        for (int i = 0; i < width; i += 16) {
            for (int j = 0; j < height; j += 16) {
                int drawWidth = Math.min(width - i, 16);
                int drawHeight = Math.min(height - j, 16);
                drawScaledTexturedModelRectFromIcon(x + i, y + j, icon, drawWidth, drawHeight);
            }

        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void drawIcon(TextureAtlasSprite icon, int x, int y) {
        RenderHelper.setBlockTextureSheet();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexturedModalRect(x, y, icon, 16, 16);
    }

    public void drawColorIcon(TextureAtlasSprite icon, int x, int y) {
        drawTexturedModalRect(x, y, icon, 16, 16);
    }

    public void drawSizedModalRect(int x1, int y1, int x2, int y2, int color) {
        if (x1 < x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y1 < y2) {
            int temp = y1;
            y1 = y2;
            y2 = temp;
        }
        float a = (float) (color >> 24 & 0xff) / 255F;
        float r = (float) (color >> 16 & 0xff) / 255F;
        float g = (float) (color >> 8 & 0xff) / 255F;
        float b = (float) (color & 0xff) / 255F;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(r, g, b, a);
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION);
        buffer.pos(x1, y2, zLevel).endVertex();
        buffer.pos(x2, y2, zLevel).endVertex();
        buffer.pos(x2, y1, zLevel).endVertex();
        buffer.pos(x1, y1, zLevel).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public void drawSizedRect(int x1, int y1, int x2, int y2, int color) {
        if (x1 < x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y1 < y2) {
            int temp = y1;
            y1 = y2;
            y2 = temp;
        }
        float a = (float) (color >> 24 & 0xff) / 255F;
        float r = (float) (color >> 16 & 0xff) / 255F;
        float g = (float) (color >> 8 & 0xff) / 255F;
        float b = (float) (color & 0xff) / 255F;
        GlStateManager.disableTexture2D();
        GlStateManager.color(r, g, b, a);
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION);
        buffer.pos(x1, y2, zLevel).endVertex();
        buffer.pos(x2, y2, zLevel).endVertex();
        buffer.pos(x2, y1, zLevel).endVertex();
        buffer.pos(x1, y1, zLevel).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.enableTexture2D();
    }

    public void drawSizedTexturedModalRect(int x, int y, int u, int v, int width, int height, float texW,
                                           float texH) {
        float texU = 1.0F / texW;
        float texV = 1.0F / texH;
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x + 0, y + height, zLevel).tex((float) (u + 0) * texU, (float) (v + height) * texV).endVertex();
        buffer.pos(x + width, y + height, zLevel).tex((float) (u + width) * texU, (float) (v + height) * texV).endVertex();
        buffer.pos(x + width, y + 0, zLevel).tex((float) (u + width) * texU, (float) (v + 0) * texV).endVertex();
        buffer.pos(x + 0, y + 0, zLevel).tex((float) (u + 0) * texU, (float) (v + 0) * texV).endVertex();
        Tessellator.getInstance().draw();
    }

    public void drawScaledTexturedModelRectFromIcon(int x, int y, TextureAtlasSprite icon, int width, int height) {
        if (icon == null) {
            return;
        } else {
            double minU = icon.getMinU();
            double maxU = icon.getMaxU();
            double minV = icon.getMinV();
            double maxV = icon.getMaxV();
            BufferBuilder buffer = Tessellator.getInstance().getBuffer();
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(x + 0, y + height, zLevel).tex(minU, minV + ((maxV - minV) * (double) height) / 16D).endVertex();
            buffer.pos(x + width, y + height, zLevel).tex(minU + ((maxU - minU) * (double) width) / 16D, minV + ((maxV - minV) * (double) height) / 16D).endVertex();
            buffer.pos(x + width, y + 0, zLevel).tex(minU + ((maxU - minU) * (double) width) / 16D, minV).endVertex();
            buffer.pos(x + 0, y + 0, zLevel).tex(minU, minV).endVertex();
            Tessellator.getInstance().draw();
            return;
        }
    }

    public void drawTooltip(List<?> list) {
        drawTooltipHoveringText(list, mouseX + guiLeft, mouseY + guiTop, fontRenderer);
        tooltip.clear();
    }

    protected void drawTooltipHoveringText(List<?> list, int x, int y, FontRenderer font) {
        if (list == null || list.isEmpty())
            return;
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        int k = 0;
        Iterator<?> iterator = list.iterator();
        do {
            if (!iterator.hasNext())
                break;
            String s = (String) iterator.next();
            int l = font.getStringWidth(s);
            if (l > k)
                k = l;
        } while (true);
        int i1 = x + 12;
        int j1 = y - 12;
        int k1 = 8;
        if (list.size() > 1)
            k1 += 2 + (list.size() - 1) * 10;
        if (i1 + k > width)
            i1 -= 28 + k;
        if (j1 + k1 + 6 > height)
            j1 = height - k1 - 6;
        zLevel = 300F;
        itemRender.zLevel = 300F;
        int l1 = 0xf0100010;
        drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
        drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
        drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
        drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
        drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
        int i2 = 0x505000ff;
        int j2 = (i2 & 0xfefefe) >> 1 | i2 & 0xff000000;
        drawGradientRect(i1 - 3, (j1 - 3) + 1, (i1 - 3) + 1, (j1 + k1 + 3) - 1, i2, j2);
        drawGradientRect(i1 + k + 2, (j1 - 3) + 1, i1 + k + 3, (j1 + k1 + 3) - 1, i2, j2);
        drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, (j1 - 3) + 1, i2, i2);
        drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
        for (int k2 = 0; k2 < list.size(); k2++) {
            String s1 = (String) list.get(k2);
            font.drawStringWithShadow(s1, i1, j1, -1);
            if (k2 == 0)
                j1 += 2;
            j1 += 10;
        }

        zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableRescaleNormal();
    }

    public void mouseClicked(int mouseButton)
            throws IOException {
        super.mouseClicked(guiLeft + mouseX, guiTop + mouseY, mouseButton);
    }

    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    protected int getCenteredOffset(String string) {
        return getCenteredOffset(string, xSize);
    }

    protected int getCenteredOffset(String string, int xWidth) {
        return (xWidth - fontRenderer.getStringWidth(string)) / 2;
    }

    public int getGuiLeft() {
        return guiLeft;
    }

    public int getGuiTop() {
        return guiTop;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void overlayRecipe() {
    }

}
