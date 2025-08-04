package com.jusipat.client.screens;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class TownSquareMenu extends AbstractContainerMenu {
    public final Container container;
    public TownSquareMenu(int containerID, Container container) {
        super(PlatzMenuTypes.TH_BLOCK.get(), containerID);
        this.container = container;

//        // Player inventory
//        for (int i = 0; i < 3; ++i) {
//            for (int j = 0; j < 9; ++j) {
//                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
//            }
//        }
//        // Player Hotbar
//        for (int i = 0; i < 9; ++i) {
//            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
//        }
    }

//    @Override
//    public ItemStack quickMoveStack(Player player, int index) {
//        ItemStack newStack = ItemStack.EMPTY;
//        Slot slot = this.slots.get(index);
//        if(slot.hasItem()) {
//            ItemStack originalStack = slot.getItem();
//            newStack = originalStack.copy();
//
//            if (index < 36) {
//                if (!moveItemStackTo(originalStack, 36, 36 + 2, false)) {
//                    return ItemStack.EMPTY;
//                }
//            } else if (index < 36 + 2) {
//                if (!moveItemStackTo(originalStack, 0, 36, false)) {
//                    return ItemStack.EMPTY;
//                }
//            } else {
//                return ItemStack.EMPTY;
//            }
//
//            if (originalStack.isEmpty()) {
//                slot.set(ItemStack.EMPTY);
//            } else {
//                slot.setChanged();
//            }
//
//            slot.onTake(player, originalStack);
//        }
//        return newStack;
//    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

}