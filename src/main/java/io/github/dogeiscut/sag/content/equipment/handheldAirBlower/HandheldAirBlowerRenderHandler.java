package io.github.dogeiscut.sag.content.equipment.handheldAirBlower;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.content.equipment.zapper.ShootableGadgetRenderHandler;
import io.github.dogeiscut.sag.registry.SagItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class HandheldAirBlowerRenderHandler extends ShootableGadgetRenderHandler {

    @Override
    protected boolean appliesTo(ItemStack stack) {
        return SagItems.HANDHELD_AIR_BLOWER.isIn(stack);
    }

    @Override
    protected void transformTool(PoseStack ms, float flip, float equipProgress, float recoil, float pt) {
        ms.translate(flip * -0.1f, 0.0f, -0.3f);
        ms.mulPose(Axis.YP.rotationDegrees(flip * 5.0F));
    }

    @Override
    protected void transformHand(PoseStack ms, float flip, float equipProgress, float recoil, float pt) {}

    @Override
    protected void playSound(InteractionHand hand, Vec3 position) {}
}