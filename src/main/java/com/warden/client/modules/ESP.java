package com.warden.client.modules;

import net.minecraft.entity.player.PlayerEntity;

public class ESP extends Mod {
    public ESP() {
        super("ESP");
    }

    @Override
    public void onTick() {
        if (!enabled || mc.world == null) return;

        mc.world.getEntities().forEach(entity -> {
            if (entity instanceof PlayerEntity && entity != mc.player) {
                // Glow (parlama) efekti vererek duvar arkasından görmeyi sağlar
                entity.setGlowing(true);
            }
        });
    }

    @Override
    public void onDisable() {
        // Hile kapandığında parlamayı kapat
        if (mc.world != null) {
            mc.world.getEntities().forEach(entity -> entity.setGlowing(false));
        }
    }
}
