package com.github.awruff.totems.mixins.common.item;

import com.github.awruff.totems.item.ModItems;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
abstract class MixinRenderItem {
    @Shadow
    protected abstract void registerModel(Item item, String key);

    @Inject(
            method = "registerGuiModels",
            at = @At(
                    value = "TAIL"
            )
    )
    private void addTotem(CallbackInfo ci) {
        registerModel(ModItems.TOTEM, "totem_of_undying");
    }
}
