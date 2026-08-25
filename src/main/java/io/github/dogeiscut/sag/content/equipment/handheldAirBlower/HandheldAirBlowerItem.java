package io.github.dogeiscut.sag.content.equipment.handheldAirBlower;

import com.simibubi.create.content.equipment.armor.BacktankUtil;
import io.github.dogeiscut.sag.registry.SagItems;
import io.github.dogeiscut.sag.registry.SagSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;

import java.util.Optional;
import java.util.function.Function;

public class HandheldAirBlowerItem extends Item {
    private static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR;
    public static final int MAX_DAMAGE = 200;

    // TODO:
    // - Fix bar flickering on charge
    // - Fix being able to store charge state by uncrouching while holding right click. (force unuse on uncrouch?)
    // - Adjust charge sound events (custom sounds?)
    // - Increase wind charge speed for higher charge.
    // - Decide if this should be useable without a backtank
    // - Balance and adjust default wind usage values.
    // - Blowing logic
    // - entity blowing interactions
    // - Sable sublevel interaction
    // - particles
    // - model + renderer
    // - Make shooting a wind charge cost air
    // - Make overcharging a wind charge cost air
    // - Air explosion self damage
    // - Fix missing subtitle translations
    // - Make charging not cost air

    public HandheldAirBlowerItem(Properties properties) {
        super(properties.durability(MAX_DAMAGE));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        if (isChargingClient(stack)) return true;
        return BacktankUtil.isBarVisible(stack, maxUses());
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        if (isChargingClient(stack)) {
            Player player = getClientPlayer();
            if (player != null) {
                int ticks = player.getTicksUsingItem();
                int overfillTicks = getOverfillExplodeTicks();

                // Smoothly fills up from 0 to 13 over the entire charge duration
                float progress = (float) ticks / (float) overfillTicks;
                return Math.round(13.0f * Mth.clamp(progress, 0.0f, 1.0f));
            }
        }
        return BacktankUtil.getBarWidth(stack, maxUses());
    }

    @Override
    public int getBarColor(ItemStack stack) {
        if (isChargingClient(stack)) {
            Player player = getClientPlayer();
            if (player != null) {
                int ticks = player.getTicksUsingItem();
                int minTicks = getMinChargeTicks();
                int maxTicks = getMaxChargeTicks();
                int overfillTicks = getOverfillExplodeTicks();

                if (ticks < minTicks) {
                    // Cyan Blue during initial charge-up
                    return 0x00A8FF;
                } else if (ticks <= maxTicks) {
                    // Green Zone (Perfect Launch Window)
                    return 0x55FF55;
                } else {
                    // Fades smoothly from Green to Warning Red as it nears explosion
                    float factor = (float) (ticks - maxTicks) / (overfillTicks - maxTicks);
                    factor = Mth.clamp(factor, 0.0f, 1.0f);

                    int startColor = 0x55FF55; // Green
                    int endColor = 0xFF0000;   // Red

                    int r = (int) Mth.lerp(factor, (startColor >> 16) & 0xFF, (endColor >> 16) & 0xFF);
                    int g = (int) Mth.lerp(factor, (startColor >> 8) & 0xFF, (endColor >> 8) & 0xFF);
                    int b = (int) Mth.lerp(factor, startColor & 0xFF, endColor & 0xFF);

                    return (r << 16) | (g << 8) | b;
                }
            }
        }
        return BacktankUtil.getBarColor(stack, maxUses());
    }

    private boolean isChargingClient(ItemStack stack) {
        Player player = getClientPlayer();
        if (player != null && player.isUsingItem() && player.getUseItem() == stack) {
            return player.isCrouching();
        }
        return false;
    }

    private Player getClientPlayer() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            return Minecraft.getInstance().player;
        }
        return null;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (slotChanged) return true;
        return !ItemStack.isSameItem(oldStack, newStack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
        return false;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return Integer.MAX_VALUE;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (!(livingEntity instanceof Player player)) return;

        if (remainingUseDuration % getDamageRate() == 0) {
            findAndDamageHandheldAirBlower(player);
        }

        int ticksUsed = livingEntity.getTicksUsingItem();

        if (player.isCrouching()) {
            handleCharging(level, player, ticksUsed);
        } else {
            // TODO: Blowing logic...
            if (ticksUsed % 6 == 0) {
                SagSoundEvents.AIR_BLOWER_BLOW.playFrom(player, 0.6f, 1.0f);
            }
        }
    }

    private void handleCharging(Level level, Player player, int chargeTicks) {
        int minTicks = getMinChargeTicks();
        int maxTicks = getMaxChargeTicks();
        int overfillTicks = getOverfillExplodeTicks();

        if (chargeTicks < maxTicks && chargeTicks % 5 == 0) {
            SagSoundEvents.AIR_BLOWER_CHARGE_LIGHT.playFrom(player, 0.4f, 0.8f + ((float) chargeTicks / minTicks) * 0.4f);
        } else if (chargeTicks >= maxTicks && chargeTicks <= overfillTicks && chargeTicks % 3 == 0) {
            SagSoundEvents.AIR_BLOWER_CHARGE_HEAVY.playFrom(player, 0.7f, 1.0f + ((float) (chargeTicks - maxTicks) / (overfillTicks - maxTicks)) * 0.5f);
        }

        if (chargeTicks > overfillTicks) {
            if (!level.isClientSide) {
                Vec3 eyePos = player.getEyePosition();
                DamageSource damageSource = level.damageSources().explosion(player, player);

                level.explode(
                        player,
                        damageSource,
                        EXPLOSION_DAMAGE_CALCULATOR,
                        eyePos.x, eyePos.y, eyePos.z,
                        1.2F,
                        false,
                        Level.ExplosionInteraction.TRIGGER,
                        ParticleTypes.GUST_EMITTER_SMALL,
                        ParticleTypes.GUST_EMITTER_LARGE,
                        SoundEvents.WIND_CHARGE_BURST
                );
            }
            SagSoundEvents.AIR_BLOWER_EXPLODE.playFrom(player, 1.0f, 1.0f);
            player.stopUsingItem();
        }
    }

    static {
        EXPLOSION_DAMAGE_CALCULATOR = new SimpleExplosionDamageCalculator(
                true,
                true,
                Optional.of(1.22F),
                BuiltInRegistries.BLOCK.getTag(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity())
        );
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) return;

        int chargeTicks = entity.getTicksUsingItem();

        if (player.isCrouching() && chargeTicks >= getMinChargeTicks()) {
            if (!level.isClientSide) {
                Vec3 look = player.getLookAngle();
                WindCharge windCharge = new WindCharge(player, level, player.getX(), player.getEyeY() - 0.1, player.getZ());
                windCharge.shoot(look.x, look.y, look.z, 1.5f, 1.0f);
                level.addFreshEntity(windCharge);
            }
            SagSoundEvents.AIR_BLOWER_SHOOT.playFrom(player, 1.0f, 1.0f);
            player.getCooldowns().addCooldown(this, getWindChargeCooldownTicks());
        }
    }

    public static int getMinChargeTicks() { return 20; }
    public static int getMaxChargeTicks() { return 40; }
    public static int getOverfillExplodeTicks() { return 60; }
    public static int getWindChargeCooldownTicks() { return 15; }
    public static int getDamageRate() { return 5; }
    public static int maxUses() { return MAX_DAMAGE; }

    private static void findAndDamageHandheldAirBlower(Player player) {
        if (player == null || player.level().isClientSide) return;

        EquipmentSlot equipmentSlot = EquipmentSlot.MAINHAND;
        ItemStack blower = player.getMainHandItem();

        if (!SagItems.HANDHELD_AIR_BLOWER.isIn(blower)) {
            blower = player.getOffhandItem();
            equipmentSlot = EquipmentSlot.OFFHAND;
        }
        if (!SagItems.HANDHELD_AIR_BLOWER.isIn(blower)) return;

        if (!BacktankUtil.canAbsorbDamage(player, maxUses())) {
            blower.hurtAndBreak(1, player, equipmentSlot);
        }
    }
}