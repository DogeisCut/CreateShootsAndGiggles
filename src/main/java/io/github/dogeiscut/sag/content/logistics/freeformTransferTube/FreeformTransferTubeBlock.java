package io.github.dogeiscut.sag.content.logistics.freeformTransferTube;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import io.github.dogeiscut.sag.registry.SagBlockEntityTypes;
import io.github.dogeiscut.sag.registry.SagBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
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
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        OptionalDirection primary = OptionalDirection.NONE;
        OptionalDirection secondary = OptionalDirection.NONE;

//        BlockPos clickedBlockPos = context.getClickedPos().relative(context.getClickedFace().getOpposite());
//        BlockState clickedBlock = context.getLevel().getBlockState(clickedBlockPos);

        // this is a pure function, probably don't want to do that here
//        if (clickedBlock.is(SagBlocks.FREEFORM_TRANSFER_TUBE.get())) {
//            primary = OptionalDirection.of(context.getClickedFace().getOpposite());
//            context.getLevel().setBlock(clickedBlockPos, clickedBlock.setValue(SECONDARY_CONNECTION, OptionalDirection.of(context.getClickedFace())), Block.UPDATE_NONE);
//        }

        return this.defaultBlockState().setValue(PRIMARY_CONNECTION, primary).setValue(SECONDARY_CONNECTION, secondary);
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
