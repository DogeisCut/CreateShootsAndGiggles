package io.github.dogeiscut.sag.content.contraptions.playerAssemblerBooth;

import com.simibubi.create.content.contraptions.AssemblyException;
import com.simibubi.create.content.contraptions.IDisplayAssemblyExceptions;
import com.simibubi.create.content.contraptions.OrientedContraptionEntity;
import com.simibubi.create.content.contraptions.mounted.CartAssemblerBlock;
import com.simibubi.create.content.contraptions.mounted.CartAssemblerBlockEntity;
import com.simibubi.create.content.contraptions.mounted.MountedContraption;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import io.github.dogeiscut.sag.Sag;
import io.github.dogeiscut.sag.registry.SagBlocks;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class PlayerAssemblerBoothBlockEntity extends SmartBlockEntity implements IDisplayAssemblyExceptions {
    private static final int assemblyCooldown = 8;

    protected ScrollOptionBehaviour<CartAssemblerBlockEntity.CartMovementMode> movementMode;
    private int ticksSincePlayerUpdate;
    protected AssemblyException lastException;
    protected Player playerToAssemble;

    public PlayerAssemblerBoothBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        ticksSincePlayerUpdate = assemblyCooldown;
    }

    @Override
    public void tick() {
        super.tick();

        if (ticksSincePlayerUpdate < assemblyCooldown) {
            ticksSincePlayerUpdate++;
        }

        tryAssemble(playerToAssemble);
        playerToAssemble = null;

        assert level != null;
        for (Player player : level.getEntitiesOfClass(Player.class, new AABB(getBlockPos()).deflate(0.5))) {
            assembleNextTick(player);
            break;
        }
    }

    public void tryAssemble(Player player) {
        if (player == null) {
            return;
        }

        if (!isPlayerUpdateValid()) {
            return;
        }
        resetTicksSincePlayerUpdate();

        assert level != null;
        BlockState state = level.getBlockState(worldPosition);
        if (!SagBlocks.PLAYER_ASSEMBLER_BOOTH.has(state)) {
            return;
        }

        if (state.getValue(PlayerAssemblerBoothBlock.POWERED)) {
            assemble(level, worldPosition, player);
        } else {
            disassemble(level, worldPosition, player);
        }
    }

    protected void assemble(Level world, BlockPos pos, Player player) {
        if (!player.getPassengers().isEmpty()) {
            return;
        }
        assert level != null;
        if (level.isClientSide()) {
            return;
        }

        CartAssemblerBlockEntity.CartMovementMode mode = CartAssemblerBlockEntity.CartMovementMode.values()[movementMode.value];

        PlayerMountedContraption contraption = new PlayerMountedContraption(mode);
        try {
            if (!contraption.assemble(world, pos)) {
                return;
            }

            lastException = null;
            sendData();
        } catch (AssemblyException e) {
            lastException = e;
            sendData();

            return;
        }

        Direction initialOrientation = getBlockState().getValue(PlayerAssemblerBoothBlock.FACING);

        contraption.removeBlocksFromWorld(world, BlockPos.ZERO);
        contraption.startMoving(world);
        contraption.expandBoundsAroundAxis(Direction.Axis.Y);

        OrientedContraptionEntity entity = OrientedContraptionEntity.create(world, contraption, initialOrientation);
        entity.setPos(pos.getX() + .5, pos.getY(), pos.getZ() + .5);
        world.addFreshEntity(entity);
        entity.startRiding(player);
        ClientboundSetPassengersPacket packet = new ClientboundSetPassengersPacket(player);
        ((ServerPlayer) player).connection.send(packet);

        if (contraption.containsBlockBreakers())
            award(AllAdvancements.CONTRAPTION_ACTORS);
    }

    protected void disassemble(Level world, BlockPos pos, Player player) {
        if (player.getPassengers()
                .isEmpty())
            return;
        Entity entity = player.getPassengers().getFirst();
        if (!(entity instanceof OrientedContraptionEntity))
            return;
        disassemblePlayer(player);
    }

    protected void disassemblePlayer(Player player) {
        player.ejectPassengers();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        movementMode = new ScrollOptionBehaviour<>(CartAssemblerBlockEntity.CartMovementMode.class,
                CreateLang.translateDirect("contraptions.cart_movement_mode"), this, getMovementModeSlot());
        behaviours.add(movementMode);
    }

    @Override
    public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        AssemblyException.write(compound, registries, lastException);
        super.write(compound, registries, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        lastException = AssemblyException.read(compound, registries);
        super.read(compound, registries, clientPacket);
    }

    @Override
    public AssemblyException getLastAssemblyException() {
        return lastException;
    }

    protected ValueBoxTransform getMovementModeSlot() {
        return new PlayerAssemblerBoothValueBoxTransform();
    }

    private static class PlayerAssemblerBoothValueBoxTransform extends CenteredSideValueBoxTransform {

        public PlayerAssemblerBoothValueBoxTransform() {
            super((state, d) -> {
                if (d.getAxis().isVertical())
                    return false;
                return (d.getAxis() == Direction.Axis.X) == (state.getValue(PlayerAssemblerBoothBlock.FACING).getAxis() == Direction.Axis.Z);
            });
        }

        @Override
        protected Vec3 getSouthLocation() {
            return VecHelper.voxelSpace(8, 7, 17.5);
        }

    }

    public void assembleNextTick(Player player) {
        if (playerToAssemble == null)
            playerToAssemble = player;
    }

    public void resetTicksSincePlayerUpdate() {
        ticksSincePlayerUpdate = 0;
    }

    public boolean isPlayerUpdateValid() {
        return ticksSincePlayerUpdate >= assemblyCooldown;
    }
}
