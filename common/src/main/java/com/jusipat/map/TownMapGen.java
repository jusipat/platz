package com.jusipat.map;

import com.jusipat.blocks.TownSquareBlock;
import com.jusipat.blocks.block_entities.TownSquareBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TownMapGen {

    public static TownMap generateMap(TownSquareBlockEntity blockEntity) {
        TownMap map = new TownMap(32, blockEntity.getBlockPos());
        Level level = blockEntity.getLevel();

        for (int dx = (map.getSize()*-1); dx <= map.getSize()/2;dx++) {
            for (int dz = (map.getSize()*-1); dz <= map.getSize()/2;dz++) {
                BlockPos scan = new BlockPos(map.getPos().getX() + dx, map.getPos().getY(), map.getPos().getZ() + dz);
                BlockState state = level.getBlockState(scan);

                char symbol = getSymbolFromBlock(state);
                map.setSymbol(dx + 16, dz + 16, symbol);

            }
        }
        map.updateBeautyScore();
        return map;
    }
    public static char getSymbolFromBlock(BlockState state) {
        if (state.is(BlockTags.FLOWERS)) { // todo: implement more symbols
            return '⚘';
        }
        else if (state.getBlock() instanceof TownSquareBlock) {
            return '★';
        }
        else {
            return '.';
        }
    }
}
