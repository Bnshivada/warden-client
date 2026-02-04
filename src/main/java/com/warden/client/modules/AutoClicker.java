package com.warden.client.modules;

import com.warden.client.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class AutoClicker extends Mod {
    public NumberSetting cps = new NumberSetting("CPS", 10.0, 1.0, 20.0, 1.0);
    private long lastClickTime = 0;
    // mc'yi buraya tanımlayalım ki hata vermesin
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public AutoClicker() {
        super("AutoClicker");
        addSetting(cps);
    }

    @Override
    public void onTick() {
        if (enabled && mc.options.attackKey.isPressed()) {
            long currentTime = System.currentTimeMillis();
            double delay = 1000.0 / cps.getValue();

            if (currentTime - lastClickTime >= delay) {
                if (mc.crosshairTarget != null && mc.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                    EntityHitResult entityHit = (EntityHitResult) mc.crosshairTarget;
                    mc.interactionManager.attackEntity(mc.player, entityHit.getEntity());
                    mc.player.swingHand(Hand.MAIN_HAND);
                } else {
                    mc.player.swingHand(Hand.MAIN_HAND);
                }
                lastClickTime = currentTime;
            }
        }
    }
}
