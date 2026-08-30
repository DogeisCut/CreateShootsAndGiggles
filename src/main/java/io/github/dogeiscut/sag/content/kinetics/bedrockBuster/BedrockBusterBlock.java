package io.github.dogeiscut.sag.content.kinetics.bedrockBuster;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import io.github.dogeiscut.sag.registry.SagBlockEntityTypes;
import net.createmod.catnip.animation.LerpedFloat;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


import java.util.List;

import net.minecraft.network.chat.Component;

public class BedrockBusterBlock extends DirectionalKineticBlock implements IBE<BedrockBusterBlockEntity>, ICogWheel, IHaveGoggleInformation {

    public BedrockBusterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING).getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, net.minecraft.core.BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(FACING).getOpposite();
    }

    @Override
    public Class<BedrockBusterBlockEntity> getBlockEntityClass() {
        return BedrockBusterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends BedrockBusterBlockEntity> getBlockEntityType() {
        return SagBlockEntityTypes.BEDROCK_BUSTER.get();
    }

    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {

//        LangBuilder.translate("tooltip.laser.strength", "fart")
//                .style(ChatFormatting.GREEN)
//                .forGoggles(tooltip);

        return true;
    }
}