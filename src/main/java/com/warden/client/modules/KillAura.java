package com.warden.client.modules;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;

public class KillAura extends Mod {
    public KillAura() {
        super("KillAura");
    }

    @Override
    public void onTick() {
        if (!enabled || mc.player == null || mc.world == null) return;

        double reach = 4.5; // Vuruş mesafesi (Reach)
        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof LivingEntity && entity != mc.player && entity.isAlive()) {
                if (mc.player.distanceTo(entity) <= reach) {
                    // Saldırı bekleme süresi dolmuşsa vur
                    if (mc.player.getAttackCooldownProgress(0.5f) >= 1.0f) {
                        mc.interactionManager.attackEntity(mc.player, entity);
                        mc.player.swingHand(Hand.MAIN_HAND);
                        break;
                    }
                }
            }
        }
    }
}
