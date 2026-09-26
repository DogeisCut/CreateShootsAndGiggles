package io.github.dogeiscut.sag.content.contraptions.playerAssemblerBooth;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import io.github.dogeiscut.sag.registry.SagBlockEntityTypes;
import io.github.dogeiscut.sag.registry.SagBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PlayerAssemblerBoothBlock extends Block implements SimpleWaterloggedBlock, IBE<PlayerAssemblerBoothBlockEntity>, IWrenchable {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape SOUTH_AABB = makeSouthShape();
    protected static final VoxelShape NORTH_AABB = makeNorthShape();
    protected static final VoxelShape WEST_AABB = makeWestShape();
    protected static final VoxelShape EAST_AABB = makeEastShape();

    protected static final VoxelShape SOUTH_AABB_TOP = makeSouthShape().move(0, -1, 0);
    protected static final VoxelShape NORTH_AABB_TOP = makeNorthShape().move(0, -1, 0);
    protected static final VoxelShape WEST_AABB_TOP = makeWestShape().move(0, -1, 0);
    protected static final VoxelShape EAST_AABB_TOP = makeEastShape().move(0, -1, 0);


    public static VoxelShape makeNorthShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.125, 2, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.875, 0, 0, 1, 2, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.875, 1, 2, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 1.875, 0, 1, 2, 1), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeEastShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 2, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.875, 1, 2, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.125, 2, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 1.875, 0, 1, 2, 1), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeSouthShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.875, 0, 0, 1, 2, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.125, 2, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 2, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 1.875, 0, 1, 2, 1), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeWestShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.875, 1, 2, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 2, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.875, 0, 0, 1, 2, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 1.875, 0, 1, 2, 1), BooleanOp.OR);

        return shape;
    }

//    @Override
//    protected void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
//        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
//            return;
//        }
//        if (entity instanceof Player player) {
//            withBlockEntityDo(level, pos, be -> be.assembleNextTick(player));
//        }
//    }

    public PlayerAssemblerBoothBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(POWERED, false)
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return IBE.super.newBlockEntity(pos, state);
        }
        return null;
    }

    protected @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Direction direction = state.getValue(FACING);
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return switch (direction) {
                case SOUTH -> SOUTH_AABB_TOP;
                case WEST -> WEST_AABB_TOP;
                case NORTH -> NORTH_AABB_TOP;
                default -> EAST_AABB_TOP;
            };
        }
        return switch (direction) {
            case SOUTH -> SOUTH_AABB;
            case WEST -> WEST_AABB;
            case NORTH -> NORTH_AABB;
            default -> EAST_AABB;
        };
    }

    protected @NotNull BlockState updateShape(BlockState state, Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        if (facing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (facing == Direction.UP)) {
            return facingState.getBlock() instanceof PlayerAssemblerBoothBlock && facingState.getValue(HALF) != doubleblockhalf ? facingState.setValue(HALF, doubleblockhalf) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        }
    }

    public static BlockState createAnchor(BlockState state) {
        return SagBlocks.PLAYER_ANCHOR.getDefaultState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED, FACING, HALF, WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    public static boolean canAssembleTo(Player player) {
        return player.isAlive();
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, @NotNull ItemStack stack) {
        level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        Level level = context.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(context)) {
            boolean flag = level.hasNeighborSignal(blockpos) || level.hasNeighborSignal(blockpos.above());
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(POWERED, flag).setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
        } else {
            return null;
        }
    }

    @Override
    protected @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = level.getBlockState(blockpos);
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(level, blockpos, Direction.UP) : blockstate.is(this);
    }

    @Override
    public void neighborChanged(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos,
                                @NotNull Block blockIn, @NotNull BlockPos fromPos, boolean isMoving) {
        if (worldIn.isClientSide)
            return;

        boolean isLower = state.getValue(HALF) == DoubleBlockHalf.LOWER;
        BlockPos lowerPos = isLower ? pos : pos.below();
        BlockPos upperPos = isLower ? pos.above() : pos;

        boolean shouldBePowered = worldIn.hasNeighborSignal(lowerPos) || worldIn.hasNeighborSignal(upperPos);

        if (state.getValue(POWERED) != shouldBePowered) {
            worldIn.setBlock(pos, state.setValue(POWERED, shouldBePowered), Block.UPDATE_CLIENTS);
        }

        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }

    @Override
    @NotNull
    public PushReaction getPistonPushReaction(@NotNull BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public Class<PlayerAssemblerBoothBlockEntity> getBlockEntityClass() {
        return PlayerAssemblerBoothBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends PlayerAssemblerBoothBlockEntity> getBlockEntityType() {
        return SagBlockEntityTypes.PLAYER_ASSEMBLER_BOOTH.get();
    }

    public static class PlayerAnchorBlock extends Block {

        public PlayerAnchorBlock(Properties p) {
            super(p);
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
        }

        @Override
        @NotNull
        public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter,
                                   @NotNull BlockPos pos, @NotNull CollisionContext context) {
            return Shapes.empty();
        }
    }

    @Override
    protected boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType pathComputationType) {
        return false;
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        Level world = context.getLevel();
        if (world.isClientSide)
            return InteractionResult.SUCCESS;
        BlockPos pos = context.getClickedPos();
        world.setBlock(pos, rotate(state, Rotation.CLOCKWISE_90), Block.UPDATE_ALL);
        world.updateNeighborsAt(pos.below(), this);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    protected @NotNull BlockState mirror(@NotNull BlockState state, @NotNull Mirror mirror) {
        return mirror == Mirror.NONE ? state : state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
