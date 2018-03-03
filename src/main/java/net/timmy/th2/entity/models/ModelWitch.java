package net.timmy.th2.entity.models;

import net.minecraft.client.model.ModelIllager;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelWitch extends ModelIllager {

    ModelRenderer hat5;
    ModelRenderer eyebrow2;
    ModelRenderer nose;
    ModelRenderer eyebrow;
    ModelRenderer eye2;
    ModelRenderer eye;
    ModelRenderer hat1;
    ModelRenderer hat2;
    ModelRenderer hat3;
    ModelRenderer hat4;

    public ModelWitch(float par1) {

        super(par1, 0.0f, 64, 64);

        hat5 = new ModelRenderer(this, 12, 32);
        hat5.setRotationPoint(0.0F, 2.0F, 2.0F);
        hat5.addBox(-1.5F, 11.0F, -12.0F, 3, 2, 4, par1);

        eyebrow2 = new ModelRenderer(this, 32, 5);
        eyebrow2.setRotationPoint(0.0F, 2.0F, 2.0F);
        eyebrow2.addBox(2.0F, 5.0F, 3.6999998F, 3, 1, 1, par1);

        nose = new ModelRenderer(this, 32, 0);
        nose.setRotationPoint(0.0F, 2.0F, 2.0F);
        nose.addBox(-1.0F, 1.0F, 4.0F, 2, 3, 2);

        eyebrow = new ModelRenderer(this, 32, 5);
        eyebrow.setRotationPoint(0.0F, 2.0F, 2.0F);
        eyebrow.addBox(-5.0F, 5.0F, 3.6999998F, 3, 1, 1, par1);

        eye2 = new ModelRenderer(this, 40, 0);
        eye2.setRotationPoint(0.0F, 2.0F, 2.0F);
        eye2.addBox(-3.0F, 4.0F, 3.5F, 2, 2, 1, par1);

        eye = new ModelRenderer(this, 46, 0);
        eye.setRotationPoint(0.0F, 2.0F, 2.0F);
        eye.addBox(1.0F, 4.0F, 3.5F, 2, 2, 1, par1);

        hat1 = new ModelRenderer(this, 0, 32);
        hat1.setRotationPoint(0.0F, 2.0F, 2.0F);
        hat1.addBox(-7.0F, 7.0F, -7.0F, 14, 1, 14, par1);

        hat2 = new ModelRenderer(this, 32, 5);
        hat2.addBox(-4.0F, 8.0F, -5.0F, 8, 3, 8, par1);
        hat2.setRotationPoint(0.0F, 2.0F, 2.0F);

        hat3 = new ModelRenderer(this, 12, 32);
        hat3.setRotationPoint(0.0F, 2.0F, 2.0F);
        hat3.addBox(-3.0F, 11.0F, -4.0F, 6, 2, 6, par1);

        hat4 = new ModelRenderer(this, 12, 32);
        hat4.setRotationPoint(0.0F, 2.0F, 2.0F);
        hat4.addBox(-2.0F, 13.0F, -4.0F, 4, 2, 5, par1);

        head.addChild(hat1);
        head.addChild(hat2);
        head.addChild(hat3);
        head.addChild(hat4);
        head.addChild(hat5);
        head.addChild(eye);
        head.addChild(eye2);
        head.addChild(eyebrow);
        head.addChild(eyebrow2);
        head.addChild(nose);

    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
        super.render(par1Entity, par2, par3, par4, par5, par6, par7);
//Render every non-child part
        hat4.render(par7);
        eyebrow2.render(par7);
        nose.render(par7);
        eyebrow.render(par7);
        eye2.render(par7);
        eye.render(par7);
        hat1.render(par7);
        hat2.render(par7);
        hat3.render(par7);
        hat4.render(par7);

    }

}