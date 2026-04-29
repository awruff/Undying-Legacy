package com.github.awruff.totems.mixins.common.item;

import com.github.awruff.totems.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.crafting.CraftingManager;
import net.minecraft.crafting.recipe.ShapedRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingManager.class)
abstract class MixinCraftingManager {
    @Shadow
    public abstract ShapedRecipe registerShaped(ItemStack result, Object... args);

    @Inject(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Ljava/util/Collections;sort(Ljava/util/List;Ljava/util/Comparator;)V")
    )
    private void addTotem(CallbackInfo ci) {
        registerShaped(new ItemStack(ModItems.TOTEM, 1), "gGg", "GEG", "gGg", 'G', Blocks.GOLD_BLOCK, 'g', Items.GOLD_INGOT, 'E', Items.EMERALD);
    }
}
