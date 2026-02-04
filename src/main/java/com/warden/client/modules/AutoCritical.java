package com.warden.client.modules;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class AutoCritical extends Mod {
    public AutoCritical() {
        super("Criticals");
    }

    @Override
    public void onTick() {
        if (!enabled || mc.player == null) return;

        // El sallanıyorsa ve oyuncu yerdeyse
        if (mc.player.handSwinging && mc.player.isOnGround()) {
            if (mc.player.getAttackCooldownProgress(0.5f) >= 0.9f) {
                double x = mc.player.getX();
                double y = mc.player.getY();
                double z = mc.player.getZ();

                if (mc.getNetworkHandler() != null) {
                    // 1.21.4 formatı: (x, y, z, onGround, horizontalCollision)
                    // Yukarı çıkış paketi
                    mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + 0.0625, z, true, false)); 
                    
                    // Aşağı iniş paketi
                    mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, false, false));
                }
            }
        }
    }
}
