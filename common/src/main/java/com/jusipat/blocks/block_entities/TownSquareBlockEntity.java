package com.jusipat.blocks.block_entities;

import com.jusipat.map.TownMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TownSquareBlockEntity extends BlockEntity {
    public TownSquareBlockEntity(BlockPos pos, BlockState blockState) {
        super(PlatzBlockEntities.TOWN_SQUARE.get(), pos, blockState);
        townMap = new TownMap(32, pos);
    }
    private TownMap townMap;

    public TownMap getTownMap() {
        return townMap;
    }
}
