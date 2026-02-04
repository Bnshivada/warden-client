package com.warden.client.modules;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class AutoCritical extends Mod {
    public AutoCritical() { super("Criticals", Category.COMBAT); }

    @Override
    public void onTick() {
        if (nullCheck()) return;

        if (mc.player.handSwinging && mc.player.isOnGround()) {
            if (mc.player.getAttackCooldownProgress(0.5f) >= 0.9f && mc.getNetworkHandler() != null) {
                double x = mc.player.getX(), y = mc.player.getY(), z = mc.player.getZ();
                mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + 0.0625, z, true, false)); 
                mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, false, false));
            }
        }
    }
}
