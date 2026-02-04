package com.warden.client;

import com.warden.client.gui.WardenMenuScreen;
import com.warden.client.modules.*;
import com.warden.client.ui.HudRender;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents; // ESP İçin
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.Box;
import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import java.util.List;

public class WardenClient implements ClientModInitializer {
    public static KeyBinding menuKey;
    public static List<Mod> modules = new ArrayList<>();

    // Modüller
    public static KillAura killAura = new KillAura();
    public static Reach reach = new Reach(); // Reach ayrı!
    public static ESP esp = new ESP();
    public static Xray xray = new Xray();
    public static BoatFly boatFly = new BoatFly();

    @Override
    public void onInitializeClient() {
        // Listeye ekle
        modules.add(killAura);
        modules.add(reach);
        modules.add(esp);
        modules.add(xray);
        modules.add(boatFly);

        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.warden.menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "Warden Client"
        ));

        // HUD Render
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            HudRender.render(drawContext, tickCounter);
        });

        // ESP RENDER (Kutu Çizimi)
        WorldRenderEvents.LAST.register(context -> {
            if (esp.enabled) {
                 // Basit ESP Mantığı: Bu kısım shadersız 1.21.4'te karmaşık debug renderer gerektirir.
                 // Şimdilik oyuncuların glow efektini zorluyoruz (En stabil yöntem)
                 net.minecraft.client.MinecraftClient.getInstance().world.getPlayers().forEach(p -> {
                     if(p != net.minecraft.client.MinecraftClient.getInstance().player) {
                         p.setGlowing(true);
                     }
                 });
            }
        });

        // Modül Döngüsü
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            while (menuKey.wasPressed()) client.setScreen(new WardenMenuScreen());
            for (Mod m : modules) if (m.enabled) m.onTick();
        });
    }
}
