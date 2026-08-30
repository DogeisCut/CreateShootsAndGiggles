package io.github.dogeiscut.sag.content.kinetics.bedrockBuster;

import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import io.github.dogeiscut.sag.registry.SagConfig;
import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BedrockBusterBlockEntity extends KineticBlockEntity {

    private static final int CHARGE_REQUIRED = 20 * 60; // 1 minute
    private static final float EXPLOSION_RADIUS = 6f;

    private int charge = 0;
    private final int breakerId = -BlockBreakingKineticBlockEntity.NEXT_BREAKER_ID.incrementAndGet();

    LerpedFloat visualSpeed = LerpedFloat.linear();
    float angle;

    public BedrockBusterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    private BlockPos getTargetPos() {
        return getBlockPos().relative(getBlockState().getValue(BedrockBusterBlock.FACING));
    }

    @Override
    public boolean isSpeedRequirementFulfilled() {
        int required = Math.min(SagConfig.BEDROCK_BUSTER_MIN_SPEED.get(),
                com.simibubi.create.infrastructure.config.AllConfigs.server().kinetics.maxRotationSpeed.get());
        return Mth.abs(getSpeed()) >= required;
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide) {
            float targetSpeed = isSpeedRequirementFulfilled() ? getSpeed() : 0.0f;
            visualSpeed.updateChaseTarget(targetSpeed);
            visualSpeed.tickChaser();
            angle += visualSpeed.getValue() * 3 / 10f;
            angle %= 360;
        } else {
            BlockPos targetPos = getTargetPos();
            BlockState target = level.getBlockState(targetPos);
            boolean chargingProperly = isSpeedRequirementFulfilled() && target.is(Blocks.BEDROCK);

            if (!chargingProperly) {
                if (charge != 0) {
                    charge = 0;
                    level.destroyBlockProgress(breakerId, targetPos, -1);
                    level.destroyBlockProgress(breakerId, worldPosition, -1);
                }
                return;
            }

            BlockState stateToBreak = level.getBlockState(targetPos);

            level.playSound(null, targetPos, stateToBreak.getSoundType()
                    .getHitSound(), SoundSource.BLOCKS, .8f, ((float) charge / CHARGE_REQUIRED) + 0.5f);

            level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, targetPos, Block.getId(stateToBreak));

            charge++;
            level.destroyBlockProgress(breakerId, targetPos, Mth.clamp(charge * 10 / CHARGE_REQUIRED, 0, 9));
            level.destroyBlockProgress(breakerId, worldPosition, Mth.clamp(charge * 10 / CHARGE_REQUIRED, 0, 9));

            if (charge >= CHARGE_REQUIRED)
                detonate(targetPos);
        }
    }

    private void detonate(BlockPos targetPos) {
        if (!(level instanceof ServerLevel)) return;

        level.destroyBlockProgress(breakerId, targetPos, -1);
        level.destroyBlock(targetPos, false);
        level.destroyBlock(getBlockPos(), false);

        Vec3 center = Vec3.atCenterOf(targetPos);
        level.explode(BedrockBusterEvents.MARKER, center.x, center.y, center.z,
                EXPLOSION_RADIUS, true, Level.ExplosionInteraction.BLOCK);
    }

    @Override
    public void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putInt("Charge", charge);
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        charge = tag.getInt("Charge");
        if (clientPacket)
            visualSpeed.chase(getGeneratedSpeed(), 1 / 64f, LerpedFloat.Chaser.EXP);
    }

    @Override
    public void invalidate() {
        super.invalidate();
        if (level != null && !level.isClientSide)
            level.destroyBlockProgress(breakerId, getTargetPos(), -1);
    }
}