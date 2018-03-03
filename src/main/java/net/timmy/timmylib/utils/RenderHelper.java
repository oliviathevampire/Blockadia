package net.timmy.timmylib.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public final class RenderHelper {

    public static final double RENDER_OFFSET = 0.0009765625D;
    public static final ResourceLocation MC_BLOCK_SHEET = new ResourceLocation("textures/atlas/blocks.png");
    public static final ResourceLocation MC_FONT_DEFAULT = new ResourceLocation("textures/font/ascii.png");
    public static final ResourceLocation MC_FONT_ALTERNATE = new ResourceLocation("textures/font/ascii_sga.png");
    public static final ResourceLocation MC_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

    private RenderHelper() {
    }

    public static TextureManager engine() {
        return Minecraft.getMinecraft().renderEngine;
    }

    public static TextureMap textureMap() {
        return Minecraft.getMinecraft().getTextureMapBlocks();
    }

    public static Tessellator tessellator() {
        return Tessellator.getInstance();
    }

    public static RenderItem renderItem() {
        return Minecraft.getMinecraft().getRenderItem();
    }

    public static void setColor3ub(int color) {
        GlStateManager.color((byte) (color >> 16 & 0xff), (byte) (color >> 8 & 0xff), (byte) (color & 0xff));
    }

    public static void setColor4ub(int color) {
        GlStateManager.color((byte) (color >> 24 & 0xff), (byte) (color >> 16 & 0xff), (byte) (color >> 8 & 0xff), (byte) (color & 0xff));
    }

    public static void resetColor() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void renderIcon(TextureAtlasSprite icon, double z) {
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(0.0D, 16D, z).tex(icon.getMinU(), icon.getMaxV());
        buffer.pos(16D, 16D, z).tex(icon.getMaxU(), icon.getMaxV());
        buffer.pos(16D, 0.0D, z).tex(icon.getMaxU(), icon.getMinV());
        buffer.pos(0.0D, 0.0D, z).tex(icon.getMinU(), icon.getMinV());
        Tessellator.getInstance().draw();
    }

    public static void renderIcon(double x, double y, double z, TextureAtlasSprite icon, int width,
                                  int height) {
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(x, y + (double) height, z).tex(icon.getMinU(), icon.getMaxV());
        buffer.pos(x + (double) width, y + (double) height, z).tex(icon.getMaxU(), icon.getMaxV());
        buffer.pos(x + (double) width, y, z).tex(icon.getMaxU(), icon.getMinV());
        buffer.pos(x, y, z).tex(icon.getMinU(), icon.getMinV());
        Tessellator.getInstance().draw();
    }

    public static TextureAtlasSprite getFluidTexture(Fluid fluid) {
        if (fluid == null)
            fluid = FluidRegistry.LAVA;
        return getTexture(fluid.getStill());
    }

    public static TextureAtlasSprite getFluidTexture(FluidStack fluid) {
        if (fluid == null || fluid.getFluid().getStill(fluid) == null)
            fluid = new FluidStack(FluidRegistry.LAVA, 1);
        return getTexture(fluid.getFluid().getStill(fluid));
    }

    public static void bindTexture(ResourceLocation texture) {
        engine().bindTexture(texture);
    }

    public static void setBlockTextureSheet() {
        bindTexture(MC_BLOCK_SHEET);
    }

    public static void setDefaultFontTextureSheet() {
        bindTexture(MC_FONT_DEFAULT);
    }

    public static TextureAtlasSprite getTexture(String location) {
        return textureMap().getAtlasSprite(location);
    }

    public static TextureAtlasSprite getTexture(ResourceLocation location) {
        return getTexture(location.toString());
    }

    public static void setSGAFontTextureSheet() {
        bindTexture(MC_FONT_ALTERNATE);
    }

    public static void enableGUIStandardItemLighting() {
        net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
    }

    public static void enableStandardItemLighting() {
        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
    }

}
