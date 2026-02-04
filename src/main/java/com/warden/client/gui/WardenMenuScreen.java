package com.warden.client.gui;

import com.warden.client.WardenClient;
import com.warden.client.modules.Mod;
import com.warden.client.settings.NumberSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class WardenMenuScreen extends Screen {
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public WardenMenuScreen() {
        super(Text.literal("Warden ClickGUI"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0x90000000);
        
        int panelX = 50;
        int panelY = 50;
        int width = 120;
        int height = 20;

        // Başlık
        context.fill(panelX, panelY, panelX + width, panelY + height, 0xFF9400D3);
        context.drawTextWithShadow(textRenderer, "Warden Client", panelX + 5, panelY + 6, 0xFFFFFF);

        int offsetY = height;

        for (Mod mod : WardenClient.modules) {
            int color = mod.enabled ? 0xFF9932CC : 0x80000000;
            context.fill(panelX, panelY + offsetY, panelX + width, panelY + offsetY + height, color);
            context.drawTextWithShadow(textRenderer, mod.name, panelX + 5, panelY + offsetY + 6, 0xFFFFFF);
            
            offsetY += height;

            if (mod.expanded) {
                for (NumberSetting set : mod.settings) {
                    context.fill(panelX, panelY + offsetY, panelX + width, panelY + offsetY + height, 0x90000000);
                    
                    double diff = set.max - set.min;
                    double val = set.getValue() - set.min;
                    double percent = val / diff;
                    int sliderWidth = (int) (width * percent);

                    context.fill(panelX, panelY + offsetY + 14, panelX + sliderWidth, panelY + offsetY + 18, 0xFF00FFFF);
                    
                    String display = set.name + ": " + String.format("%.1f", set.getValue());
                    context.drawTextWithShadow(textRenderer, display, panelX + 5, panelY + offsetY + 4, 0xAAAAAA);
                    
                    offsetY += height;
                }
            }
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int panelX = 50;
        int panelY = 50 + 20;
        int width = 120;
        int height = 20;

        for (Mod mod : WardenClient.modules) {
            if (isHovered(mouseX, mouseY, panelX, panelY, width, height)) {
                if (button == 0) {
                    mod.toggle();
                    return true;
                } else if (button == 1) {
                    mod.expanded = !mod.expanded;
                    return true;
                }
            }
            panelY += height;

            if (mod.expanded) {
                for (NumberSetting set : mod.settings) {
                    if (isHovered(mouseX, mouseY, panelX, panelY, width, height)) {
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

    @Override
    public boolean shouldPause() { return false; }
}
