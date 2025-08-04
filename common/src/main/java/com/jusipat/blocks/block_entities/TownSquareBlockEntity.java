package com.jusipat.blocks.block_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TownSquareBlockEntity extends BlockEntity {
    public TownSquareBlockEntity(BlockPos pos, BlockState blockState) {
        super(PlatzBlockEntities.TOWN_HALL.get(), pos, blockState);
    }
}
