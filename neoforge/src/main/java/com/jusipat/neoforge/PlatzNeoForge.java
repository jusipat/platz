package com.jusipat.neoforge;

import com.jusipat.client.screens.PlatzMenuTypes;
import com.jusipat.client.screens.TownSquareScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import com.jusipat.Platz;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import javax.annotation.Nullable;

@Mod(Platz.MOD_ID)
public final class PlatzNeoForge {
    public PlatzNeoForge(ModContainer container) {
        // Run our common setup.
        Platz.init();

        @Nullable IEventBus modEventBus = container.getEventBus();
        RegisterImpl.BLOCK_ENTITY_TYPES.register(modEventBus);
    }

    @EventBusSubscriber(modid = Platz.MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(PlatzMenuTypes.TH_BLOCK.get(), TownSquareScreen::new);
            // join TH menu to TH screen
        }
    }
}
