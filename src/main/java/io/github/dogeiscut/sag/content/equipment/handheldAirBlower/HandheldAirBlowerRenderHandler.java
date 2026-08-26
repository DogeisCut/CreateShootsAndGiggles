package io.github.dogeiscut.sag.content.equipment.handheldAirBlower;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.content.equipment.zapper.ShootableGadgetRenderHandler;
import io.github.dogeiscut.sag.registry.SagItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class HandheldAirBlowerRenderHandler extends ShootableGadgetRenderHandler {

    public static final HandheldAirBlowerRenderHandler INSTANCE = new HandheldAirBlowerRenderHandler();
    private static final float SHAKE_AMPLITUDE = 0.3f;

    private float chargePitch = 0.0F;
    private float lastChargePitch = 0.0F;

    private float spinSpeed = 2.0F;
    private float lastSpinSpeed = 2.0F;

    private float spinAngle = 0.0F;
    private float lastSpinAngle = 0.0F;

    private float shakeX, lastShakeX;
    private float shakeY, lastShakeY;
    private float shakeZ, lastShakeZ;

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Post event) {
        this.tick();
    }

    public Vec3 getShake(float pt) {
        return new Vec3(
                Mth.lerp(pt * 0.3f, lastShakeX, shakeX),
                Mth.lerp(pt * 0.3f, lastShakeY, shakeY),
                Mth.lerp(pt * 0.3f, lastShakeZ, shakeZ)
        );
    }

    @Override
    public void tick() {
        super.tick();

        lastChargePitch = chargePitch;
        lastSpinSpeed = spinSpeed;
        lastSpinAngle = spinAngle;
        lastShakeX = shakeX;
        lastShakeY = shakeY;
        lastShakeZ = shakeZ;

        LocalPlayer player = Minecraft.getInstance().player;
        float targetPitch = 0.0F;
        float targetSpeed = 2.0F;
        float overchargeFrac = 0.0F;

        if (player != null && player.isUsingItem() && appliesTo(player.getUseItem())) {
            if (player.isCrouching()) {
                float chargeProgress = Mth.clamp((float) player.getTicksUsingItem() / HandheldAirBlowerItem.getMaxChargeTicks() * 6.0f, 0.0F, 1.0F);
                targetPitch = chargeProgress * 15.0F;
                targetSpeed = 15.0F + (chargeProgress * 65.0F);

                int maxTicks = HandheldAirBlowerItem.getMaxChargeTicks();
                int overfillTicks = HandheldAirBlowerItem.getOverfillExplodeTicks();
                overchargeFrac = Mth.clamp((float) (player.getTicksUsingItem() - maxTicks) / (overfillTicks - maxTicks), 0.0F, 1.0F);
            } else {
                targetPitch = 0.0F;
                targetSpeed = 30.0F;
            }
        }

        chargePitch = Mth.lerp(0.8F, chargePitch, targetPitch);
        spinSpeed = Mth.lerp(0.15F, spinSpeed, targetSpeed);
        spinAngle += spinSpeed;

        if (overchargeFrac > 0) {
            RandomSource random = Minecraft.getInstance().level.random;
            shakeX = (random.nextFloat() - 0.5F) * overchargeFrac * SHAKE_AMPLITUDE;
            shakeY = (random.nextFloat() - 0.5F) * overchargeFrac * SHAKE_AMPLITUDE;
            shakeZ = (random.nextFloat() - 0.5F) * overchargeFrac * SHAKE_AMPLITUDE;
        } else {
            shakeX = shakeY = shakeZ = 0.0F;
        }
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
        Vec3 shake = getShake(pt);
        ms.translate(flip * -0.1F + shake.x / 3.0f, -getPitch(pt)/30.0f/3.0f + shake.y / 3.0f, -0.3F + recoil * 2 + shake.z / 3.0f);
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