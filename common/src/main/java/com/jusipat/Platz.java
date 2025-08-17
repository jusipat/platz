package com.jusipat;

import com.jusipat.blocks.PlatzBlocks;
import com.jusipat.blocks.block_entities.PlatzBlockEntities;
import com.jusipat.client.screens.PlatzMenuTypes;
import com.jusipat.items.PlatzItems;
import com.jusipat.netcode.GossipPacket;
import commonnetwork.api.Network;

public final class Platz {
    public static final String MOD_ID = "platz";

    public static void init() {
        // Write common init code here.
        PlatzBlocks.initBlocks();
        PlatzItems.initItems();
        PlatzBlockEntities.initBlockEntities();
        PlatzMenuTypes.initMenuTypes();


        // common packet registration
        Network.registerPacket(GossipPacket.type(), GossipPacket.class, GossipPacket.STREAM_CODEC, GossipPacket::handle);
    }
}
