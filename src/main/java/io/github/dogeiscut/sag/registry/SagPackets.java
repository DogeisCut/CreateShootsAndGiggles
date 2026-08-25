package io.github.dogeiscut.sag.registry;

import com.simibubi.create.CreateBuildInfo;
import io.github.dogeiscut.sag.Sag;
import io.github.dogeiscut.sag.content.equipment.handheldAirBlower.HandheldAirBlowerPacket;
import net.createmod.catnip.net.base.BasePacketPayload;
import net.createmod.catnip.net.base.CatnipPacketRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.Locale;

public enum SagPackets implements BasePacketPayload.PacketTypeProvider {

    // Server to Client
    HANDHELD_AIR_BLOWER(HandheldAirBlowerPacket.class, HandheldAirBlowerPacket.STREAM_CODEC),
    ;

    private final CatnipPacketRegistry.PacketType<?> type;

    <T extends BasePacketPayload> SagPackets(Class<T> clazz, StreamCodec<? super RegistryFriendlyByteBuf, T> codec) {
        String name = this.name().toLowerCase(Locale.ROOT);
        this.type = new CatnipPacketRegistry.PacketType<>(
                new CustomPacketPayload.Type<>(Sag.asResource(name)),
                clazz, codec
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends CustomPacketPayload> CustomPacketPayload.Type<T> getType() {
        return (CustomPacketPayload.Type<T>) this.type.type();
    }

    public static void register() {
        CatnipPacketRegistry packetRegistry = new CatnipPacketRegistry(Sag.ID, CreateBuildInfo.VERSION); // TODO: our own version
        for (SagPackets packet : SagPackets.values()) {
            packetRegistry.registerPacket(packet.type);
        }
        packetRegistry.registerAllPackets();
    }
}
