package io.github.dogeiscut.sag.registry;

import net.neoforged.neoforge.common.ModConfigSpec;

public class SagConfig {
    public static final ModConfigSpec SERVER_SPEC;
    public static final ModConfigSpec.IntValue BEDROCK_BUSTER_MIN_SPEED;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        BEDROCK_BUSTER_MIN_SPEED = builder
                .comment("Minimum RPM the Bedrock Buster must be spinning at before it starts charging.")
                .defineInRange("bedrockBusterMinSpeed", 256, 64, Integer.MAX_VALUE);
        SERVER_SPEC = builder.build();
    }
}