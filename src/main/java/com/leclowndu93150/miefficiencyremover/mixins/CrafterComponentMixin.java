package com.leclowndu93150.miefficiencyremover.mixins;

import aztech.modern_industrialization.machines.components.CrafterComponent;
import aztech.modern_industrialization.machines.recipe.MachineRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrafterComponent.class)
public abstract class CrafterComponentMixin {

    @Shadow private int efficiencyTicks;
    @Shadow private int maxEfficiencyTicks;
    @Shadow private long recipeEnergy;
    @Shadow private long usedEnergy;
    @Shadow private RecipeHolder<MachineRecipe> activeRecipe = null;

    @Inject(method = "decreaseEfficiencyTicks", at = @At("HEAD"), cancellable = true)
    private void decreaseEfficiencyTicks(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "increaseEfficiencyTicks", at = @At("HEAD"), cancellable = true)
    private void increaseEfficiencyTicks(int increment, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "tickRecipe", at = @At("HEAD"))
    private void tickRecipe(CallbackInfoReturnable<Boolean> cir) {
        if (activeRecipe != null) {
            if (usedEnergy == 0) {
                efficiencyTicks = 0;
            }
            if (usedEnergy > 0) {
                efficiencyTicks = maxEfficiencyTicks;
            }
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    private void readNbt(net.minecraft.nbt.CompoundTag tag, boolean isUpgradingMachine, CallbackInfo ci) {
        efficiencyTicks = maxEfficiencyTicks;
    }
}