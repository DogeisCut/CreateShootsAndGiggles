package io.github.dogeiscut.sag.content.kinetics.bedrockBuster;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import io.github.dogeiscut.sag.registry.SagPartialModels;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class BedrockBusterRenderer extends KineticBlockEntityRenderer<BedrockBusterBlockEntity> {

    private static final float CLAW_RADIUS = 5f / 16f;
    private static final float CRUSHING_WHEEL_SPIN_MULTIPLIER = 4f;

    public BedrockBusterRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(BedrockBusterBlockEntity be, BlockState state) {
        return CachedBuffers.partialFacing(SagPartialModels.BEDROCK_BUSTER_COG, state);
    }

    @Override
    protected void renderSafe(BedrockBusterBlockEntity be, float partialTicks, PoseStack ms,
                              MultiBufferSource buffer, int light, int overlay) {
        if (VisualizationManager.supportsVisualization(be.getLevel())) return;

        BlockState state = be.getBlockState();
        Direction.Axis axis = ((IRotate) state.getBlock()).getRotationAxis(state);
        VertexConsumer vb = buffer.getBuffer(getRenderType(be, state));

        float mainAngle = KineticBlockEntityRenderer.getAngleForBe(be, be.getBlockPos(), axis)
                * 180f / (float) Math.PI;

        for (int i = 0; i < 4; i++) {
            float armOffset = i * 90f;

            SuperByteBuffer claw = CachedBuffers.partialFacing(SagPartialModels.BEDROCK_BUSTER_CLAW, state);
            claw.light(light)
                    .center()
                    .rotate((float) Math.toRadians(armOffset + mainAngle), Direction.get(Direction.AxisDirection.POSITIVE, axis))
                    .uncenter();
            claw.renderInto(ms, vb);

            float wheelSpin = mainAngle * CRUSHING_WHEEL_SPIN_MULTIPLIER;
            SuperByteBuffer wheel = CachedBuffers.partialFacing(AllPartialModels.CRUSHING_WHEEL, state);
            wheel.light(light)
                    .center()
                    .rotate((float) Math.toRadians(armOffset + mainAngle), Direction.get(Direction.AxisDirection.POSITIVE, axis))
                    .translate(CLAW_RADIUS, 0, 0)
                    .rotate((float) Math.toRadians(wheelSpin), Direction.get(Direction.AxisDirection.POSITIVE, perpendicularAxis(axis)))
                    .uncenter();
            wheel.renderInto(ms, vb);
        }
    }

    private static Direction.Axis perpendicularAxis(Direction.Axis axis) {
        return switch (axis) {
            case X -> Direction.Axis.Z;
            case Y -> Direction.Axis.X;
            case Z -> Direction.Axis.X;
        };
    }
}