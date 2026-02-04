package com.warden.client.gui;

import com.warden.client.WardenClient;
import com.warden.client.modules.Mod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class WardenMenuScreen extends Screen {
    public WardenMenuScreen() {
        super(Text.literal("Warden Menu"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // 1. Arka Planı Karart (Daha derin bir siyah)
        context.fill(0, 0, this.width, this.height, 0xB0000000); 

        // 2. Ana Panel Çerçevesi (Opsiyonel: Şık durması için)
        int panelX = 15;
        int panelY = 15;
        int panelWidth = 150;
        int panelHeight = 120;
        context.fill(panelX, panelY, panelX + panelWidth, panelY + panelHeight, 0x60FFFFFF); // Şeffaf panel

        // 3. Başlık (Turkuaz Warden Yazısı)
        context.drawTextWithShadow(this.textRenderer, "§b§lWARDEN §f§lCLIENT §7v1.1", panelX + 5, panelY + 5, 0xFFFFFF);
        context.fill(panelX + 5, panelY + 16, panelX + panelWidth - 5, panelY + 17, 0xFF00FFFF); // Alt çizgi

        // 4. Modülleri Listele ve Durumlarını Göster
        int startY = panelY + 25;
        for (Mod mod : WardenClient.modules) {
            String status = mod.enabled ? "§a[ON]" : "§c[OFF]";
            // Fare butonun üzerindeyse rengi değiştir (Hover efekti)
            int color = isHovering(mouseX, mouseY, panelX + 5, startY, 100, 10) ? 0x00FFFF : 0xFFFFFF;
            
            context.drawTextWithShadow(this.textRenderer, mod.name + ": " + status, panelX + 10, startY, color);
            startY += 15; // Bir sonraki hile için aşağı kaydır
        }

        context.drawTextWithShadow(this.textRenderer, "§7§oEsc ile kapat", this.width - 80, this.height - 20, 0xAAAAAA);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int panelX = 15;
        int startY = 40; // Yazıların başladığı Y koordinatı

        for (Mod mod : WardenClient.modules) {
            // Her modülün tıklama alanını kontrol et
            if (mouseX >= panelX + 10 && mouseX <= panelX + 110 && mouseY >= startY && mouseY <= startY + 10) {
                mod.toggle(); // Hileyi aç/kapat
                return true;
            }
            startY += 15;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    // Fare bir bölgenin üzerinde mi? (Efekt için yardımcı fonksiyon)
    private boolean isHovering(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    @Override
    public boolean shouldPause() { return false; }
}
