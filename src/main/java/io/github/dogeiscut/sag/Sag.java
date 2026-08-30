package io.github.dogeiscut.sag;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import io.github.dogeiscut.sag.content.kinetics.bedrockBuster.BedrockBusterEvents;
import io.github.dogeiscut.sag.registry.*;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

@Mod(Sag.ID)
public class Sag {
    public static final String ID = "create_sag";
    public static final String NAME = "Create: Shoots and Giggles";

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID)
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

        modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.SERVER, SagConfig.SERVER_SPEC);

        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.register(BedrockBusterEvents.class);

        SagCreativeModeTabs.register(modEventBus);
        SagDataComponents.register(modEventBus);

        SagSoundEvents.prepare();
        SagItems.register();
        SagBlocks.register();
        SagPackets.register();
        SagBlockEntityTypes.register();

        modEventBus.addListener(SagSoundEvents::register);
    }

    public static void gatherData(GatherDataEvent event) {
        if (!event.getMods().contains(ID))
            return;

        ExistingFileHelper helper = event.getExistingFileHelper();

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();

        generator.addProvider(event.includeClient(), SagSoundEvents.provider(output));
        generator.addProvider(event.includeClient(), new LanguageProvider(output, Sag.ID, "en_us") {
            @Override
            protected void addTranslations() {
                //SagSoundEvents.provideLang(this::add);

                add("item.create_sag.handheld_air_blower.tooltip.summary", "...");
                add("item.create_sag.handheld_air_blower.tooltip.condition1", "R-Click");
                add("item.create_sag.handheld_air_blower.tooltip.behaviour1", "...");
            }
        });
    }

    public static CreateRegistrate registrate() {
        if (!STACK_WALKER.getCallerClass().getPackageName().startsWith("io.github.dogeiscut"))
            throw new UnsupportedOperationException("Other mods are not permitted to use Create: Shoots and Giggles' registrate instance.");
        return REGISTRATE;
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}
