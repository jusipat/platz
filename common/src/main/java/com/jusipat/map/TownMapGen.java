package com.jusipat.map;

import com.jusipat.blocks.TownSquareBlock;
import com.jusipat.blocks.block_entities.TownSquareBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;

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
                            setMapKey(map, state);
                            map.getCharacterSet().get(state);
                            if (map.getCharacterSet().get(state) != '.') {
                                map.setSymbolFromBlockState(arrayX, arrayZ, state);
                            }
                        }
                    }
                }
                }
        }
    }

    public static void parseMap(TownMap map) {
        BlockState[][] mapGrid = map.getGrid();
        HashMap<BlockState, Character> mapCharKeySet = map.getCharacterSet();
        ArrayList<String> asciiReadable = new ArrayList<>();
        for (BlockState[] states : mapGrid) {
            StringBuilder str = new StringBuilder();
            for (int c = 0; c < mapGrid[0].length; c++) {
                str.append(mapCharKeySet.getOrDefault(states[c], '.'));
            }
            asciiReadable.add(str.toString());
        }
        map.setAsciiMap(asciiReadable);
    }

    public static void setMapKey(TownMap map, BlockState state) {
        if (state.is(BlockTags.FLOWERS)) { // todo: implement more symbols
            map.getCharacterSet().put(state, '*');
        }
        else if (state.is(BlockTags.WALLS)) {
            map.getCharacterSet().put(state, '#');
        }
        else if (state.is(BlockTags.LOGS)) {
            map.getCharacterSet().put(state, '#');
        }
        else if (state.is(BlockTags.PLANKS)) {
            map.getCharacterSet().put(state, '#');
        }
        else if (state.is(Blocks.COBBLESTONE) || state.is(Blocks.MOSSY_COBBLESTONE)) {
            map.getCharacterSet().put(state, '#');
        }
        else if (state.is(Blocks.STONE)) {
            map.getCharacterSet().put(state, '#');
        }
        else if (state.is(BlockTags.STONE_BRICKS)) {
            map.getCharacterSet().put(state, '#');
        }
        else if (state.is(BlockTags.CROPS)) {
            map.getCharacterSet().put(state, '|');
        }
        else if (state.is(BlockTags.DOORS)) {
            map.getCharacterSet().put(state, 'd');
        }
        else if (state.is(BlockTags.SLABS)) {
            map.getCharacterSet().put(state, '-');
        }
        else if (state.is(BlockTags.STAIRS)) {
            map.getCharacterSet().put(state, '/');
        }
        else if (state.is(Blocks.WATER)) {
            map.getCharacterSet().put(state, '~');
        }
        else if (state.is(Blocks.DIRT_PATH)) {
            map.getCharacterSet().put(state, '_');
        }
        else if (state.is(Blocks.BUSH) || state.is(Blocks.DEAD_BUSH)) {
            map.getCharacterSet().put(state, '_');
        }
        else if (state.is(Blocks.TALL_DRY_GRASS) || state.is(Blocks.TALL_GRASS)
        || state.is(Blocks.SHORT_DRY_GRASS) || state.is(Blocks.SHORT_GRASS)) {
            map.getCharacterSet().put(state, ',');
        }
        else if (state.getBlock() instanceof TownSquareBlock) {
            map.getCharacterSet().put(state, 's');
        }
        else {
            map.getCharacterSet().put(state, '.');
        }
    }
}
