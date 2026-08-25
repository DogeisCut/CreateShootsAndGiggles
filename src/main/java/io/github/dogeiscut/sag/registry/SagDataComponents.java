package io.github.dogeiscut.sag.registry;

import com.mojang.serialization.Codec;
import io.github.dogeiscut.sag.Sag;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

import net.minecraft.core.component.DataComponentType.Builder;

public class SagDataComponents {
    private static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Sag.ID);

    public static final DataComponentType<Boolean> WAS_CROUCHING =
            register("was_crouching", b -> b.persistent(Codec.BOOL));

    private static <T> DataComponentType<T> register(String name, UnaryOperator<Builder<T>> builder) {
        DataComponentType<T> type = builder.apply(DataComponentType.builder()).build();
        DATA_COMPONENTS.register(name, () -> type);
        return type;
    }

    public static void register(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }
}
