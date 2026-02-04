package com.warden.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class WardenMenuScreen extends Screen {
    public WardenMenuScreen() {
        super(Text.literal("Warden Menu"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0x90000000);
        
        context.drawCenteredTextWithShadow(this.textRenderer, "WARDEN CLIENT v1.0", this.width / 2, 20, 0x00FFFF);
        
        context.drawTextWithShadow(this.textRenderer, "> [X] BoatFly", 20, 50, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, "> [ ] KillAura", 20, 65, 0xAAAAAA);
        
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }
}
