package com.warden.client.modules;

import com.warden.client.settings.NumberSetting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class AutoClicker extends Mod {
    // Saniyede kaç vuruş yapacağını seç (Varsayılan 10, Max 20)
    public NumberSetting cps = new NumberSetting("CPS", 10.0, 1.0, 20.0, 1.0);
    
    private long lastClickTime = 0;

    public AutoClicker() {
        super("AutoClicker");
        addSetting(cps);
    }

    @Override
    public void onTick() {
        // Sadece sol tık basılıysa çalışsın (Legit mod)
        if (enabled && mc.options.attackKey.isPressed()) {
            long currentTime = System.currentTimeMillis();
            // CPS hesaplaması (1000ms / CPS = Gecikme süresi)
            double delay = 1000.0 / cps.getValue();

            if (currentTime - lastClickTime >= delay) {
                // Eğer nişangahın ucunda bir varlık varsa ona vur
                if (mc.crosshairTarget != null && mc.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                    EntityHitResult entityHit = (EntityHitResult) mc.crosshairTarget;
                    mc.interactionManager.attackEntity(mc.player, entityHit.getEntity());
                    mc.player.swingHand(Hand.MAIN_HAND);
                } else {
                    // Yoksa sadece el salla (Animation)
                    mc.player.swingHand(Hand.MAIN_HAND);
                }
                
                lastClickTime = currentTime;
            }
        }
    }
}
