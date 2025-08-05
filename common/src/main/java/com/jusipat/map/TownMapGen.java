package com.jusipat.map;

import com.jusipat.blocks.TownSquareBlock;
import com.jusipat.blocks.block_entities.TownSquareBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class TownMapGen {

    public static TownMap generateMap(TownSquareBlockEntity blockEntity) {
        final int radius = 16;
        TownMap map = new TownMap(radius * 2, blockEntity.getBlockPos());

        TownMapGen.writeToMap(map, blockEntity, radius);
        TownMapGen.parseMap(map);
        return map;
    }

    public static void writeToMap(TownMap map, TownSquareBlockEntity blockEntity, int RADIUS) {
        for (int h = -1; h < 10; h++) {
            for (int dx = -RADIUS; dx < RADIUS; dx++) {
                for (int dz = -RADIUS; dz < RADIUS; dz++) {
                    BlockPos scanPos = blockEntity.getBlockPos().offset(dx, h, dz);
                    Level level;
                    if (blockEntity.hasLevel()) {
                        level = blockEntity.getLevel();
                        BlockState state = level.getBlockState(scanPos);
                        int arrayX = dx + RADIUS;
                        int arrayZ = dz + RADIUS;
                        if (arrayX < map.getSize() && arrayZ < map.getSize()) {
                            char symbol = getSymbolFromBlock(state);
                            if (symbol != '.') {
                                map.setSymbol(arrayX, arrayZ, symbol);
                            }
                        }
                    }
                }
                }
            map.updateBeautyScore();
        }
    }

    public static char getSymbolFromBlock(BlockState state) {
        if (state.is(BlockTags.FLOWERS)) { // todo: implement more symbols
            return '*';
        }
        else if (state.is(BlockTags.WALLS)) {
            return '#';
        }
        else if (state.is(BlockTags.LOGS)) {
            return '#';
        }
        else if (state.is(BlockTags.PLANKS)) {
            return '#';
        }
        else if (state.is(Blocks.COBBLESTONE) || state.is(Blocks.MOSSY_COBBLESTONE)) {
            return '#';
        }
        else if (state.is(Blocks.STONE)) {
            return '#';
        }
        else if (state.is(BlockTags.STONE_BRICKS)) {
            return '#';
        }
        else if (state.is(BlockTags.CROPS)) {
            return '|';
        }
        else if (state.is(BlockTags.DOORS)) {
            return 'D';
        }
        else if (state.is(BlockTags.SLABS)) {
            return '-';
        }
        else if (state.is(BlockTags.STAIRS)) {
            return '/';
        }
        else if (state.is(Blocks.WATER)) {
            return '~';
        }
        else if (state.is(Blocks.DIRT_PATH)) {
            return '_';
        }
        else if (state.getBlock() instanceof TownSquareBlock) {
            return 'S';
        }
        else {
            return '.';
        }
    }

    public static void parseMap(TownMap map) {
        char[][] mapGrid = map.getGrid();
        ArrayList<String> ar = new ArrayList<>();
        for (char[] chars : mapGrid) {
            StringBuilder str = new StringBuilder();
            for (int c = 0; c < mapGrid[0].length; c++) {
                str.append(chars[c]);
            }
            System.err.println(str);
            ar.add(str.toString());
        }
        //System.err.println(ar);
        map.setArray(ar);
    }
}
