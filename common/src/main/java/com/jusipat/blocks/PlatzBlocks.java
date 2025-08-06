package com.jusipat.blocks;

import com.jusipat.Platz;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class PlatzBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Platz.MOD_ID, Registries.BLOCK);

    public static RegistrySupplier<Block> TOWN_SQUARE_BLOCK;

    public static void initBlocks() {
            TOWN_SQUARE_BLOCK = registerBlock("town_square_block", () -> new TownSquareBlock(baseProperties("town_square_block")));


        BLOCKS.register();
    }

    public static RegistrySupplier<Block> registerBlock(String name, Supplier<Block> block) {
        return BLOCKS.register(ResourceLocation.fromNamespaceAndPath(Platz.MOD_ID, name), block);
    }
    public static BlockBehaviour.Properties baseProperties(String name) {
        return BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Platz.MOD_ID, name)));
    }
}
