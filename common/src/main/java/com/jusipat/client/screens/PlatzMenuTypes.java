package com.jusipat.client.screens;

import com.jusipat.Platz;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class PlatzMenuTypes {

    private static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Platz.MOD_ID, Registries.MENU);

    public static RegistrySupplier<MenuType<TownSquareMenu>> TH_BLOCK;

    public static void initMenuTypes(){
        TH_BLOCK = registerMenuType("th_block", () -> new MenuType<>(TownSquareMenu::new, FeatureFlagSet.of()));

        MENU_TYPES.register();

        if (Platform.isFabric()) {
            ClientLifecycleEvent.CLIENT_STARTED.register(client -> {
                MenuRegistry.registerScreenFactory(TH_BLOCK.get(), TownSquareScreen::new);
            });
        }
    }

    public static <T extends MenuType<?>> RegistrySupplier<T> registerMenuType(String name, Supplier<T> menuType){
        return MENU_TYPES.register(ResourceLocation.fromNamespaceAndPath(Platz.MOD_ID, name), menuType);
    }
}