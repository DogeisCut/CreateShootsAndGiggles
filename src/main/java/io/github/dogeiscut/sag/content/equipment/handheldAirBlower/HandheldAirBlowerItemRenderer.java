package io.github.dogeiscut.sag.content.equipment.handheldAirBlower;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import io.github.dogeiscut.sag.Sag;
import io.github.dogeiscut.sag.SagClient;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class HandheldAirBlowerItemRenderer extends CustomRenderedItemModelRenderer {

    protected static final PartialModel PUMP = PartialModel.of(Sag.asResource("item/handheld_air_blower/pump"));
    protected static final PartialModel NOZZLE = PartialModel.of(Sag.asResource("item/handheld_air_blower/nozzle"));

    @Override
    protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        float pt = AnimationTickHolder.getPartialTicks();

        renderer.render(model.getOriginalModel(), light);

        float angle = SagClient.BLOWER_RENDER_HANDLER.getSpinAngle(pt);


        ms.pushPose();
        float offset = -0.125F;
        ms.translate(0, offset, 0);
        ms.mulPose(Axis.ZP.rotationDegrees(angle));
        ms.translate(0, -offset, 0);

        renderer.render(PUMP.get(), light);
        ms.popPose();


        ms.pushPose();
        ms.translate(0, offset, 0);
        ms.mulPose(Axis.ZP.rotationDegrees(-angle));
        ms.translate(0, -offset, 0);

        renderer.render(NOZZLE.get(), light);
        ms.popPose();
    }
}