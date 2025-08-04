package com.jusipat.blocks;

import com.jusipat.blocks.block_entities.TownSquareBlockEntity;
import com.jusipat.client.screens.TownSquareMenu;
import com.jusipat.map.TownMap;
import com.jusipat.map.TownMapGen;
import com.mojang.serialization.MapCodec;
import dev.architectury.event.events.common.InteractionEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
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
    protected MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
        return new SimpleMenuProvider((i, inventory, player) -> new TownSquareMenu(i, inventory), Component.literal("Town Square"));
    }

    @Override
    public InteractionResult click(Player player, InteractionHand interactionHand, BlockPos blockPos, Direction direction) {
        BlockEntity be = player.level().getBlockEntity(blockPos);
        if (player.level().getBlockEntity(blockPos) == null || !((be) instanceof TownSquareBlockEntity)) {
            return InteractionResult.PASS;
        }
        if (player.isShiftKeyDown()){
            return InteractionResult.PASS;
        }

        if (!player.level().isClientSide) {
            player.openMenu(this.asBlock().defaultBlockState().getMenuProvider(player.level(), blockPos));

            TownSquareBlockEntity townBlockEntity = (TownSquareBlockEntity) be;
            TownMap map = TownMapGen.generateMap(townBlockEntity);
            TownMapGen.printMap(map);
        }

        return InteractionResult.SUCCESS;
    }

}
