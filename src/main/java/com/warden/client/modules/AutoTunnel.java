package com.warden.client.modules;

import com.warden.client.modules.Mod.Category;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;

public class AutoTunnel extends Mod {
    public AutoTunnel() { 
        super("AutoTunnel", Category.WORLD); 
    }

    @Override
    public void onTick() {
        // nullCheck Mod.java'da tanımlı olmalı. 
        // 1.21.4'te 'movementForward' 0'dan büyükse W tuşuna basılıyor demektir.
        if (nullCheck() || mc.player.input.movementForward <= 0) return;

        // Oyuncunun baktığı yönü al (Kuzey, Güney, Doğu, Batı)
        Direction facing = mc.player.getHorizontalFacing();

        // Tam önümüzdeki 2 bloğu (Ayak ve Kafa) hedefliyoruz
        BlockPos[] positions = {
            mc.player.getBlockPos().offset(facing),      // Ayak hizası
            mc.player.getBlockPos().offset(facing).up()   // Kafa hizası
        };

        for (BlockPos pos : positions) {
            // Eğer blok zaten havaysa veya kırılamaz bir bloksa (Bedrock vb.) atla
            if (mc.world.getBlockState(pos).isAir() || mc.world.getBlockState(pos).getHardness(mc.world, pos) < 0) continue;

            // Blok kırma işlemini başlat/devam ettir
            // getOpposite() yönü, bloğun hangi yüzüne vurduğumuzu simüle eder
            mc.interactionManager.updateBlockBreakingProgress(pos, facing.getOpposite());
            
            // El sallama animasyonu
            mc.player.swingHand(Hand.MAIN_HAND);
        }
    }
}
