package io.github.dogeiscut.sag.content.equipment.handheldAirBlower;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.content.equipment.zapper.ShootableGadgetRenderHandler;
import io.github.dogeiscut.sag.registry.SagItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class HandheldAirBlowerRenderHandler extends ShootableGadgetRenderHandler {

    public static final HandheldAirBlowerRenderHandler INSTANCE = new HandheldAirBlowerRenderHandler();

    private float chargePitch = 0.0F;
    private float lastChargePitch = 0.0F;

    private float spinSpeed = 2.0F;
    private float lastSpinSpeed = 2.0F;

    private float spinAngle = 0.0F;
    private float lastSpinAngle = 0.0F;

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Post event) {
        this.tick();
    }

    @Override
    public void tick() {
        super.tick();

        lastChargePitch = chargePitch;
        lastSpinSpeed = spinSpeed;
        lastSpinAngle = spinAngle;

        LocalPlayer player = Minecraft.getInstance().player;
        float targetPitch = 0.0F;
        float targetSpeed = 2.0F;

        if (player != null && player.isUsingItem() && appliesTo(player.getUseItem())) {
            if (player.isCrouching()) {
                float chargeProgress = Mth.clamp((float) player.getTicksUsingItem() / HandheldAirBlowerItem.getMaxChargeTicks() * 6.0f, 0.0F, 1.0F);
                targetPitch = chargeProgress * 15.0F;
                targetSpeed = 15.0F + (chargeProgress * 65.0F);
            } else {
                targetPitch = 0.0F;
                targetSpeed = 30.0F;
            }
        }

        chargePitch = Mth.lerp(0.8F, chargePitch, targetPitch);
        spinSpeed = Mth.lerp(0.15F, spinSpeed, targetSpeed);

        spinAngle += spinSpeed;
    }

    public void blow(InteractionHand hand, Vec3 location) {
        LocalPlayer player = Minecraft.getInstance().player;
        boolean rightHand = hand == InteractionHand.MAIN_HAND ^ player.getMainArm() == HumanoidArm.LEFT;
        if (rightHand) {
            rightHandAnimation = .08f;
            dontReequipRight = false;
        } else {
            leftHandAnimation = .08f;
            dontReequipLeft = false;
        }
        playSound(hand, location);
    }

    public float getPitch(float pt) {
        return Mth.lerp(pt, lastChargePitch, chargePitch);
    }

    public float getSpinAngle(float pt) {
        return Mth.lerp(pt, lastSpinAngle, spinAngle);
    }

    @Override
    protected boolean appliesTo(ItemStack stack) {
        return SagItems.HANDHELD_AIR_BLOWER.isIn(stack);
    }

    @Override
    protected void transformTool(PoseStack ms, float flip, float equipProgress, float recoil, float pt) {
        ms.translate(flip * -0.1F, -getPitch(pt)/30.0f/3.0f, -0.3F + recoil * 2);
        ms.mulPose(Axis.YP.rotationDegrees(flip * 5.0F));

        float totalPitch = -getPitch(pt) - (recoil * 45.0F);
        ms.mulPose(Axis.XP.rotationDegrees(totalPitch));
    }

    @Override
    protected void transformHand(PoseStack ms, float flip, float equipProgress, float recoil, float pt) {
        ms.translate(0.0f, -0.1, 0.1F);
        float totalPitch = getPitch(pt) - (recoil * 45.0F);
        ms.mulPose(Axis.XP.rotationDegrees(totalPitch));
    }

    @Override
    protected void playSound(InteractionHand hand, Vec3 position) {}
}