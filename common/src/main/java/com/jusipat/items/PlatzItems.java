package com.jusipat.items;

import com.jusipat.Platz;
import com.jusipat.blocks.PlatzBlocks;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class PlatzItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Platz.MOD_ID, Registries.ITEM);

    public static RegistrySupplier<Item> TOWN_HALL_BLOCK;

    public static void initItems() {
        TOWN_HALL_BLOCK = registerItem("town_square", () -> new BlockItem(PlatzBlocks.TOWN_SQUARE_BLOCK.get(), baseProperties("town_square")));

        ITEMS.register();
    }

    public static RegistrySupplier<Item> registerItem(String name, Supplier<Item> item) {
        return ITEMS.register(ResourceLocation.fromNamespaceAndPath(Platz.MOD_ID, name), item);
    }
    public static Item.Properties baseProperties(String name) {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Platz.MOD_ID, name)));
    }
}
