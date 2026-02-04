package com.warden.client.mixin;

import com.warden.client.modules.Xray;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
    @Inject(method = "getAmbientOcclusionLightLevel", at = @At("HEAD"), cancellable = true)
    private void onGetAmbientOcclusionLightLevel(BlockState state, net.minecraft.world.BlockView world, net.minecraft.util.math.BlockPos pos, CallbackInfoReturnable<Float> info) {
        if (Xray.isXrayEnabled) {
            info.setReturnValue(1.0f); // Her yeri aydınlık yap
        }
    }
}
