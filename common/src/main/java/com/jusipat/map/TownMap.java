package com.jusipat.map;

import net.minecraft.core.BlockPos;

import java.util.Arrays;

public class TownMap {
    private int size;
    private BlockPos pos;
    private char[][] grid;
    private int beautyScore = 0;

    public TownMap(int size, BlockPos pos) {
        this.size = size;
        this.pos = pos;
        this.grid = new char[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(this.grid[i], '.');
        }
    }

    public int getSize() {
        return size;
    }

    public BlockPos getPos() {
        return pos;
    }

    public int getBeautyScore() {
        return beautyScore;
    }

    public char[][] getGrid() {
        return grid;
    }

    public void setSymbol(int x, int y, char symbol) {
        grid[x][y] = symbol;
    }

    public void updateBeautyScore() {
        for (int dx = 0; dx < grid.length; dx++) {
            for (int dy = 0; dy < grid[dx].length; dy++) {
                if (grid[dx][dy] != '.') {
                    beautyScore++;
                }
            }
        }
    }

}
