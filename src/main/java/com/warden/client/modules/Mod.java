package com.warden.client.modules;

import com.warden.client.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;
import java.util.ArrayList;
import java.util.List;

public class Mod {
    public String name;
    public boolean enabled = false;
    public boolean expanded = false; // Menüde ayarları açık mı?
    public List<NumberSetting> settings = new ArrayList<>(); // Ayar listesi
    protected static MinecraftClient mc = MinecraftClient.getInstance();

    public Mod(String name) {
        this.name = name;
    }

    public void addSetting(NumberSetting setting) {
        settings.add(setting);
    }

    public void toggle() {
        this.enabled = !this.enabled;
        if (enabled) onEnable(); else onDisable();
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}
}
