package io.github.dogeiscut.sag.content.logistics.freeformTransferTube;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class FreeformTransferTubeSavedData extends SavedData {
    private final Map<UUID, FreeformTransferTubeNetwork> networks;

    public static SavedData.Factory<FreeformTransferTubeSavedData>

    public FreeformTransferTubeSavedData(Set<FreeformTransferTubeNetwork> networks, Level level) {
        this.networks = networks;
    }

    public Set<FreeformTransferTubeNetwork> getNetworks() {
        return networks;
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag compoundTag, HolderLookup.@NotNull Provider provider) {
        return compoundTag.put("networks", networks);
    }


}
