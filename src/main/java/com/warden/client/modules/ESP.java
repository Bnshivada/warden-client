package com.warden.client.modules;

import net.minecraft.client.MinecraftClient;

public class ESP extends Mod {
    public ESP() {
        super("ESP");
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        client.world.getPlayers().forEach(player -> {
            if (player != client.player) {
                player.setGlowing(enabled); // ESP açıksa parlasın, kapalıysa sönsün
            }
        });
    }

    @Override
    public void onDisable() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null) {
            client.world.getPlayers().forEach(p -> p.setGlowing(false));
        }
    }
}
