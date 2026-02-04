package com.warden.client.modules;

import net.minecraft.client.MinecraftClient;

public class Mod {
    public String name;
    public boolean enabled = false;
    protected static MinecraftClient mc = MinecraftClient.getInstance();

    public Mod(String name) {
        this.name = name;
    }

    public void toggle() {
        this.enabled = !this.enabled;
        if (enabled) onEnable(); else onDisable();
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}
}
