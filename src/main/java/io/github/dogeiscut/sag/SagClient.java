package io.github.dogeiscut.sag;

import io.github.dogeiscut.sag.content.equipment.handheldAirBlower.HandheldAirBlowerRenderHandler;
import io.github.dogeiscut.sag.registry.SagPartialModels;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = Sag.MODID, dist = Dist.CLIENT)
public class SagClient {
    public SagClient(IEventBus modEventBus) {
        onCtorClient(modEventBus);
    }

    public static final HandheldAirBlowerRenderHandler BLOWER_RENDER_HANDLER = new HandheldAirBlowerRenderHandler();
    public static void onCtorClient(IEventBus modEventBus) {
        IEventBus neoEventBus = NeoForge.EVENT_BUS;

        BLOWER_RENDER_HANDLER.registerListeners(neoEventBus);

        modEventBus.addListener(SagClient::clientInit);
    }

    public static void clientInit(final FMLClientSetupEvent event) {
        SagPartialModels.init();
    }
}
