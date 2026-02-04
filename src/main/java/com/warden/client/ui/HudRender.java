package com.warden.client.ui;

import com.warden.client.WardenClient;
import com.warden.client.modules.Mod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class HudRender {
    private static final int[] GRADIENT_COLORS = {0xFFFF69B4, 0xFFDA70D6, 0xFFBA55D3, 0xFF9932CC, 0xFF8A2BE2, 0xFF9400D3, 0xFF4B0082};

    public static void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.options.hudHidden) return;

        // --- GRADIENT LOGO ---
        String title = "WARDEN CLIENT";
        int startX = 10, startY = 10;
        for (int i = 0; i < title.length(); i++) {
            context.drawText(mc.textRenderer, String.valueOf(title.charAt(i)), startX, startY, GRADIENT_COLORS[i % GRADIENT_COLORS.length], true);
            startX += mc.textRenderer.getWidth(String.valueOf(title.charAt(i)));
        }

        // --- AKTİF HİLELER (XYZ SİLİNDİ, BOŞLUKSUZ ALT ALTA) ---
        int offset = 22; // Logonun hemen altından başlar
        for (Mod mod : WardenClient.modules) {
            if (mod.enabled) {
                // Sadece gri (§7) renkte hile ismi
                context.drawTextWithShadow(mc.textRenderer, "§7" + mod.name, 10, offset, 0xFFFFFF);
                offset += 10; // Tam alt alta (boşluksuz)
            }
        }
    }
}
