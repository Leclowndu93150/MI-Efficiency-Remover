package com.leclowndu93150.miefficiencyremover.mixins;

import net.swedz.tesseract.neoforge.compat.mi.component.craft.AbstractModularCrafterComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractModularCrafterComponent.class)
public abstract class AbstractModularCrafterComponentMixin {

    @Shadow private int efficiencyTicks;
    @Shadow private int maxEfficiencyTicks;
    @Shadow private long recipeEnergy;
    @Shadow private long usedEnergy;

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
        efficiencyTicks = maxEfficiencyTicks;
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    private void readNbt(net.minecraft.nbt.CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries, boolean isUpgradingMachine, CallbackInfo ci) {
        efficiencyTicks = maxEfficiencyTicks;
    }
}