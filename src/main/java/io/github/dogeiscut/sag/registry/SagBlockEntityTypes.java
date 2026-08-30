package io.github.dogeiscut.sag.registry;

import com.simibubi.create.content.kinetics.base.OrientedRotatingVisual;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import io.github.dogeiscut.sag.Sag;
import io.github.dogeiscut.sag.content.kinetics.bedrockBuster.BedrockBusterBlockEntity;
import io.github.dogeiscut.sag.content.kinetics.bedrockBuster.BedrockBusterRenderer;
import io.github.dogeiscut.sag.content.kinetics.bedrockBuster.BedrockBusterVisual;

public class SagBlockEntityTypes {
    private static final CreateRegistrate REGISTRATE = Sag.registrate();

    public static final BlockEntityEntry<BedrockBusterBlockEntity> BEDROCK_BUSTER = REGISTRATE
            .blockEntity("bedrock_buster", BedrockBusterBlockEntity::new)
            .visual(() -> BedrockBusterVisual::new, false)
            .renderer(() -> BedrockBusterRenderer::new)
            .validBlocks(SagBlocks.BEDROCK_BUSTER)
            .register();

    public static void register() {
    }
}