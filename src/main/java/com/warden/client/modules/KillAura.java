package com.warden.client.modules;

import com.warden.client.WardenClient; // Reach modülüne erişmek için
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;

public class KillAura extends Mod {
    public KillAura() { super("KillAura"); }

    @Override
    public void onTick() {
        if (!enabled || mc.player == null || mc.world == null) return;
        
        // Reach modülü açıksa onun ayarını, kapalıysa 3.0 (default) al
        double range = WardenClient.reach.enabled ? WardenClient.reach.reachDistance.getValue() : 3.0;

        for (Entity target : mc.world.getEntities()) {
            if (target instanceof LivingEntity && target != mc.player && target.isAlive()) {
                if (mc.player.distanceTo(target) <= range) {
                    if (mc.player.getAttackCooldownProgress(0.5f) >= 1.0f) {
                        mc.interactionManager.attackEntity(mc.player, target);
                        mc.player.swingHand(Hand.MAIN_HAND);
                        break;
                    }
                }
            }
        }
    }
}
