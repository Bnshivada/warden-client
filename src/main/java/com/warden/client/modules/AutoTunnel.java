package com.warden.client.modules;

import com.warden.client.modules.Mod.Category;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;

public class AutoTunnel extends Mod {
    public AutoTunnel() { 
        super("AutoTunnel", Category.WORLD); 
    }

    @Override
    public void onTick() {
        if (nullCheck()) return;

        // 1.21.4'te tuş kontrolü movementForward ile yapılır
        // 0'dan büyükse ileri (W) basılıyor demektir
        if (mc.player.input.movementForward <= 0) return;

        Direction facing = mc.player.getHorizontalFacing();

        BlockPos[] positions = {
            mc.player.getBlockPos().offset(facing),      // Ayak
            mc.player.getBlockPos().offset(facing).up()   // Kafa
        };

        for (BlockPos pos : positions) {
            if (mc.world.getBlockState(pos).isAir() || mc.world.getBlockState(pos).getHardness(mc.world, pos) < 0) continue;

            // Blok kırma işlemi
            mc.interactionManager.updateBlockBreakingProgress(pos, facing.getOpposite());
            mc.player.swingHand(Hand.MAIN_HAND);
        }
    }
}
