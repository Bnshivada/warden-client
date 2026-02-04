package com.warden.client.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.Vec3d;

public class MovementModules {
    public static void runBoatFly(MinecraftClient client) {
        if (client.player != null && client.player.getVehicle() instanceof BoatEntity boat) {
            Vec3d velocity = boat.getVelocity();
            double speed = 0.5; // Yükselme ve alçalma hızı

            // Space ile yüksel, Left Shift ile alçal
            double y = 0;
            if (client.options.jumpKey.isPressed()) y = speed;
            else if (client.options.sneakKey.isPressed()) y = -speed;

            boat.setVelocity(velocity.x, y, velocity.z);
        }
    }
}
