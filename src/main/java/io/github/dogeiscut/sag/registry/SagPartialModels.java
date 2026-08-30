package io.github.dogeiscut.sag.registry;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import io.github.dogeiscut.sag.Sag;

public class SagPartialModels {

    public static final PartialModel BEDROCK_BUSTER_COG = block("bedrock_buster/cog");
    public static final PartialModel BEDROCK_BUSTER_CLAW = block("bedrock_buster/claw");

    private static PartialModel block(String path) {
        return PartialModel.of(Sag.asResource("block/" + path));
    }

    public static void init() {
    }
}
