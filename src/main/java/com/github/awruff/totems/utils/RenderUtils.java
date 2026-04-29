package com.github.awruff.totems.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.model.block.ModelTransformations;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.client.render.platform.Lighting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class RenderUtils {
    public static final Random RANDOM = new Random();

    public static ItemStack activatedItem;
    public static int activationTicks;
    public static float offsetX, offsetY;

    public static void renderItem(Minecraft mc, int width, int height, float partialTicks) {
        if (activatedItem == null || activationTicks <= 0) {
            return;
        }

        int remainingTicks = 40 - activationTicks;
        float progress = (remainingTicks + partialTicks) / 40.0F;

        float progressSq = progress * progress;
        float progressCube = progress * progressSq;
        float animationCurve = 10.25F * progressCube * progressSq
                - 24.95F * progressSq * progressSq
                + 25.5F * progressCube
                - 13.8F * progressSq
                + 4.0F * progress;

        float angle = animationCurve * (float) Math.PI;

        float offsetXScaled = offsetX * (width / 4f);
        float offsetYScaled = offsetY * (height / 4f);

        GlStateManager.enableAlphaTest();
        GlStateManager.pushMatrix();
        GlStateManager.pushLightingAttributes();
        GlStateManager.enableDepthTest();
        GlStateManager.disableCull();

        Lighting.turnOn();

        GlStateManager.translatef(
                (width / 2f) + offsetXScaled * MathHelper.abs(MathHelper.sin(angle * 2.0F)),
                (height / 2f) + offsetYScaled * MathHelper.abs(MathHelper.sin(angle * 2.0F)),
                -50.0F
        );

        float scale = 50.0F + 175.0F * MathHelper.sin(angle);
        GlStateManager.scalef(-scale, -scale, scale);

        GlStateManager.rotatef(900.0F * MathHelper.abs(MathHelper.sin(angle)), 0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(6.0F * MathHelper.cos(progress * 8.0F), 1.0F, 0.0F, 0.0F);
        GlStateManager.rotatef(6.0F * MathHelper.cos(progress * 8.0F), 0.0F, 0.0F, 1.0F);

        mc.getItemRenderer().renderItemInHand(activatedItem, ModelTransformations.Type.FIXED);

        GlStateManager.popAttributes();
        GlStateManager.popMatrix();
        Lighting.turnOff();
        GlStateManager.enableCull();
        GlStateManager.disableDepthTest();
    }


    public static void activateItem(ItemStack item) {
        activatedItem = item;
        activationTicks = 40;
        offsetX = RANDOM.nextFloat() * 2.0F - 1.0F;
        offsetY = RANDOM.nextFloat() * 2.0F - 1.0F;
    }
}
