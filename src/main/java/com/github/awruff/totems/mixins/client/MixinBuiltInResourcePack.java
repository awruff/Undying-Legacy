package com.github.awruff.totems.mixins.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.resource.pack.BuiltInResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashSet;
import java.util.Set;

@Mixin(BuiltInResourcePack.class)
public class MixinBuiltInResourcePack {
    @ModifyReturnValue(method = "getNamespaces", at = @At("RETURN"))
    private Set<String> idk(Set<String> original) {
        // OSL doesn't do this for whatever reason... :(
        // this actually means I could move the textures into the mod's namespace but uhhh I'm eepy
        Set<String> set = new HashSet<>(original);
        set.add("undyinglegacy");
        return set;
    }
}
