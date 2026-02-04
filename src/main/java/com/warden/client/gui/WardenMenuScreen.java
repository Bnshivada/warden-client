package com.warden.client.gui;

import com.warden.client.WardenClient;
import com.warden.client.modules.Mod;
import com.warden.client.settings.NumberSetting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.Color;

public class WardenMenuScreen extends Screen {
    public WardenMenuScreen() { super(Text.literal("Warden ClickGUI")); }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        
        // MODERN PANEL TASARIMI
        int panelX = 50;
        int panelY = 50;
        int width = 120;
        int height = 20;

        // Başlık
        context.fill(panelX, panelY, panelX + width, panelY + height, 0xFF9400D3); // Mor Başlık
        context.drawTextWithShadow(textRenderer, "Warden Client", panelX + 5, panelY + 6, 0xFFFFFF);

        int offsetY = height;

        for (Mod mod : WardenClient.modules) {
            // Modül Butonu
            int color = mod.enabled ? 0xFF9932CC : 0x80000000; // Açık: Mor, Kapalı: Siyahımsı
            context.fill(panelX, panelY + offsetY, panelX + width, panelY + offsetY + height, color);
            context.drawTextWithShadow(textRenderer, mod.name, panelX + 5, panelY + offsetY + 6, 0xFFFFFF);
            
            // Eğer Mouse üstündeyse "Sağ Tıkla" ipucu
            if (isHovered(mouseX, mouseY, panelX, panelY + offsetY, width, height)) {
                if(!mod.settings.isEmpty()) 
                    context.drawText(textRenderer, "...", panelX + width - 15, panelY + offsetY + 6, 0xCCCCCC, false);
            }

            offsetY += height;

            // --- AYARLAR (SLIDER) KISMI ---
            if (mod.expanded) {
                for (NumberSetting set : mod.settings) {
                    // Arka plan
                    context.fill(panelX, panelY + offsetY, panelX + width, panelY + offsetY + height, 0x90000000);
                    
                    // Slider Çubuğu Hesaplama
                    double diff = set.max - set.min;
                    double val = set.getValue() - set.min;
                    double percent = val / diff;
                    int sliderWidth = (int) (width * percent);

                    // Slider Doluluğu (Turkuaz)
                    context.fill(panelX, panelY + offsetY + 14, panelX + sliderWidth, panelY + offsetY + 18, 0xFF00FFFF);
                    
                    // Yazılar
                    String display = set.name + ": " + String.format("%.1f", set.getValue());
                    context.drawTextWithShadow(textRenderer, display, panelX + 5, panelY + offsetY + 4, 0xAAAAAA);

                    // Slider Sürükleme Mantığı
                    if (isHovered(mouseX, mouseY, panelX, panelY + offsetY, width, height) && net.minecraft.client.util.InputUtil.isKeyPressed(mc.getWindow().getHandle(), 256)) { // Sol tık basılıysa (Basit kontrol)
                       // Burada sürükleme mantığı karmaşık olacağı için tıklama ile artırma yapıyoruz
                    }
                    
                    offsetY += height;
                }
            }
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int panelX = 50;
        int panelY = 50 + 20; // Başlık boyunu ekle
        int width = 120;
        int height = 20;

        for (Mod mod : WardenClient.modules) {
            // Modüle tıklandı mı?
            if (isHovered(mouseX, mouseY, panelX, panelY, width, height)) {
                if (button == 0) { // Sol Tık -> Modu Aç/Kapat
                    mod.toggle();
                    return true;
                } else if (button == 1) { // Sağ Tık -> Ayarları Aç (Expand)
                    mod.expanded = !mod.expanded;
                    return true;
                }
            }
            panelY += height;

            // Ayarlar açıksa tıklama alanını kaydır ve Slider kontrolü yap
            if (mod.expanded) {
                for (NumberSetting set : mod.settings) {
                    if (isHovered(mouseX, mouseY, panelX, panelY, width, height)) {
                        // Basit Slider: Sol tık artırır, Sağ tık azaltır
                        if (button == 0) set.setValue(set.getValue() + set.increment);
                        if (button == 1) set.setValue(set.getValue() - set.increment);
                        return true;
                    }
                    panelY += height;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean isHovered(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
