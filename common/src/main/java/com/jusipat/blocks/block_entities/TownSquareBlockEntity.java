package com.jusipat.blocks.block_entities;

import com.jusipat.map.TownMap;
import com.jusipat.map.TownMapGen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.ArrayList;
import java.util.Arrays;

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

    @Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        String result = String.join("}", townMap.getAsciiMap());
        valueOutput.putString("townmap", result);
        valueOutput.putInt("size", townMap.getSize());
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        String[] stringArray = valueInput.getString("townmap").toString().split("}");
        // rebuild ascii map from saved tag string
        ArrayList<String> delimited = new ArrayList<>(Arrays.asList(stringArray));
        townMap.setAsciiMap(delimited);
        townMap.setSize(valueInput.getInt("size").get());
    }

    public void refreshMap() {
        if (this.level != null && !this.level.isClientSide) return;
        this.townMap = TownMapGen.generateMap(this);
    }


}
