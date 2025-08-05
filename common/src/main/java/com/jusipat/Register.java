package com.jusipat;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class Register {
    @ExpectPlatform
    public static <T extends BlockEntityType<E>, E extends BlockEntity> Supplier<T> blockEntityType(String id, Supplier<T> sup) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends BlockEntity> BlockEntityType<T> newBlockEntityType(BlockEntityType.BlockEntitySupplier<T> blockEntitySupplier, Block... validBlocks) {
        throw new AssertionError();
    }

    @FunctionalInterface
    public interface BlockEntitySupplier<T extends BlockEntity> {

        @NotNull T create(BlockPos pos, BlockState state);
    }
}
