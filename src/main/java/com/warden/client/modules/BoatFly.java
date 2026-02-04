package com.warden.client.modules;

import com.warden.client.settings.NumberSetting;
// 1. Çarpıyı silen import
import com.warden.client.modules.Mod.Category;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.Vec3d;

public class BoatFly extends Mod {
    public NumberSetting speed = new NumberSetting("Hiz", 1.0, 0.1, 30.0, 0.5);

    public BoatFly() {
        super("BoatFly", Category.MOVEMENT);
        addSetting(speed);
    }

    @Override
    public void onTick() {
        // nullCheck Mod.java'dan geliyor
        if (nullCheck() || !(mc.player.getVehicle() instanceof BoatEntity boat)) return;

        double flySpeed = speed.getValue();
        
        // 2. W-A-S-D Kontrolü (Hareketi oyuncunun girdilerine göre hesapla)
        float forward = mc.player.input.movementForward;
        float sideways = mc.player.input.movementSideways;
        float yaw = mc.player.getYaw();

        // Hareket yoksa hızı sıfırla (Kendi kendine gitmesin)
        if (forward == 0 && sideways == 0) {
            boat.setVelocity(0, 0, 0);
        } else {
            // Bakılan açıya göre matematiksel vektör hesaplama
            double rad = Math.toRadians(yaw);
            double sin = Math.sin(rad);
            double cos = Math.cos(rad);
            
            double velocityX = (forward * cos - sideways * sin) * flySpeed;
            double velocityZ = (forward * sin + sideways * cos) * flySpeed;
            
            double vertical = 0;
            if (mc.options.jumpKey.isPressed()) vertical = 0.5; // Space ile yukarı
            else if (mc.options.sprintKey.isPressed()) vertical = -0.5; // CTRL ile aşağı

            boat.setVelocity(velocityX, vertical, velocityZ);
        }
        
        // Teknenin havada sabit kalmasını sağlar (Yerçekimini yenmek için)
        boat.setNoGravity(true);
    }

    @Override
    public void onDisable() {
        if (mc.player != null && mc.player.getVehicle() instanceof BoatEntity boat) {
            boat.setNoGravity(false);
        }
    }
}
