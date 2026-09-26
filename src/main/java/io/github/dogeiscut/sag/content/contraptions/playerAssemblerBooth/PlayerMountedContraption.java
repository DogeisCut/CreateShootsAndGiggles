package io.github.dogeiscut.sag.content.contraptions.playerAssemblerBooth;

import com.simibubi.create.AllContraptionTypes;
import com.simibubi.create.api.contraption.ContraptionType;
import com.simibubi.create.content.contraptions.AssemblyException;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.mounted.CartAssemblerBlockEntity;
import io.github.dogeiscut.sag.registry.SagBlocks;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Queue;

// TODO: fix drills and actors only working in one world direction (as if they were set to not rotate)
// TODO: offset the entire riding contraption down half a block on the player somehow
// TODO: model + polish
// TODO: consider opening up all sides of the booth

public class PlayerMountedContraption extends Contraption {

    public CartAssemblerBlockEntity.CartMovementMode rotationMode;
    public Player connectedPlayer;

    public PlayerMountedContraption() {
        this(CartAssemblerBlockEntity.CartMovementMode.ROTATE);
    }

    public PlayerMountedContraption(CartAssemblerBlockEntity.CartMovementMode mode) {
        rotationMode = mode;
    }

    @Override
    public ContraptionType getType() {
        return AllContraptionTypes.MOUNTED.value();
    }

    @Override
    public boolean assemble(Level world, BlockPos pos) throws AssemblyException {
        BlockState state = world.getBlockState(pos);
        if (!searchMovedStructure(world, pos, null))
            return false;

        addBlock(world, pos, Pair.of(new StructureTemplate.StructureBlockInfo(pos, SagBlocks.PLAYER_ANCHOR.getDefaultState(), null), null));

        return blocks.size() != 1;
    }

    @Override
    protected boolean addToInitialFrontier(Level world, BlockPos pos, Direction direction, Queue<BlockPos> frontier) {
        frontier.clear();
        frontier.add(pos.above());
        return true;
    }

    @Override
    protected Pair<StructureTemplate.StructureBlockInfo, BlockEntity> capture(Level world, BlockPos pos) {
        Pair<StructureTemplate.StructureBlockInfo, BlockEntity> pair = super.capture(world, pos);
        StructureTemplate.StructureBlockInfo capture = pair.getKey();
        if (!SagBlocks.PLAYER_ASSEMBLER_BOOTH.has(capture.state()))
            return pair;

        Pair<StructureTemplate.StructureBlockInfo, BlockEntity> anchorSwap =
                Pair.of(new StructureTemplate.StructureBlockInfo(pos, PlayerAssemblerBoothBlock.createAnchor(capture.state()), null), pair.getValue());
        if (pos.equals(anchor) || connectedPlayer != null)
            return anchorSwap;

        for (Direction.Axis axis : Iterate.axes) {
            if (axis.isVertical() || !VecHelper.onSameAxis(anchor, pos, axis))
                continue;
            for (Player player : world.getEntitiesOfClass(Player.class,
                    new AABB(pos))) {
                if (!PlayerAssemblerBoothBlock.canAssembleTo(player))
                    break;
                connectedPlayer = player;
                connectedPlayer.setPos(pos.getX() + .5, pos.getY(), pos.getZ() + .5f);
            }
        }

        return anchorSwap;
    }

    @Override
    protected boolean movementAllowed(BlockState state, Level world, BlockPos pos) {
        if (!pos.equals(anchor) && SagBlocks.PLAYER_ASSEMBLER_BOOTH.has(state))
            return testSecondaryCartAssembler(world, state, pos);
        return super.movementAllowed(state, world, pos);
    }

    protected boolean testSecondaryCartAssembler(Level world, BlockState state, BlockPos pos) {
        for (Direction.Axis axis : Iterate.axes) {
            if (axis.isVertical() || !VecHelper.onSameAxis(anchor, pos, axis))
                continue;
            for (Player player : world.getEntitiesOfClass(Player.class,
                    new AABB(pos))) {
                if (!PlayerAssemblerBoothBlock.canAssembleTo(player))
                    break;
                return true;
            }
        }
        return false;
    }

    @Override
    public CompoundTag writeNBT(HolderLookup.Provider registries, boolean spawnPacket) {
        CompoundTag tag = super.writeNBT(registries, spawnPacket);
        NBTHelper.writeEnum(tag, "RotationMode", rotationMode);
        return tag;
    }

    @Override
    public void readNBT(Level world, CompoundTag nbt, boolean spawnData) {
        rotationMode = NBTHelper.readEnum(nbt, "RotationMode", CartAssemblerBlockEntity.CartMovementMode.class);
        super.readNBT(world, nbt, spawnData);
    }

    @Override
    protected boolean customBlockPlacement(LevelAccessor world, BlockPos pos, BlockState state) {
        return SagBlocks.PLAYER_ANCHOR.has(state);
    }

    @Override
    protected boolean customBlockRemoval(LevelAccessor world, BlockPos pos, BlockState state) {
        return SagBlocks.PLAYER_ANCHOR.has(state);
    }

    @Override
    public boolean canBeStabilized(Direction facing, BlockPos localPos) {
        return true;
    }

    public void addExtraInventories(Entity cart) {
        if (cart instanceof Container container)
            storage.attachExternal(new InvWrapper(container));
    }
}
