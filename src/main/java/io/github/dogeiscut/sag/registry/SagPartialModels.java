package io.github.dogeiscut.sag.registry;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import io.github.dogeiscut.sag.Sag;

public class SagPartialModels {

    private static PartialModel block(String path) {
        return PartialModel.of(Sag.asResource("block/" + path));
    }

    public static void init() {
        // init static fields
    }
}
