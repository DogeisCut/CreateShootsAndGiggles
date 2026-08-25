package io.github.dogeiscut.sag.content.equipment.handheldAirBlower;

import com.simibubi.create.content.equipment.armor.BacktankUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;

public class HandheldAirBlowerItem extends Item {
    public static final int MAX_DAMAGE = 200;

    private static DamageSource lastActiveDamageSource;

    public HandheldAirBlowerItem(Properties properties) {
        super(properties.durability(MAX_DAMAGE));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return BacktankUtil.isBarVisible(stack, MAX_DAMAGE);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return BacktankUtil.getBarWidth(stack, MAX_DAMAGE);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return BacktankUtil.getBarColor(stack, MAX_DAMAGE);
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
        return true;
    }
}
