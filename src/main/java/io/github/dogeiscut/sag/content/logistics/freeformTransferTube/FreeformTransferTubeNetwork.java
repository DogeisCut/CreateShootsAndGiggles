package io.github.dogeiscut.sag.content.logistics.freeformTransferTube;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.neoforged.neoforge.common.util.NeoForgeExtraCodecs;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FreeformTransferTubeNetwork {
    private final UUID id;
    private final Set<BlockPos> memberPositions;
    private final ItemStackHandler inventory;

    public static final Codec<FreeformTransferTubeNetwork> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            UUIDUtil.CODEC.fieldOf("id").forGetter(FreeformTransferTubeNetwork::getId),
            NeoForgeExtraCodecs.setOf(BlockPos.CODEC).fieldOf("member_positions").forGetter(FreeformTransferTubeNetwork::getMemberPositions)
    ).apply(instance, FreeformTransferTubeNetwork::new));

    public UUID getId() {
        return id;
    }

    public Set<BlockPos> getMemberPositions() {
        return memberPositions;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public FreeformTransferTubeNetwork() {
        this(UUID.randomUUID(), new HashSet<>());
    }

    public FreeformTransferTubeNetwork(UUID id, Set<BlockPos> positions) {
        this.id = id;
        this.memberPositions = positions;

        this.inventory = new ItemStackHandler(64) {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }
}
