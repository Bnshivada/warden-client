package com.warden.client.modules;

public class Xray extends Mod {
    public Xray() { super("Xray", Category.RENDER); }

    public static boolean isXrayEnabled = false;

    @Override
    public void onEnable() {
        isXrayEnabled = true;
        mc.options.getGamma().setValue(16.0); 
        if (mc.worldRenderer != null) mc.worldRenderer.reload();
    }

    @Override
    public void onDisable() {
        isXrayEnabled = false;
        mc.options.getGamma().setValue(1.0);
        if (mc.worldRenderer != null) mc.worldRenderer.reload();
    }
}
