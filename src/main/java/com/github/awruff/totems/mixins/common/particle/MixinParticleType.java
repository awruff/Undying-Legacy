package com.github.awruff.totems.mixins.common.particle;

import net.minecraft.entity.particle.ParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ParticleType.class)
enum MixinParticleType {
    UNDYING_LEGACY_TOTEM("totem", 47, false, 0);

    @Shadow
    MixinParticleType(String key, int id, boolean ignoreDistance, int parameterCount) {
    }
}
