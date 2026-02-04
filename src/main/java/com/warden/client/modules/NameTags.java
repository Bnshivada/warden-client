package com.warden.client.modules;

import com.warden.client.modules.Mod.Category;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class NameTags extends Mod {
    public NameTags() { super("NameTags", Category.RENDER); }

    @Override
    public void onEnable() {
        WorldRenderEvents.AFTER_ENTITIES.register(this::renderTags);
    }

    private void renderTags(WorldRenderContext context) {
        if (!enabled || mc.world == null || mc.player == null) return;

        for (Entity entity : mc.world.getEntities()) {
            if (entity == mc.player || !entity.isAlive()) continue;

            String label = "";

            if (entity instanceof PlayerEntity player) {
                int health = (int) (player.getHealth() + player.getAbsorptionAmount());
                label = player.getName().getString() + " §c" + health + "❤";
            } 
            else if (entity instanceof ItemEntity item) {
                label = item.getStack().getName().getString() + " §bx" + item.getStack().getCount();
            }

            if (!label.isEmpty()) {
                // ÇARPI BURADAYSA: tickCounter() yerine direkt getTickDelta'yı deneyelim
                float tickDelta = context.tickDelta(); 
                Vec3d pos = entity.getLerpedPos(tickDelta).add(0, entity.getHeight() + 0.5, 0);
                
                renderTextInWorld(context, label, pos);
            }
        }
    }

    private void renderTextInWorld(WorldRenderContext context, String text, Vec3d pos) {
        MatrixStack matrices = context.matrixStack();
        TextRenderer tr = mc.textRenderer;
        
        matrices.push();
        
        Vec3d camPos = context.camera().getPos();
        matrices.translate(pos.x - camPos.x, pos.y - camPos.y, pos.z - camPos.z);
        
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-context.camera().getYaw()));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(context.camera().getPitch()));
        
        float scale = 0.025f;
        matrices.scale(-scale, -scale, scale);

        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        VertexConsumerProvider consumers = context.consumers();
        
        float xOffset = (float) (-tr.getWidth(text) / 2);
        
        // ÇARPI BURADAYSA: tr.draw metodunun parametrelerini 1.21.4'e göre güncelleyelim
        tr.draw(text, xOffset, 0, 0xFFFFFFFF, false, matrix4f, consumers, TextRenderer.TextLayerType.SEE_THROUGH, 0x80000000, 15728880);

        matrices.pop();
    }
}
