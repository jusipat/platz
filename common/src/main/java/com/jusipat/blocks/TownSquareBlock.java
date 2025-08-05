package com.jusipat.blocks;

import com.jusipat.blocks.block_entities.TownSquareBlockEntity;
import com.jusipat.client.screens.TownSquareMenu;
import com.jusipat.map.TownMap;
import com.jusipat.map.TownMapGen;
import com.mojang.serialization.MapCodec;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TownSquareBlock extends BaseEntityBlock implements InteractionEvent.RightClickBlock {

    protected TownSquareBlock(Properties properties) {
        super(properties);
        InteractionEvent.RIGHT_CLICK_BLOCK.register(this);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TownSquareBlockEntity(blockPos, blockState);
    }

    @Nullable
    protected ExtendedMenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
        BlockEntity be = level.getBlockEntity(blockPos);
        if (be instanceof TownSquareBlockEntity) {
            return new ExtendedMenuProvider() {
                @Override
                public void saveExtraData(FriendlyByteBuf buf) {
                    buf.writeBlockPos(blockPos);
                }

                @Override
                public Component getDisplayName() {
                    return Component.empty();
                }

                @Override
                public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                    return new TownSquareMenu(i, inventory, be);
                }
            };
        }
        return null;
    }

    @Override
    public InteractionResult click(Player player, InteractionHand interactionHand, BlockPos pos, Direction direction) {
        BlockEntity be = player.level().getBlockEntity(pos);
        if (player.level().getBlockEntity(pos) == null || !((be) instanceof TownSquareBlockEntity)) {
            return InteractionResult.PASS;
        }
        if (player.isShiftKeyDown()){
            return InteractionResult.PASS;
        }
        if (!player.level().isClientSide) {
            ExtendedMenuProvider menuProvider = this.getMenuProvider(
                    player.level().getBlockState(pos),
                    player.level(),
                    pos
            );
            if (menuProvider != null) {
                MenuRegistry.openExtendedMenu((ServerPlayer) player, menuProvider);
                // multiplatform menu open ^^
                //player.openMenu(menuProvider);
            }
        }

        return InteractionResult.SUCCESS;
    }


}
