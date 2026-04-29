package com.github.awruff.totems.mixins.common.lifesaver;

import com.github.awruff.totems.item.ModItems;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.entity.living.effect.StatusEffectInstance;
import net.minecraft.entity.living.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
abstract class MixinLivingEntity extends Entity {
    private MixinLivingEntity(World world) {
        super(world);
    }

    @Shadow
    public abstract void setHealth(float amount);

    @Shadow
    public abstract void clearStatusEffects();

    @Shadow
    public abstract void addStatusEffect(StatusEffectInstance instance);

    @Shadow
    public abstract ItemStack getItemInHand(InteractionHand hand);

    @Shadow
    public abstract void setItemInHand(InteractionHand hand, ItemStack item);

    @Definition(id = "getHealth", method = "Lnet/minecraft/entity/living/LivingEntity;getHealth()F")
    @Expression("this.getHealth() <= 0.0")
    @ModifyExpressionValue(method = "takeDamage", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean shouldDie(boolean original, @Local(argsOnly = true) DamageSource source) {
        return original && !popsTotem(source);
    }

    @Unique
    private boolean popsTotem(DamageSource source) {
        if (source.isOutOfWorld()) return false;

        ItemStack item = null;

        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack heldStack = getItemInHand(hand);

            if (heldStack != null && heldStack.getItem() == ModItems.TOTEM) {
                item = heldStack.copy();
                heldStack.size -= 1;

                if (heldStack.size <= 0) {
                    setItemInHand(hand, null);
                }

                break;
            }
        }

        if (item != null) {
            setHealth(1.0F);
            clearStatusEffects();
            addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
            addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));

            world.doEntityEvent((LivingEntity)(Object)this, (byte)35);
        }

        return item != null;
    }
}
