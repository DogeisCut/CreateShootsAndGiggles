package io.github.dogeiscut.sag.registry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import io.github.dogeiscut.sag.Sag;
import io.github.dogeiscut.sag.content.kinetics.bedrockBuster.BedrockBusterBlockEntity;
import io.github.dogeiscut.sag.content.kinetics.bedrockBuster.BedrockBusterRenderer;

public class SagBlockEntityTypes {
    private static final CreateRegistrate REGISTRATE = Sag.registrate();

    public static final BlockEntityEntry<BedrockBusterBlockEntity> BEDROCK_BUSTER = REGISTRATE
            .blockEntity("bedrock_buster", BedrockBusterBlockEntity::new)
            .renderer(() -> BedrockBusterRenderer::new)
            .validBlocks(SagBlocks.BEDROCK_BUSTER)
            .register();

    public static void register() {
    }
}