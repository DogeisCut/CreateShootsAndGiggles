package io.github.dogeiscut.sag.content.logistics.freeformTransferTube;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import io.github.dogeiscut.sag.registry.SagBlockEntityTypes;
import io.github.dogeiscut.sag.registry.SagBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FreeformTransferTubeBlock extends Block implements IWrenchable, IBE<FreeformTransferTubeBlockEntity> {

    public static final EnumProperty<OptionalDirection> PRIMARY_CONNECTION = EnumProperty.create("primary_connection", OptionalDirection.class);
    public static final EnumProperty<OptionalDirection> SECONDARY_CONNECTION = EnumProperty.create("secondary_connection", OptionalDirection.class);

    public FreeformTransferTubeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(PRIMARY_CONNECTION, OptionalDirection.NONE).setValue(SECONDARY_CONNECTION, OptionalDirection.NONE));
    }

    @Override
    public Class<FreeformTransferTubeBlockEntity> getBlockEntityClass() {
        return FreeformTransferTubeBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends FreeformTransferTubeBlockEntity> getBlockEntityType() {
        return SagBlockEntityTypes.FREEFORM_TRANSFER_TUBE.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PRIMARY_CONNECTION, SECONDARY_CONNECTION);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        OptionalDirection primary = OptionalDirection.NONE;
        OptionalDirection secondary = OptionalDirection.NONE;

        BlockPos clickedBlockPos = context.getClickedPos().relative(context.getClickedFace().getOpposite());
        BlockState clickedBlock = context.getLevel().getBlockState(clickedBlockPos);

        if (clickedBlock.is(SagBlocks.FREEFORM_TRANSFER_TUBE.get())) {
            secondary = OptionalDirection.of(context.getClickedFace().getOpposite());
        }

        return this.defaultBlockState().setValue(PRIMARY_CONNECTION, primary).setValue(SECONDARY_CONNECTION, secondary);
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        OptionalDirection secondary = state.getValue(SECONDARY_CONNECTION);
        Direction dir = secondary.from();
        if (dir != null) {
            BlockPos neighborPos = pos.relative(dir);
            BlockState neighborState = level.getBlockState(neighborPos);

            if (neighborState.is(SagBlocks.FREEFORM_TRANSFER_TUBE.get())) {
                OptionalDirection comingFrom = OptionalDirection.of(dir.getOpposite());
                level.setBlock(neighborPos, neighborState.setValue(PRIMARY_CONNECTION, comingFrom), Block.UPDATE_ALL);
            }
        }
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        OptionalDirection con1 = state.getValue(PRIMARY_CONNECTION);
        OptionalDirection con2 = state.getValue(SECONDARY_CONNECTION);
        boolean no1 = con1 == OptionalDirection.NONE;
        boolean no2 = con2 == OptionalDirection.NONE;

        if (no1 && no2) {
            state = state.setValue(PRIMARY_CONNECTION, OptionalDirection.of(context.getClickedFace()));
        } else if (no1) {
            if (con2 == OptionalDirection.of(context.getClickedFace())) {
                state = state.setValue(SECONDARY_CONNECTION, OptionalDirection.NONE);
            } else {
                state = state.setValue(PRIMARY_CONNECTION, OptionalDirection.of(context.getClickedFace()));
            }
        } else if (no2) {
            if (con1 == OptionalDirection.of(context.getClickedFace())) {
                state = state.setValue(PRIMARY_CONNECTION, OptionalDirection.NONE);
            } else {
                state = state.setValue(SECONDARY_CONNECTION, OptionalDirection.of(context.getClickedFace()));
            }
        } else {
            if (con1 == OptionalDirection.of(context.getClickedFace())) {
                state = state.setValue(PRIMARY_CONNECTION, OptionalDirection.NONE);
            } else if (con2 == OptionalDirection.of(context.getClickedFace())) {
                state = state.setValue(SECONDARY_CONNECTION, OptionalDirection.NONE);
            } else {
                //state = state.setValue(PRIMARY_CONNECTION, OptionalDirection.of(context.getClickedFace()));
                return InteractionResult.PASS;
            }
        }

        IWrenchable.playRotateSound(level, pos);
        KineticBlockEntity.switchToBlockState(level, pos, updateAfterWrenched(state, context));

        return InteractionResult.SUCCESS;
    }
}
