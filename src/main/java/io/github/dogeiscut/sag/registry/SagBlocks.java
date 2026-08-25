package io.github.dogeiscut.sag.registry;

import static com.simibubi.create.foundation.data.BlockStateGen.*;
import static com.simibubi.create.foundation.data.TagGen.*;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.ryanhcode.sable.companion.SableCompanion;
import dev.ryanhcode.sable.index.SableTags;
import io.github.dogeiscut.sag.Sag;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;

public class SagBlocks {
    private static final CreateRegistrate REGISTRATE = Sag.registrate();

    static {
        REGISTRATE.setCreativeTab(SagCreativeModeTabs.ICE_TAB);
    }

    public static final BlockEntry<IceBlock> WHITE_STAINED_ICE = iceBlock("white_stained_ice", "White Stained Ice");
    public static final BlockEntry<IceBlock> LIGHT_GRAY_STAINED_ICE = iceBlock("light_gray_stained_ice", "Light Gray Stained Ice");
    public static final BlockEntry<IceBlock> GRAY_STAINED_ICE = iceBlock("gray_stained_ice", "Gray Stained Ice");
    public static final BlockEntry<IceBlock> BLACK_STAINED_ICE = iceBlock("black_stained_ice", "Black Stained Ice");
    public static final BlockEntry<IceBlock> BROWN_STAINED_ICE = iceBlock("brown_stained_ice", "Brown Stained Ice");
    public static final BlockEntry<IceBlock> RED_STAINED_ICE = iceBlock("red_stained_ice", "Red Stained Ice");
    public static final BlockEntry<IceBlock> ORANGE_STAINED_ICE = iceBlock("orange_stained_ice", "Orange Stained Ice");
    public static final BlockEntry<IceBlock> YELLOW_STAINED_ICE = iceBlock("yellow_stained_ice", "Yellow Stained Ice");
    public static final BlockEntry<IceBlock> LIME_STAINED_ICE = iceBlock("lime_stained_ice", "Lime Stained Ice");
    public static final BlockEntry<IceBlock> GREEN_STAINED_ICE = iceBlock("green_stained_ice", "Green Stained Ice");
    public static final BlockEntry<IceBlock> CYAN_STAINED_ICE = iceBlock("cyan_stained_ice", "Cyan Stained Ice");
    public static final BlockEntry<IceBlock> LIGHT_BLUE_STAINED_ICE = iceBlock("light_blue_stained_ice", "Light Blue Stained Ice");
    public static final BlockEntry<IceBlock> BLUE_STAINED_ICE = iceBlock("blue_stained_ice", "Blue Stained Ice");
    public static final BlockEntry<IceBlock> PURPLE_STAINED_ICE = iceBlock("purple_stained_ice", "Purple Stained Ice");
    public static final BlockEntry<IceBlock> MAGENTA_STAINED_ICE = iceBlock("magenta_stained_ice", "Magenta Stained Ice");
    public static final BlockEntry<IceBlock> PINK_STAINED_ICE = iceBlock("pink_stained_ice", "Pink Stained Ice");

    public static final BlockEntry<Block> WHITE_STAINED_PACKED_ICE = packedIceBlock("white_stained_packed_ice", "White Stained Packed Ice");
    public static final BlockEntry<Block> LIGHT_GRAY_STAINED_PACKED_ICE = packedIceBlock("light_gray_stained_packed_ice", "Light Gray Stained Packed Ice");
    public static final BlockEntry<Block> GRAY_STAINED_PACKED_ICE = packedIceBlock("gray_stained_packed_ice", "Gray Stained Packed Ice");
    public static final BlockEntry<Block> BLACK_STAINED_PACKED_ICE = packedIceBlock("black_stained_packed_ice", "Black Stained Packed Ice");
    public static final BlockEntry<Block> BROWN_STAINED_PACKED_ICE = packedIceBlock("brown_stained_packed_ice", "Brown Stained Packed Ice");
    public static final BlockEntry<Block> RED_STAINED_PACKED_ICE = packedIceBlock("red_stained_packed_ice", "Red Stained Packed Ice");
    public static final BlockEntry<Block> ORANGE_STAINED_PACKED_ICE = packedIceBlock("orange_stained_packed_ice", "Orange Stained Packed Ice");
    public static final BlockEntry<Block> YELLOW_STAINED_PACKED_ICE = packedIceBlock("yellow_stained_packed_ice", "Yellow Stained Packed Ice");
    public static final BlockEntry<Block> LIME_STAINED_PACKED_ICE = packedIceBlock("lime_stained_packed_ice", "Lime Stained Packed Ice");
    public static final BlockEntry<Block> GREEN_STAINED_PACKED_ICE = packedIceBlock("green_stained_packed_ice", "Green Stained Packed Ice");
    public static final BlockEntry<Block> CYAN_STAINED_PACKED_ICE = packedIceBlock("cyan_stained_packed_ice", "Cyan Stained Packed Ice");
    public static final BlockEntry<Block> LIGHT_BLUE_STAINED_PACKED_ICE = packedIceBlock("light_blue_stained_packed_ice", "Light Blue Stained Packed Ice");
    public static final BlockEntry<Block> BLUE_STAINED_PACKED_ICE = packedIceBlock("blue_stained_packed_ice", "Blue Stained Packed Ice");
    public static final BlockEntry<Block> PURPLE_STAINED_PACKED_ICE = packedIceBlock("purple_stained_packed_ice", "Purple Stained Packed Ice");
    public static final BlockEntry<Block> MAGENTA_STAINED_PACKED_ICE = packedIceBlock("magenta_stained_packed_ice", "Magenta Stained Packed Ice");
    public static final BlockEntry<Block> PINK_STAINED_PACKED_ICE = packedIceBlock("pink_stained_packed_ice", "Pink Stained Packed Ice");

    public static final BlockEntry<Block> WHITE_STAINED_BLUE_ICE = blueIceBlock("white_stained_blue_ice", "White Stained Blue Ice");
    public static final BlockEntry<Block> LIGHT_GRAY_STAINED_BLUE_ICE = blueIceBlock("light_gray_stained_blue_ice", "Light Gray Stained Blue Ice");
    public static final BlockEntry<Block> GRAY_STAINED_BLUE_ICE = blueIceBlock("gray_stained_blue_ice", "Gray Stained Blue Ice");
    public static final BlockEntry<Block> BLACK_STAINED_BLUE_ICE = blueIceBlock("black_stained_blue_ice", "Black Stained Blue Ice");
    public static final BlockEntry<Block> BROWN_STAINED_BLUE_ICE = blueIceBlock("brown_stained_blue_ice", "Brown Stained Blue Ice");
    public static final BlockEntry<Block> RED_STAINED_BLUE_ICE = blueIceBlock("red_stained_blue_ice", "Red Stained Blue Ice");
    public static final BlockEntry<Block> ORANGE_STAINED_BLUE_ICE = blueIceBlock("orange_stained_blue_ice", "Orange Stained Blue Ice");
    public static final BlockEntry<Block> YELLOW_STAINED_BLUE_ICE = blueIceBlock("yellow_stained_blue_ice", "Yellow Stained Blue Ice");
    public static final BlockEntry<Block> LIME_STAINED_BLUE_ICE = blueIceBlock("lime_stained_blue_ice", "Lime Stained Blue Ice");
    public static final BlockEntry<Block> GREEN_STAINED_BLUE_ICE = blueIceBlock("green_stained_blue_ice", "Green Stained Blue Ice");
    public static final BlockEntry<Block> CYAN_STAINED_BLUE_ICE = blueIceBlock("cyan_stained_blue_ice", "Cyan Stained Blue Ice");
    public static final BlockEntry<Block> LIGHT_BLUE_STAINED_BLUE_ICE = blueIceBlock("light_blue_stained_blue_ice", "Light Blue Stained Blue Ice");
    public static final BlockEntry<Block> BLUE_STAINED_BLUE_ICE = blueIceBlock("blue_stained_blue_ice", "Blue Stained Blue Ice");
    public static final BlockEntry<Block> PURPLE_STAINED_BLUE_ICE = blueIceBlock("purple_stained_blue_ice", "Purple Stained Blue Ice");
    public static final BlockEntry<Block> MAGENTA_STAINED_BLUE_ICE = blueIceBlock("magenta_stained_blue_ice", "Magenta Stained Blue Ice");
    public static final BlockEntry<Block> PINK_STAINED_BLUE_ICE = blueIceBlock("pink_stained_blue_ice", "Pink Stained Blue Ice");

    static {
        REGISTRATE.setCreativeTab(SagCreativeModeTabs.MAIN_TAB);
    }

    public static final BlockEntry<Block> PYKRETE =  REGISTRATE.block("pykrete", Block::new)
            .initialProperties(() -> Blocks.PACKED_ICE)
            .properties(p -> p.strength(4, 4)
                    .friction(0.88f))
            .loot(RegistrateBlockLootTables::dropWhenSilkTouch)
            .transform(pickaxeOnly())
            .tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("sable", "slippery")))
            .blockstate(simpleCubeAll("pykrete"))
            .simpleItem()
            .lang("Pykrete")
            .register();

    private static BlockEntry<IceBlock> iceBlock(String id, String name) {
        return REGISTRATE.block(id, IceBlock::new)
                .initialProperties(() -> Blocks.ICE)
                .loot(RegistrateBlockLootTables::dropWhenSilkTouch)
                .addLayer(() -> RenderType::translucent)
                .transform(pickaxeOnly())
                .tag(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON)
                .tag(BlockTags.GEODE_INVALID_BLOCKS)
                .tag(BlockTags.ICE)
                .tag(BlockTags.POLAR_BEARS_SPAWNABLE_ON_ALTERNATE)
                .tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("sable", "slippery")))
                .tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("sable", "fragile")))
                .tag()
                .blockstate(simpleCubeAll("stained_ice/" + id))
                .simpleItem()
                .lang(name)
                .register();
    }

    private static BlockEntry<Block> packedIceBlock(String id, String name) {
        return REGISTRATE.block(id, Block::new)
                .initialProperties(() -> Blocks.PACKED_ICE)
                .loot(RegistrateBlockLootTables::dropWhenSilkTouch)
                .transform(pickaxeOnly())
                // TODO: confirm these tags
                .tag(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON)
                .tag(BlockTags.GEODE_INVALID_BLOCKS)
                .tag(BlockTags.ICE)
                .tag(BlockTags.POLAR_BEARS_SPAWNABLE_ON_ALTERNATE)
                .tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("sable", "slippery")))
                .blockstate(simpleCubeAll("stained_packed_ice/" + id))
                .simpleItem()
                .lang(name)
                .register();
    }

    private static BlockEntry<Block> blueIceBlock(String id, String name) {
        return REGISTRATE.block(id, Block::new)
                .initialProperties(() -> Blocks.BLUE_ICE)
                .loot(RegistrateBlockLootTables::dropWhenSilkTouch)
                .transform(pickaxeOnly())
                // TODO: confirm these tags
                .tag(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON)
                .tag(BlockTags.GEODE_INVALID_BLOCKS)
                .tag(BlockTags.ICE)
                .tag(BlockTags.POLAR_BEARS_SPAWNABLE_ON_ALTERNATE)
                .tag(TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("sable", "slippery")))
                .blockstate(simpleCubeAll("stained_blue_ice/" + id))
                .simpleItem()
                .lang(name)
                .register();
    }

    public static void register() {
    }
}
