package com.jusipat;

import com.jusipat.blocks.PlatzBlocks;
import com.jusipat.blocks.block_entities.PlatzBlockEntities;
import com.jusipat.client.screens.PlatzMenuTypes;
import com.jusipat.items.PlatzItems;

public final class Platz {
    public static final String MOD_ID = "platz";

    public static void init() {
        // Write common init code here.
        PlatzBlocks.initBlocks();
        PlatzItems.initItems();
        PlatzBlockEntities.initBlockEntities();
        PlatzMenuTypes.initMenuTypes();
    }
}
