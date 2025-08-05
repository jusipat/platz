package com.jusipat.blocks.block_entities;

import com.jusipat.client.screens.TownSquareMenu;
import com.jusipat.map.TownMap;
import com.jusipat.map.TownMapGen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TownSquareBlockEntity extends BlockEntity {
    private TownMap townMap;
    public TownSquareBlockEntity(BlockPos pos, BlockState blockState) {
        super(PlatzBlockEntities.TOWN_SQUARE.get(), pos, blockState);
    }

    public TownMap getTownMap() {
        return townMap;
    }

    @Override
    public void setLevel(Level level) {
        super.setLevel(level);
        townMap = TownMapGen.generateMap(this);
    }
}
