package com.jusipat.netcode;

import com.jusipat.Platz;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

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

    public static void handle(PacketContext<GossipPacket> ctx)
    {
        if (Side.CLIENT.equals(ctx.side()))
        {
            Minecraft.getInstance().player.displayClientMessage(Component.literal("ExamplePacketOne on the client!"), true);
        }
        else
        {
            ctx.sender().sendSystemMessage(Component.literal("ExamplePacketOne received on the server"));
        }
    }
}