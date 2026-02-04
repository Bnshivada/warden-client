package com.warden.client.modules;

import com.warden.client.WardenClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;

public class KillAura extends Mod {
    public KillAura() { 
        super("KillAura", Category.COMBAT); 
    }

    @Override
    public void onTick() {
        if (nullCheck()) return;
        
        double range = WardenClient.reach.enabled ? Reach.reachDistance.getValue() : 3.0;

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
