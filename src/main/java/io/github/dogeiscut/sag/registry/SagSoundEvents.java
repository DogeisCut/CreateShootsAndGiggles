package io.github.dogeiscut.sag.registry;

import com.google.gson.JsonObject;
import com.simibubi.create.AllSoundEvents.SoundEntry;
import com.simibubi.create.AllSoundEvents.SoundEntryBuilder;
import io.github.dogeiscut.sag.Sag;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SagSoundEvents {

    public static final Map<ResourceLocation, SoundEntry> ALL = new HashMap<>();

    public static final SoundEntry
            AIR_BLOWER_BLOW = create("air_blower_blow")
            .subtitle("Air blower blows")
            .playExisting(SoundEvents.BREEZE_IDLE_GROUND, 0.9f, 1.3f)
            .playExisting(SoundEvents.BREEZE_IDLE_GROUND, 1.1f, 2.0f)
            .category(SoundSource.PLAYERS)
            .build(),

    AIR_BLOWER_CHARGE_LIGHT = create("air_blower_charge_light")
            .subtitle("Air blower charges")
            .playExisting(SoundEvents.BREEZE_WIND_CHARGE_BURST.value(), 0.3f, 1.5f)
            .category(SoundSource.PLAYERS)
            .build(),

    AIR_BLOWER_CHARGE_MEDIUM = create("air_blower_charge_medium")
            .subtitle("Air blower charged")
            .playExisting(SoundEvents.BREEZE_WIND_CHARGE_BURST.value(), 0.5f, 1.2f)
            .category(SoundSource.PLAYERS)
            .build(),

    AIR_BLOWER_CHARGE_HEAVY = create("air_blower_charge_heavy")
            .subtitle("Air blower overcharging")
            .playExisting(SoundEvents.WIND_CHARGE_BURST.value(), 0.6f, 0.6f)
            .category(SoundSource.PLAYERS)
            .build(),

    AIR_BLOWER_SHOOT = create("air_blower_shoot")
            .subtitle("Air blower shoots wind charge")
            .playExisting(SoundEvents.WIND_CHARGE_BURST.value(), 0.9f, 1.2f)
            .playExisting(SoundEvents.WIND_CHARGE_THROW, 0.9f, 0.8f)
            .category(SoundSource.PLAYERS)
            .build(),

    AIR_BLOWER_EXPLODE = create("air_blower_explode")
            .subtitle("Air blower backfires")
            .playExisting(SoundEvents.WIND_CHARGE_BURST.value(), 1.0f, 0.5f)
            .playExisting(SoundEvents.GENERIC_EXPLODE.value(), 0.5f, 1.2f)
            .category(SoundSource.PLAYERS)
            .build();

    public static SagSoundEntryBuilder create(String name) {
        return new SagSoundEntryBuilder(Sag.asResource(name));
    }

    public static void register(RegisterEvent event) {
        event.register(Registries.SOUND_EVENT, helper -> {
            for (SoundEntry entry : ALL.values())
                entry.register(helper);
        });
    }

    public static void prepare() {
        for (SoundEntry entry : ALL.values())
            entry.prepare();
    }

    public static class SagSoundEntryBuilder extends SoundEntryBuilder {
        public SagSoundEntryBuilder(ResourceLocation id) {
            super(id);
        }

        @Override
        public SoundEntryBuilder addVariant(String name) {
            return this.addVariant(Sag.asResource(name));
        }

        @Override
        public SoundEntry build() {
            SoundEntry entry = super.build();
            ALL.put(entry.getId(), entry);
            return entry;
        }
    }

    public static SoundEntryProvider provider(PackOutput output) {
        return new SoundEntryProvider(output);
    }

    public static class SoundEntryProvider implements DataProvider {
        private PackOutput output;

        public SoundEntryProvider(PackOutput output) {
            this.output = output;
        }

        @Override
        public CompletableFuture<?> run(CachedOutput cache) {
            Path path = this.output.getOutputFolder().resolve("assets/" + Sag.MODID);
            JsonObject json = new JsonObject();
            ALL.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> entry.getValue().write(json));
            return DataProvider.saveStable(cache, json, path.resolve("sounds.json"));
        }

        @Override public String getName() { return "Create: Shoots and Giggles custom sounds"; }
    }
}