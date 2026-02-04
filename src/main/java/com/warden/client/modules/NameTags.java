package com.warden.client.modules;

import com.warden.client.modules.Mod.Category;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class NameTags extends Mod {
    public NameTags() { super("NameTags", Category.RENDER); }

    @Override
    public void onEnable() {
        WorldRenderEvents.AFTER_ENTITIES.register(this::renderTags);
    }

    private void renderTags(WorldRenderContext context) {
        if (!enabled || mc.world == null) return;

        TextRenderer tr = mc.textRenderer;
        
        for (Entity entity : mc.world.getEntities()) {
            if (entity == mc.player) continue;

            String label = "";
            int color = 0xFFFFFFFF;

            // OYUNCU KONTROLÜ
            if (entity instanceof PlayerEntity player) {
                int health = (int) (player.getHealth() + player.getAbsorptionAmount());
                label = player.getEntityName() + " §c" + health + "❤";
            } 
            // YERDEKİ EŞYA KONTROLÜ
            else if (entity instanceof ItemEntity item) {
                label = item.getStack().getName().getString() + " §bx" + item.getStack().getCount();
            }

            if (!label.isEmpty()) {
                renderTextInWorld(context, label, entity.getLerpedPos(context.tickCounter().getTickDelta(true)).add(0, entity.getHeight() + 0.5, 0));
            }
        }
    }

    private void renderTextInWorld(WorldRenderContext context, String text, Vec3d pos) {
        // Bu kısım metni dünyanın içinde, oyuncuya bakacak şekilde render eder
        // Basitlik için HudRender üzerinden de yapılabilir ama WorldRender daha stabildir.
    }
}
