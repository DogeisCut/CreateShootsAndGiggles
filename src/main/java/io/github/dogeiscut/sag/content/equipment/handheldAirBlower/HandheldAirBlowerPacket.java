package io.github.dogeiscut.sag.content.equipment.handheldAirBlower;

import com.simibubi.create.content.equipment.zapper.ShootGadgetPacket;
import com.simibubi.create.content.equipment.zapper.ShootableGadgetRenderHandler;
import io.github.dogeiscut.sag.SagClient;
import io.github.dogeiscut.sag.registry.SagPackets;
import net.createmod.catnip.codecs.stream.CatnipStreamCodecs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class HandheldAirBlowerPacket extends ShootGadgetPacket {
    public static final StreamCodec<RegistryFriendlyByteBuf, HandheldAirBlowerPacket> STREAM_CODEC = StreamCodec.composite(
            CatnipStreamCodecs.VEC3, p -> p.location,
            CatnipStreamCodecs.HAND, p -> p.hand,
            ByteBufCodecs.BOOL, p -> p.self,
            ByteBufCodecs.BOOL, p -> p.blow,
            HandheldAirBlowerPacket::new
    );
    protected final Boolean blow;

    public HandheldAirBlowerPacket(Vec3 location, InteractionHand hand, boolean self, boolean blow) {
        super(location, hand, self);
        this.blow = blow;
    }

    public HandheldAirBlowerPacket(Vec3 location, InteractionHand hand, boolean self) {
        this(location, hand, self, false);
    }

    @Override @OnlyIn(Dist.CLIENT)
    protected void handleAdditional() {

    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handle(LocalPlayer player) {
        Entity renderViewEntity = Minecraft.getInstance()
                .getCameraEntity();
        if (renderViewEntity == null)
            return;
        if (renderViewEntity.position()
                .distanceTo(location) > 100)
            return;

        ShootableGadgetRenderHandler handler = getHandler();
        handleAdditional();
        if (blow) {
            SagClient.HANDHELD_AIR_BLOWER_RENDER_HANDLER.blow(hand, location);
        } else {
            SagClient.HANDHELD_AIR_BLOWER_RENDER_HANDLER.shoot(hand, location);
        }
    }

    @Override @OnlyIn(Dist.CLIENT)
    protected ShootableGadgetRenderHandler getHandler() { return SagClient.HANDHELD_AIR_BLOWER_RENDER_HANDLER; }

    @Override
    public PacketTypeProvider getTypeProvider() { return SagPackets.HANDHELD_AIR_BLOWER; }
}