package com.warden.client.modules;

import com.warden.client.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;
import java.util.ArrayList;
import java.util.List;

public class Mod {
    public String name;
    public boolean enabled = false;
    public boolean expanded = false; // Menüde ayarların açık olup olmadığı
    public Category category; 
    public List<NumberSetting> settings = new ArrayList<>();
    
    // Her modülün Minecraft instance'ına güvenli erişimi
    protected final MinecraftClient mc = MinecraftClient.getInstance();

    // Kategorileri iç (inner) enum olarak tanımlıyoruz
    public enum Category {
        COMBAT, MOVEMENT, RENDER, PLAYER, WORLD
    }

    // Constructor: Artık her modül isim ve kategori ile doğmak zorunda
    public Mod(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public void addSetting(NumberSetting setting) {
        settings.add(setting);
    }

    public void toggle() {
        this.enabled = !this.enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    // Alt sınıflar tarafından doldurulacak metodlar
    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}

    // Yardımcı: Oyuncu dünyada değilse modülün hata vermesini önlemek için kullanılabilir
    protected boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }
}
