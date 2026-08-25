package io.github.dogeiscut.sag;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import io.github.dogeiscut.sag.registry.SagBlocks;
import io.github.dogeiscut.sag.registry.SagItems;
import io.github.dogeiscut.sag.registry.SagSoundEvents;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

@Mod(Sag.MODID)
public class Sag {
    public static final String MODID = "create_sag";
    public static final String NAME = "Create: Shoots and Giggles";

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );

    public Sag(IEventBus modEventBus, ModContainer modContainer) {
        onCtor(modEventBus, modContainer);
    }

    public static void onCtor(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("{} initializing!", NAME);
        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        REGISTRATE.registerEventListeners(modEventBus);
        modEventBus.addListener(Sag::gatherData);

        SagSoundEvents.prepare();
        SagItems.register();
        SagBlocks.register();

        modEventBus.addListener(SagSoundEvents::register);
    }

    public static void gatherData(GatherDataEvent evt) {
        if (!evt.getMods().contains(MODID))
            return;

        ExistingFileHelper helper = evt.getExistingFileHelper();

        DataGenerator generator = evt.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> registries = evt.getLookupProvider();

        generator.addProvider(evt.includeClient(), SagSoundEvents.provider(output));
    }

    public static CreateRegistrate registrate() {
        if (!STACK_WALKER.getCallerClass().getPackageName().startsWith("io.github.dogeiscut"))
            throw new UnsupportedOperationException("Other mods are not permitted to use Create: Shoots and Giggles' registrate instance.");
        return REGISTRATE;
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
