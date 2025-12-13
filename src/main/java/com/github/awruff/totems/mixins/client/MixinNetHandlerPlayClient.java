package com.github.awruff.totems.mixins.client;

import com.github.awruff.totems.item.ModItems;
import com.github.awruff.totems.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.handler.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.particle.ParticleType;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinNetHandlerPlayClient {
    @Shadow
    private Minecraft minecraft;

    @Shadow
    private ClientWorld world;

    @Redirect(
            method = "handleEntityEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;doEvent(B)V")
    )
    private void handleTotemEvent(Entity entity, byte event) {
        if (event == 35) { // 35 is the event in modern, so we just use that here :shrug:

            // TODO: Add Totem Particles (will require ASM, spooky)
            minecraft.particleManager.addEmitter(entity, ParticleType.CRIT_MAGIC);
            minecraft.particleManager.addEmitter(entity, ParticleType.CRIT_MAGIC);
            minecraft.particleManager.addEmitter(entity, ParticleType.CRIT_MAGIC);

            world.playSound(entity.x, entity.y, entity.z, "undyinglegacy:item.totem.use", 1.0f, 1.0f, false);

            if (entity == minecraft.player) {
                RenderUtils.activateItem(new ItemStack(ModItems.TOTEM));
            }
        } else {
            entity.doEvent(event);
        }
    }
}
