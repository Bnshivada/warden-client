package com.warden.client;

import com.warden.client.gui.WardenMenuScreen;
import com.warden.client.modules.*; // Tüm modülleri dahil et
import com.warden.client.ui.HudRender; // Koordinat sistemi için
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class WardenClient implements ClientModInitializer {
    public static KeyBinding menuKey;
    public static List<Mod> modules = new ArrayList<>();

    // Hilelerimizi tanımlıyoruz
    public static KillAura killAura = new KillAura();
    public static ESP esp = new ESP();
    public static Xray xray = new Xray();

    @Override
    public void onInitializeClient() {
        // 1. Modülleri Listeye Ekle
        modules.add(killAura);
        modules.add(esp);
        modules.add(xray);

        // 2. Menü Tuşu: Sağ Shift
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.warden.menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "Warden Client"
        ));

        // 3. HUD Render (Koordinat ve Listeyi çizme)
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            HudRender.render(drawContext, tickDelta);
        });

        // 4. Ana Döngü (Tick)
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            // Menü Açma
            while (menuKey.wasPressed()) {
                client.setScreen(new WardenMenuScreen());
            }

            // Tüm modülleri her tick'te çalıştır
            for (Mod m : modules) {
                if (m.enabled) {
                    m.onTick();
                }
            }
        });
    }
}
