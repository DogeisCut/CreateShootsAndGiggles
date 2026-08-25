package io.github.dogeiscut.sag.registry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.github.dogeiscut.sag.Sag;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SagCreativeModeTabs {
    private static final DeferredRegister<CreativeModeTab> REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Sag.ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = REGISTER.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.create_sag.main"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(() -> SagItems.HANDHELD_AIR_BLOWER.asStack())
                    .displayItems(new SimpleTabGenerator(SagCreativeModeTabs.MAIN_TAB))  // qualified
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ICE_TAB = REGISTER.register("ice",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.create_sag.ice"))
                    .withTabsBefore(SagCreativeModeTabs.MAIN_TAB.getKey())              // qualified
                    .icon(() -> SagBlocks.RED_STAINED_ICE.asStack())
                    .displayItems(new SimpleTabGenerator(SagCreativeModeTabs.ICE_TAB))  // qualified
                    .build());

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }

    private record SimpleTabGenerator(DeferredHolder<CreativeModeTab, CreativeModeTab> tab) implements CreativeModeTab.DisplayItemsGenerator {
        @Override
        public void accept(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
            for (RegistryEntry<Item, Item> entry : Sag.registrate().getAll(Registries.ITEM))
                if (CreateRegistrate.isInCreativeTab(entry, tab))
                    output.accept(entry.get());
            for (RegistryEntry<Block, Block> entry : Sag.registrate().getAll(Registries.BLOCK)) {
                if (CreateRegistrate.isInCreativeTab(entry, tab)) {
                    Item item = entry.get().asItem();
                    if (item != Items.AIR)
                        output.accept(item);
                }
            }
        }
    }
}