package net.timmy.timmylib.client;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;

public class RenderUtil {

    public static final ResourceLocation BLOCK_TEX;

    static {
        BLOCK_TEX = TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

    public RenderUtil() {
    }

    public static TextureManager engine() {
        return Minecraft.getMinecraft().renderEngine;
    }

    public static void bindBlockTexture() {
        engine().bindTexture(BLOCK_TEX);
    }

    public static TextureAtlasSprite getStillTexture(FluidStack fluid) {
        if (fluid == null || fluid.getFluid() == null)
            return null;
        else
            return getStillTexture(fluid.getFluid());
    }

    public static TextureAtlasSprite getStillTexture(Fluid fluid) {
        ResourceLocation iconKey = fluid.getStill();
        if (iconKey == null)
            return null;
        else
            return Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(iconKey.toString());
    }

    public static void renderGuiTank(FluidTank tank, double x, double y, double zLevel, double width, double height) {
        renderGuiTank(tank.getFluid(), tank.getCapacity(), tank.getFluidAmount(), x, y, zLevel, width, height);
    }

    public static void renderGuiTank(FluidStack fluid, int capacity, int amount, double x, double y, double zLevel, double width, double height) {
        if (fluid == null || fluid.getFluid() == null || fluid.amount <= 0)
            return;
        TextureAtlasSprite icon = getStillTexture(fluid);
        if (icon == null)
            return;
        int renderAmount = (int) Math.max(Math.min(height, ((double) amount * height) / (double) capacity), 1.0D);
        int posY = (int) ((y + height) - (double) renderAmount);
        bindBlockTexture();
        int color = fluid.getFluid().getColor(fluid);
        GL11.glColor3ub((byte) (color >> 16 & 0xff), (byte) (color >> 8 & 0xff), (byte) (color & 0xff));
        GlStateManager.enableBlend();
        for (int i = 0; (double) i < width; i += 16) {
            for (int j = 0; j < renderAmount; j += 16) {
                int drawWidth = (int) Math.min(width - (double) i, 16D);
                int drawHeight = Math.min(renderAmount - j, 16);
                int drawX = (int) (x + (double) i);
                int drawY = posY + j;
                double minU = icon.getMinU();
                double maxU = icon.getMaxU();
                double minV = icon.getMinV();
                double maxV = icon.getMaxV();
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder tes = tessellator.getBuffer();
                tes.begin(7, DefaultVertexFormats.POSITION_TEX);
                tes.pos(drawX, drawY + drawHeight, 0.0D).tex(minU, minV + ((maxV - minV) * (double) drawHeight) / 16D).endVertex();
                tes.pos(drawX + drawWidth, drawY + drawHeight, 0.0D).tex(minU + ((maxU - minU) * (double) drawWidth) / 16D, minV + ((maxV - minV) * (double) drawHeight) / 16D).endVertex();
                tes.pos(drawX + drawWidth, drawY, 0.0D).tex(minU + ((maxU - minU) * (double) drawWidth) / 16D, minV).endVertex();
                tes.pos(drawX, drawY, 0.0D).tex(minU, minV).endVertex();
                tessellator.draw();
            }

        }

        GlStateManager.disableBlend();
    }

    public static void renderBlockInWorld(Block block, int meta) {
        renderItemInWorld(new ItemStack(block, 1, meta));
    }

    @SuppressWarnings("unused")
    public static void renderItemInWorld(ItemStack stack) {
        Minecraft mc = Minecraft.getMinecraft();
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        if (stack.getItem() instanceof ItemBlock)
            GlStateManager.scale(0.59999999999999998D, 0.59999999999999998D, 0.59999999999999998D);
        else
            GlStateManager.scale(0.5D, 0.5D, 0.5D);
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.FIXED);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    public static void drawBar(BufferBuilder renderer, int x, int y, int width, int height, int red, int green, int blue,
                               int alpha) {
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos(x, y, 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos(x, y + height, 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos(x + width, y + height, 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos(x + width, y, 0.0D).color(red, green, blue, alpha).endVertex();
        Tessellator.getInstance().draw();
    }
}
