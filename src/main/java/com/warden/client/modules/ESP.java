package com.warden.client.modules;

import com.warden.client.modules.Mod.Category;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;

public class ESP extends Mod {
    public ESP() { 
        super("ESP", Category.RENDER); 
    }

    @Override
    public void onEnable() {
        WorldRenderEvents.AFTER_ENTITIES.register(this::renderBoxes);
    }

    private void renderBoxes(WorldRenderContext context) {
        if (!enabled || mc.world == null) return;

        // Dünyadaki TÜM varlıkları geziyoruz
        for (Entity entity : mc.world.getEntities()) {
            
            // Sadece canlı olanları (oyuncu, hayvan, yaratık) seç ve kendimizi hariç tut
            if (entity instanceof LivingEntity && entity != mc.player && entity.isAlive()) {
                
                // Varlığın hitbox kutusunu al
                Box box = entity.getBoundingBox();
                
                // Kırmızı bir kutu çiz (Renk: R:1, G:0, B:0, Alpha: 1)
                DebugRenderer.drawBox(context.matrixStack(), context.consumers(), box, 1.0f, 0.0f, 0.0f, 1.0f);
            }
        }
    }
}
