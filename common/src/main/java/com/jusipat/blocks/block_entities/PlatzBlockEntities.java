package com.jusipat.blocks.block_entities;

import com.jusipat.Platz;
import com.jusipat.Register;
import com.jusipat.blocks.PlatzBlocks;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class PlatzBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Platz.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final Supplier<BlockEntityType<TownSquareBlockEntity>> TOWN_HALL =
            Register.blockEntityType("town_square_block_entity", () -> Register.newBlockEntityType(TownSquareBlockEntity::new, PlatzBlocks.TOWN_HALL_BLOCK.get()));

    public static void initBlockEntities() {
        BLOCK_ENTITY_TYPES.register();
    }

}
