package com.warden.client;

import com.warden.client.gui.WardenMenuScreen;
import com.warden.client.modules.MovementModules;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class WardenClient implements ClientModInitializer {
    public static KeyBinding menuKey;
    public static boolean boatFlyEnabled = false; // Hilenin açık/kapalı durumu

    @Override
    public void onInitializeClient() {
        // Menü Tuşu: Sağ Shift
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.warden.menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "Warden Client"
        ));

        // Oyunun her anında çalışan döngü
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            // Menü Açma Kontrolü
            while (menuKey.wasPressed()) {
                client.setScreen(new WardenMenuScreen());
            }

            // BoatFly aktifse çalıştır
            if (boatFlyEnabled) {
                MovementModules.runBoatFly(client);
            }
        });
    }
}
