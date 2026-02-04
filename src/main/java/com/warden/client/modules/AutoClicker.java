package com.warden.client.modules;

import com.warden.client.settings.NumberSetting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class AutoClicker extends Mod {
    public NumberSetting cps = new NumberSetting("CPS", 10.0, 1.0, 20.0, 1.0);
    private long lastClickTime = 0;

    public AutoClicker() {
        super("AutoClicker", Category.COMBAT);
        addSetting(cps);
    }

    @Override
    public void onTick() {
        if (nullCheck() || !mc.options.attackKey.isPressed()) return;

        long currentTime = System.currentTimeMillis();
        double delay = 1000.0 / cps.getValue();

        if (currentTime - lastClickTime >= delay) {
            if (mc.crosshairTarget != null && mc.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHit = (EntityHitResult) mc.crosshairTarget;
                mc.interactionManager.attackEntity(mc.player, entityHit.getEntity());
            }
            mc.player.swingHand(Hand.MAIN_HAND);
            lastClickTime = currentTime;
        }
    }
}
