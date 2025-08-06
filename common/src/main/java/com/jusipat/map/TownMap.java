package com.jusipat.map;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TownMap {
    private int size;
    private BlockState[][] grid;
    private HashMap<BlockState, Character> characterSet;
    private HashMap<Character, Integer> colourMap;
    private ArrayList<String> asciiMap = new ArrayList<String>();

    public TownMap(int size, BlockPos pos) {
        this.size = size;
        this.grid = new BlockState[size][size];
        this.characterSet = new HashMap<>();
        this.colourMap = new HashMap<>();
        putColours();
        for (int i = 0; i < size; i++) {
            Arrays.fill(this.grid[i], null); // fill with empty block states
        }
    }

    public void putColours() {
        colourMap.put('#', -2039584);
        colourMap.put('/', -2039584);
        colourMap.put('*',  0xFFFF0000);
        colourMap.put('s',  0xFF00FFFF);
        colourMap.put('.',  0xFF2E2E2E);
        colourMap.put(',',  0xFF556B2F);
        colourMap.put('¸',  0xFF66A060);


    }

    public HashMap<Character, Integer> getColourMap() {
        return colourMap;
    }

    public int getSize() {
        return size;
    }

    public HashMap<BlockState, Character> getCharacterSet() {
        return characterSet;
    }

    public void setCharacterSet(HashMap<BlockState, Character> hashMap) {
        this.characterSet = hashMap;
    }

    public void setAsciiMap(ArrayList<String> asciiMap) {
        this.asciiMap = asciiMap;
    }

    public ArrayList<String> getAsciiMap() {
        return asciiMap;
    }

    public BlockState[][] getGrid() {
        return grid;
    }

    public void setSymbolFromBlockState(int x, int y, BlockState state) {
        grid[x][y] = state;
    }
}
