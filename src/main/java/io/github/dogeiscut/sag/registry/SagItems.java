package io.github.dogeiscut.sag.registry;

import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import io.github.dogeiscut.sag.Sag;
import io.github.dogeiscut.sag.content.equipment.bomb.BombItem;
import io.github.dogeiscut.sag.content.equipment.handheldAirBlower.HandheldAirBlowerItem;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Rarity;

public class SagItems {
    private static final CreateRegistrate REGISTRATE = Sag.registrate();

    static {
        REGISTRATE.setCreativeTab(SagCreativeModeTabs.MAIN_TAB);
    }

    public static final ItemEntry<HandheldAirBlowerItem> HANDHELD_AIR_BLOWER = REGISTRATE.item("handheld_air_blower", HandheldAirBlowerItem::new)
            .properties(p -> p.rarity(Rarity.UNCOMMON))
            .tag(ItemTags.DURABILITY_ENCHANTABLE)
            .model(AssetLookup.itemModelWithPartials())
            //.model((ctx, prov) -> {})
            .register();

    public static final ItemEntry<BombItem> BOMB = REGISTRATE.item("bomb", BombItem::new)
            .register();


    public static void register() {
    }
}
