package com.warden.client.mixin;

import com.warden.client.modules.Xray;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "shouldDrawSide", at = @At("HEAD"), cancellable = true)
    private static void onShouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction side, BlockPos blockPos, CallbackInfoReturnable<Boolean> info) {
        // Eğer Xray açıksa
        if (Xray.isXrayEnabled) {
            // Sadece değerli blokların görünmesine izin ver (Örn: Elmas, Altın, Demir)
            // Eğer blok bir maden değilse, çizilmesini engelle
            boolean isOre = state.getBlock().getName().getString().contains("Ore") 
                            || state.getBlock().getName().getString().contains("Debris");
            
            info.setReturnValue(isOre);
        }
    }
}
