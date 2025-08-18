package com.jusipat.netcode;

import com.jusipat.Platz;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class GossipPacket
{
    public static final ResourceLocation CHANNEL = ResourceLocation.fromNamespaceAndPath(Platz.MOD_ID, "gossip_packet");
    public static final StreamCodec<FriendlyByteBuf, GossipPacket> STREAM_CODEC = StreamCodec.ofMember(GossipPacket::encode, GossipPacket::new);

    public GossipPacket()
    {
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type()
    {
        return new CustomPacketPayload.Type<>(CHANNEL);
    }

    public GossipPacket(FriendlyByteBuf buf)
    {
    }

    public void encode(FriendlyByteBuf buf)
    {

    }

    public static void handle(PacketContext<GossipPacket> ctx) {
        if (Side.CLIENT.equals(ctx.side())) {
            // Optional: client feedback
            Minecraft.getInstance().player.displayClientMessage(Component.literal("Sent villager discount packet"), true);
        } else {
            ServerPlayer player = ctx.sender();

            if (player != null && player.level() instanceof ServerLevel level) {
                BlockPos pos = player.blockPosition();
                AABB area = new AABB(
                        pos.getX() - 100, pos.getY() - 100, pos.getZ() - 100,
                        pos.getX() + 100, pos.getY() + 100, pos.getZ() + 100
                );

                List<Villager> villagers = level.getEntitiesOfClass(Villager.class, area);

                for (Villager villager : villagers) {
                    villager.getGossips().add(player.getUUID(), GossipType.MAJOR_POSITIVE, 20);
                    villager.getGossips().add(player.getUUID(), GossipType.MAJOR_POSITIVE, 20);
                    villager.getGossips().add(player.getUUID(), GossipType.MAJOR_POSITIVE, 20);
                }

                player.sendSystemMessage(Component.literal("You gained reputation with nearby villagers!"));
            }
        }
    }

}