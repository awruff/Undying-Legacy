package com.github.awruff.totems.mixins.common.item;

import com.github.awruff.totems.item.TotemOfUndyingItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
abstract class MixinItem {

    @Shadow
    private static void register(int id, String key, Item item) {
    }

    @Inject(
            method = "init",
            at = @At(
                    value = "TAIL"
            )
    )
    private static void init(CallbackInfo ci) {
        register(6769, "totem_of_undying", new TotemOfUndyingItem().setKey("totem_of_undying"));
    }
}