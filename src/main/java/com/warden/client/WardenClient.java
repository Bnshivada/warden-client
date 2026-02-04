package com.warden.client;

import com.warden.client.gui.WardenMenuScreen;
import com.warden.client.modules.*;
import com.warden.client.ui.HudRender;
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

    // Modüllerin statik tanımları (Diğer yerlerden erişim için)
    public static KillAura killAura;
    public static Reach reach;
    public static ESP esp;
    public static Xray xray;
    public static BoatFly boatFly;
    public static AutoClicker autoClicker;
    public static AutoCritical autoCritical;

    @Override
    public void onInitializeClient() {
        // 1. Modülleri oluştur
        killAura = new KillAura();
        reach = new Reach();
        esp = new ESP();
        xray = new Xray();
        boatFly = new BoatFly();
        autoClicker = new AutoClicker();
        autoCritical = new AutoCritical();

        // 2. Listeye ekle (Menüde ve Tick döngüsünde görünmeleri için)
        modules.add(killAura);
        modules.add(reach);
        modules.add(autoClicker);
        modules.add(autoCritical);
        modules.add(esp);
        modules.add(xray);
        modules.add(boatFly);

        // 3. Menü tuşunu tanımla (Sağ Shift)
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.warden.menu", 
                InputUtil.Type.KEYSYM, 
                GLFW.GLFW_KEY_RIGHT_SHIFT, 
                "Warden Client"
        ));

        // 4. HUD (Ekranda yazıların görünmesi) için kayıt
        HudRenderCallback.EVENT.register(HudRender::render);

        // 5. ANA DÖNGÜ (Hilelerin her an çalışmasını sağlar)
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;

            // Menü açma kontrolü
            while (menuKey.wasPressed()) {
                client.setScreen(new WardenMenuScreen());
            }

            // Aktif olan modülleri çalıştır
            for (Mod m : modules) {
                if (m.enabled) {
                    try {
                        m.onTick();
                    } catch (Exception e) {
                        // Bir modül hata verirse client çökmesin diye logla
                        System.err.println("[Warden] Modül çalışırken hata oluştu: " + m.name);
                    }
                }
            }
        });
    }
}
