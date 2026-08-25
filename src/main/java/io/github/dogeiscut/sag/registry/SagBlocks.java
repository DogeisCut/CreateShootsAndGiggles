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

    public static final BlockEntry<IceBlock> white_stained_ice = iceBlock("white_stained_ice", "White Stained Ice");
    public static final BlockEntry<IceBlock> light_gray_stained_ice = iceBlock("light_gray_stained_ice", "Light Gray Stained Ice");
    public static final BlockEntry<IceBlock> gray_stained_ice = iceBlock("gray_stained_ice", "Gray Stained Ice");
    public static final BlockEntry<IceBlock> black_stained_ice = iceBlock("black_stained_ice", "Black Stained Ice");
    public static final BlockEntry<IceBlock> brown_stained_ice = iceBlock("brown_stained_ice", "Brown Stained Ice");
    public static final BlockEntry<IceBlock> red_stained_ice = iceBlock("red_stained_ice", "Red Stained Ice");
    public static final BlockEntry<IceBlock> orange_stained_ice = iceBlock("orange_stained_ice", "Orange Stained Ice");
    public static final BlockEntry<IceBlock> yellow_stained_ice = iceBlock("yellow_stained_ice", "Yellow Stained Ice");
    public static final BlockEntry<IceBlock> lime_stained_ice = iceBlock("lime_stained_ice", "Lime Stained Ice");
    public static final BlockEntry<IceBlock> green_stained_ice = iceBlock("green_stained_ice", "Green Stained Ice");
    public static final BlockEntry<IceBlock> cyan_stained_ice = iceBlock("cyan_stained_ice", "Cyan Stained Ice");
    public static final BlockEntry<IceBlock> light_blue_stained_ice = iceBlock("light_blue_stained_ice", "Light Blue Stained Ice");
    public static final BlockEntry<IceBlock> blue_stained_ice = iceBlock("blue_stained_ice", "Blue Stained Ice");
    public static final BlockEntry<IceBlock> purple_stained_ice = iceBlock("purple_stained_ice", "Purple Stained Ice");
    public static final BlockEntry<IceBlock> magenta_stained_ice = iceBlock("magenta_stained_ice", "Magenta Stained Ice");
    public static final BlockEntry<IceBlock> pink_stained_ice = iceBlock("pink_stained_ice", "Pink Stained Ice");

    public static final BlockEntry<Block> white_stained_packed_ice = packedIceBlock("white_stained_packed_ice", "White Stained Packed Ice");
    public static final BlockEntry<Block> light_gray_stained_packed_ice = packedIceBlock("light_gray_stained_packed_ice", "Light Gray Stained Packed Ice");
    public static final BlockEntry<Block> gray_stained_packed_ice = packedIceBlock("gray_stained_packed_ice", "Gray Stained Packed Ice");
    public static final BlockEntry<Block> black_stained_packed_ice = packedIceBlock("black_stained_packed_ice", "Black Stained Packed Ice");
    public static final BlockEntry<Block> brown_stained_packed_ice = packedIceBlock("brown_stained_packed_ice", "Brown Stained Packed Ice");
    public static final BlockEntry<Block> red_stained_packed_ice = packedIceBlock("red_stained_packed_ice", "Red Stained Packed Ice");
    public static final BlockEntry<Block> orange_stained_packed_ice = packedIceBlock("orange_stained_packed_ice", "Orange Stained Packed Ice");
    public static final BlockEntry<Block> yellow_stained_packed_ice = packedIceBlock("yellow_stained_packed_ice", "Yellow Stained Packed Ice");
    public static final BlockEntry<Block> lime_stained_packed_ice = packedIceBlock("lime_stained_packed_ice", "Lime Stained Packed Ice");
    public static final BlockEntry<Block> green_stained_packed_ice = packedIceBlock("green_stained_packed_ice", "Green Stained Packed Ice");
    public static final BlockEntry<Block> cyan_stained_packed_ice = packedIceBlock("cyan_stained_packed_ice", "Cyan Stained Packed Ice");
    public static final BlockEntry<Block> light_blue_stained_packed_ice = packedIceBlock("light_blue_stained_packed_ice", "Light Blue Stained Packed Ice");
    public static final BlockEntry<Block> blue_stained_packed_ice = packedIceBlock("blue_stained_packed_ice", "Blue Stained Packed Ice");
    public static final BlockEntry<Block> purple_stained_packed_ice = packedIceBlock("purple_stained_packed_ice", "Purple Stained Packed Ice");
    public static final BlockEntry<Block> magenta_stained_packed_ice = packedIceBlock("magenta_stained_packed_ice", "Magenta Stained Packed Ice");
    public static final BlockEntry<Block> pink_stained_packed_ice = packedIceBlock("pink_stained_packed_ice", "Pink Stained Packed Ice");

    public static final BlockEntry<Block> white_stained_blue_ice = blueIceBlock("white_stained_blue_ice", "White Stained Blue Ice");
    public static final BlockEntry<Block> light_gray_stained_blue_ice = blueIceBlock("light_gray_stained_blue_ice", "Light Gray Stained Blue Ice");
    public static final BlockEntry<Block> gray_stained_blue_ice = blueIceBlock("gray_stained_blue_ice", "Gray Stained Blue Ice");
    public static final BlockEntry<Block> black_stained_blue_ice = blueIceBlock("black_stained_blue_ice", "Black Stained Blue Ice");
    public static final BlockEntry<Block> brown_stained_blue_ice = blueIceBlock("brown_stained_blue_ice", "Brown Stained Blue Ice");
    public static final BlockEntry<Block> red_stained_blue_ice = blueIceBlock("red_stained_blue_ice", "Red Stained Blue Ice");
    public static final BlockEntry<Block> orange_stained_blue_ice = blueIceBlock("orange_stained_blue_ice", "Orange Stained Blue Ice");
    public static final BlockEntry<Block> yellow_stained_blue_ice = blueIceBlock("yellow_stained_blue_ice", "Yellow Stained Blue Ice");
    public static final BlockEntry<Block> lime_stained_blue_ice = blueIceBlock("lime_stained_blue_ice", "Lime Stained Blue Ice");
    public static final BlockEntry<Block> green_stained_blue_ice = blueIceBlock("green_stained_blue_ice", "Green Stained Blue Ice");
    public static final BlockEntry<Block> cyan_stained_blue_ice = blueIceBlock("cyan_stained_blue_ice", "Cyan Stained Blue Ice");
    public static final BlockEntry<Block> light_blue_stained_blue_ice = blueIceBlock("light_blue_stained_blue_ice", "Light Blue Stained Blue Ice");
    public static final BlockEntry<Block> blue_stained_blue_ice = blueIceBlock("blue_stained_blue_ice", "Blue Stained Blue Ice");
    public static final BlockEntry<Block> purple_stained_blue_ice = blueIceBlock("purple_stained_blue_ice", "Purple Stained Blue Ice");
    public static final BlockEntry<Block> magenta_stained_blue_ice = blueIceBlock("magenta_stained_blue_ice", "Magenta Stained Blue Ice");
    public static final BlockEntry<Block> pink_stained_blue_ice = blueIceBlock("pink_stained_blue_ice", "Pink Stained Blue Ice");

    public static final BlockEntry<Block> pykrete =  REGISTRATE.block("pykrete", Block::new)
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
                .blockstate(simpleCubeAll(id))
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
                .blockstate(simpleCubeAll(id))
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
                .blockstate(simpleCubeAll(id))
                .simpleItem()
                .lang(name)
                .register();
    }

    public static void register() {
    }
}
