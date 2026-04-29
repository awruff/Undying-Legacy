package com.github.awruff.totems.mixins.common.particle;

import com.github.awruff.totems.utils.ParticleTotem;
import net.minecraft.client.ParticleManager;
import net.minecraft.client.entity.particle.ParticleFactory;
import net.minecraft.entity.particle.ParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public abstract class MixinParticleManager {
    @Shadow
    public abstract void register(int type, ParticleFactory factory);

    @Inject(
            method = "registerFactories",
            at = @At("RETURN")
    )
    private void registerTotemParticle(CallbackInfo ci) {
        register(ParticleType.UNDYING_LEGACY_TOTEM.getId(), new ParticleTotem.Factory());
    }
}
