package io.github.dogeiscut.sag.registry;

import com.simibubi.create.content.kinetics.base.OrientedRotatingVisual;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import io.github.dogeiscut.sag.Sag;
import io.github.dogeiscut.sag.content.kinetics.bedrockBuster.BedrockBusterBlockEntity;
import io.github.dogeiscut.sag.content.kinetics.bedrockBuster.BedrockBusterRenderer;
import io.github.dogeiscut.sag.content.kinetics.bedrockBuster.BedrockBusterVisual;
import io.github.dogeiscut.sag.content.logistics.freeformTransferTube.FreeformTransferTubeBlockEntity;
import net.minecraft.client.renderer.entity.NoopRenderer;

public class SagBlockEntityTypes {
    private static final CreateRegistrate REGISTRATE = Sag.registrate();

    public static final BlockEntityEntry<BedrockBusterBlockEntity> BEDROCK_BUSTER = REGISTRATE
            .blockEntity("bedrock_buster", BedrockBusterBlockEntity::new)
            .visual(() -> BedrockBusterVisual::new, false)
            .renderer(() -> BedrockBusterRenderer::new)
            .validBlocks(SagBlocks.BEDROCK_BUSTER)
            .register();

    public static final BlockEntityEntry<FreeformTransferTubeBlockEntity> FREEFORM_TRANSFER_TUBE = REGISTRATE
            .blockEntity("freeform_transfer_tube", FreeformTransferTubeBlockEntity::new)
            .validBlocks(SagBlocks.FREEFORM_TRANSFER_TUBE)
            .register();

    public static void register() {
    }
}