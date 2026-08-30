package io.github.dogeiscut.sag.content.kinetics.bedrockBuster;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Marker;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class BedrockBusterEvents {
    public static Marker MARKER;

    @SubscribeEvent
    public static void onDetonate(ExplosionEvent.Detonate event) {
        if (MARKER == null || event.getExplosion().getDirectSourceEntity() != MARKER)
            return;

        Level level = event.getLevel();
        List<BlockPos> affected = new ArrayList<>(event.getAffectedBlocks());
        event.getAffectedBlocks().clear();

        for (BlockPos pos : affected) {
            if (level.isEmptyBlock(pos))
                continue;
            level.destroyBlock(pos, false);
        }
    }
}