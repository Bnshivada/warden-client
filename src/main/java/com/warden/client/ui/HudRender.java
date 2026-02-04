package com.warden.client.ui;

import com.warden.client.WardenClient;
import com.warden.client.modules.Mod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class HudRender {
    // Pembe'den Mor'a geçiş için renk dizisi (örnek)
    private static final int[] GRADIENT_COLORS = {
            0xFFFF69B4, // Hot Pink
            0xFFDA70D6, // Orchid
            0xFFBA55D3, // Medium Orchid
            0xFF9932CC, // Dark Orchid
            0xFF8A2BE2, // Blue Violet
            0xFF9400D3, // Dark Violet
            0xFF4B0082  // Indigo
    };

    public static void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.options.hudHidden) return;

        // --- SOL ÜST: GRADIENT BAŞLIK ---
        String title = "WARDEN CLIENT";
        int startX = 10;
        int startY = 10;

        for (int i = 0; i < title.length(); i++) {
            char c = title.charAt(i);
            int colorIndex = i % GRADIENT_COLORS.length;
            int color = GRADIENT_COLORS[colorIndex];
            
            context.drawText(mc.textRenderer, String.valueOf(c), startX, startY, color, true);
            startX += mc.textRenderer.getWidth(String.valueOf(c)); // Her karakterin genişliği kadar ilerle
        }

        // --- SOL ALT: GRİ KOORDİNATLAR ---
        // İstediğin o özel #BFBFBF (0xBFBFBF) Gri tonu
        int x = (int) mc.player.getX();
        int y = (int) mc.player.getY();
        int z = (int) mc.player.getZ();

        context.drawTextWithShadow(mc.textRenderer, "§fX: §7" + x, 10, startY + 20, 0xBFBFBF); // Başlığın biraz altına
        context.drawTextWithShadow(mc.textRenderer, "§fY: §7" + y, 10, startY + 30, 0xBFBFBF);
        context.drawTextWithShadow(mc.textRenderer, "§fZ: §7" + z, 10, startY + 40, 0xBFBFBF);


        // --- SAĞ ÜST: AKTİF HİLELER LİSTESİ (Sadece Gri) ---
        int offset = 0;
        int screenWidth = mc.getWindow().getScaledWidth();

        for (Mod mod : WardenClient.modules) {
            if (mod.enabled) {
                String text = "§7" + mod.name; // Sadece gri tonunda
                int textWidth = mc.textRenderer.getWidth(text);
                
                context.drawTextWithShadow(mc.textRenderer, text, screenWidth - textWidth - 5, 5 + offset, 0xFFFFFF); // 0xFFFFFF beyaz yazının gölgesi için
                offset += 12; // Yazılar üst üste binmesin diye aralık
            }
        }
    }
}
