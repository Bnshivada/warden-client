package com.warden.client.ui;

import com.warden.client.WardenClient;
import com.warden.client.modules.Mod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class HudRender {
    public static void render(DrawContext context, float tickDelta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.options.hudHidden) return;

        // 1. Ana Başlık (Görseldeki stil)
        // Warden yazısı beyaz, v1.1 yazısı gri
        context.drawTextWithShadow(mc.textRenderer, "§fWARDEN §7v1.1", 10, 10, 0xFFFFFF);

        // 2. Koordinat Sistemi (Küsüratsız tam sayı)
        // İstediğin o özel gri tonu: 0xBFBFBF
        int x = (int) mc.player.getX();
        int y = (int) mc.player.getY();
        int z = (int) mc.player.getZ();

        context.drawTextWithShadow(mc.textRenderer, "§fX: §7" + x, 10, 25, 0xBFBFBF);
        context.drawTextWithShadow(mc.textRenderer, "§fY: §7" + y, 10, 35, 0xBFBFBF);
        context.drawTextWithShadow(mc.textRenderer, "§fZ: §7" + z, 10, 45, 0xBFBFBF);

        // 3. Aktif Modüller Listesi (Sağ Üst Köşe)
        // Hangi hile açıksa onu ekranda gösterir
        int offset = 0;
        for (Mod mod : WardenClient.modules) {
            if (mod.enabled) {
                // Aktif hileleri sağ üste alt alta dizer
                int width = mc.getWindow().getScaledWidth();
                String text = "§f" + mod.name;
                int textWidth = mc.textRenderer.getWidth(text);
                
                context.drawTextWithShadow(mc.textRenderer, text, width - textWidth - 5, 5 + offset, 0xFFFFFF);
                offset += 10;
            }
        }
    }
}
