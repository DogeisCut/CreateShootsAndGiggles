package io.github.dogeiscut.sag.content.kinetics.bedrockBuster;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityVisual;
import com.simibubi.create.content.kinetics.base.RotatingInstance;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import com.simibubi.create.AllPartialModels;
import dev.engine_room.flywheel.api.instance.Instance;
import dev.engine_room.flywheel.api.visual.DynamicVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.instance.FlatLit;
import dev.engine_room.flywheel.lib.instance.InstanceTypes;
import dev.engine_room.flywheel.lib.instance.TransformedInstance;
import dev.engine_room.flywheel.lib.model.Models;
import dev.engine_room.flywheel.lib.visual.SimpleDynamicVisual;
import io.github.dogeiscut.sag.registry.SagPartialModels;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class BedrockBusterVisual extends KineticBlockEntityVisual<BedrockBusterBlockEntity> implements SimpleDynamicVisual {

    private static final int CLAW_COUNT = 5;
    private static final float WHEEL_SPIN_MULTIPLIER = -4f;

    protected final RotatingInstance cog;
    protected final TransformedInstance[] claws = new TransformedInstance[CLAW_COUNT];
    protected final TransformedInstance[] wheels = new TransformedInstance[CLAW_COUNT];

    protected float lastAngle = Float.NaN;
    private final Direction facing;

    public BedrockBusterVisual(VisualizationContext context, BedrockBusterBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick);

        BlockState state = blockEntity.getBlockState();
        facing = state.getValue(BedrockBusterBlock.FACING);

        cog = instancerProvider().instancer(AllInstanceTypes.ROTATING, Models.partial(SagPartialModels.BEDROCK_BUSTER_COG))
                .createInstance();
        cog.setup(blockEntity)
                .setPosition(getVisualPosition())
                .rotateToFace(facing.getOpposite())
                .setChanged();

        var clawInstancer = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(SagPartialModels.BEDROCK_BUSTER_CLAW));
        var wheelInstancer = instancerProvider().instancer(InstanceTypes.TRANSFORMED, Models.partial(AllPartialModels.CRUSHING_WHEEL));

        for (int i = 0; i < CLAW_COUNT; i++) {
            claws[i] = clawInstancer.createInstance();
            wheels[i] = wheelInstancer.createInstance();
        }

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
        for (int i = 0; i < CLAW_COUNT; i++) {
            float armOffset = i * (360f / CLAW_COUNT);

            claws[i].setIdentityTransform()
                    .translate(getVisualPosition())
                    .center()
                    .rotateToFace(facing.getOpposite())
                    .rotateZDegrees(angle + armOffset)
                    .rotateXDegrees(90f)
                    .uncenter()
                    .setChanged();

            float wheelSpin = angle * WHEEL_SPIN_MULTIPLIER;

            wheels[i].setIdentityTransform()
                    .translate(getVisualPosition())
                    .center()
                    .rotateToFace(facing.getOpposite())
                    .rotateZDegrees(angle + armOffset)
                    .rotateXDegrees(90f)
                    .translate(0, 1.1, 0.7)
                    .scale(0.4f)
                    .rotateZDegrees(90f)
                    .rotateYDegrees(wheelSpin)
                    .uncenter()
                    .setChanged();
        }
    }

    @Override
    public void update(float partialTick) {
        cog.setup(blockEntity).setChanged();
    }

    @Override
    public void updateLight(float partialTick) {
        relight(cog);
        for (int i = 0; i < CLAW_COUNT; i++) {
            relight(claws[i], wheels[i]);
        }
    }

    @Override
    protected void _delete() {
        cog.delete();
        for (var claw : claws) claw.delete();
        for (var wheel : wheels) wheel.delete();
    }

    @Override
    public void collectCrumblingInstances(Consumer<@Nullable Instance> consumer) {
        consumer.accept(cog);
        for (var claw : claws) consumer.accept(claw);
        for (var wheel : wheels) consumer.accept(wheel);
    }
}