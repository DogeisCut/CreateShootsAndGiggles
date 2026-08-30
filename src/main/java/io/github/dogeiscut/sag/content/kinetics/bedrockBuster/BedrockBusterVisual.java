package io.github.dogeiscut.sag.content.kinetics.bedrockBuster;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import io.github.dogeiscut.sag.registry.SagPartialModels;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.function.Consumer;

import org.joml.Quaternionf;

import net.createmod.catnip.math.AngleHelper;
import net.minecraft.core.Direction;


public class BedrockBusterVisual extends KineticBlockEntityVisual<BedrockBusterBlockEntity> implements SimpleDynamicVisual {
    protected final RotatingInstance cog;
    protected final TransformedInstance claw;
    protected float lastAngle = Float.NaN;
    private final Direction facing;

    public BedrockBusterVisual(VisualizationContext context, BedrockBusterBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        BlockState state = blockEntity.getBlockState();

        facing = state.getValue(BedrockBusterBlock.FACING);

        cog = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(SagPartialModels.BEDROCK_BUSTER_COG))
                .createInstance();

        cog.setup(BedrockBusterVisual.this.blockEntity)
                .setPosition(getVisualPosition())
                .rotateToFace(facing.getOpposite())
                .setChanged();

        claw = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(SagPartialModels.BEDROCK_BUSTER_CLAW))
                .createInstance();

        animate(blockEntity.angle);
    }

    @Override
    public void beginFrame(DynamicVisual.Context ctx) {

        float partialTicks = ctx.partialTick();

        float speed = blockEntity.visualSpeed.getValue(partialTicks) * 3 / 10f;
        float angle = blockEntity.angle + speed * partialTicks;

        if (Math.abs(angle - lastAngle) < 0.001)
            return;

        animate(angle);

        lastAngle = angle;
    }

    private void animate(float angle) {
        claw.setIdentityTransform()
                .center()
                .rotateToFace(facing.getOpposite())
                .rotateZDegrees(angle)
                .uncenter()
                .setChanged();
    }

    @Override
    public void update(float partialTick) {
        cog.setup(blockEntity).setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        relight(cog, claw);
    }

    @Override
    protected void _delete() {
        cog.delete();
        claw.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {
        consumer.accept(cog);
        consumer.accept(claw);
    }
}
