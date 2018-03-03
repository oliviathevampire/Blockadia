package net.thegaminghuskymc.sandboxgame.client.renderer.block.model;

import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.util.EnumFacing;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class BuiltInModel implements IBakedModel {
    private final ItemCameraTransforms cameraTransforms;
    private final ItemOverrideList overrideList;

    public BuiltInModel(ItemCameraTransforms p_i46537_1_, ItemOverrideList p_i46537_2_) {
        this.cameraTransforms = p_i46537_1_;
        this.overrideList = p_i46537_2_;
    }

    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return Collections.<BakedQuad>emptyList();
    }

    public boolean isAmbientOcclusion() {
        return false;
    }

    public boolean isGui3d() {
        return true;
    }

    public boolean isBuiltInRenderer() {
        return true;
    }

    public TextureAtlasSprite getParticleTexture() {
        return null;
    }

    public ItemCameraTransforms getItemCameraTransforms() {
        return this.cameraTransforms;
    }

    public ItemOverrideList getOverrides() {
        return this.overrideList;
    }
}