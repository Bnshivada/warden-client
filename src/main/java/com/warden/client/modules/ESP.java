package com.warden.client.modules;

public class ESP extends Mod {
    public ESP() { super("ESP", Category.RENDER); }

    @Override
    public void onTick() {
        if (mc.world == null) return;
        mc.world.getPlayers().forEach(player -> {
            if (player != mc.player) player.setGlowing(enabled);
        });
    }

    @Override
    public void onDisable() {
        if (mc.world != null) {
            mc.world.getPlayers().forEach(p -> p.setGlowing(false));
        }
    }
}
