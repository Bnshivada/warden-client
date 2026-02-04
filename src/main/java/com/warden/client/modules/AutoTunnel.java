package com.warden.client.modules;

import com.warden.client.modules.Mod.Category;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Hand;

public class AutoTunnel extends Mod {
    public AutoTunnel() { super("AutoTunnel", Category.WORLD); }

    @Override
    public void onTick() {
        if (nullCheck() || !mc.player.input.pressingForward) return;

        // Oyuncunun tam önündeki 2 bloğu (kafa ve ayak hizası) hedefle
        BlockPos[] positions = {
            mc.player.getBlockPos().offset(mc.player.getHorizontalFacing()).up(), // Kafa
            mc.player.getBlockPos().offset(mc.player.getHorizontalFacing())       // Ayak
        };

        for (BlockPos pos : positions) {
            if (mc.world.getBlockState(pos).isAir()) continue;
            mc.interactionManager.updateBlockBreakingProgress(pos, mc.player.getHorizontalFacing().getOpposite());
            mc.player.swingHand(Hand.MAIN_HAND);
        }
    }
}
