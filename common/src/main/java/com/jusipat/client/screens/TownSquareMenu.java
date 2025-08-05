    package com.jusipat.client.screens;

    import com.jusipat.blocks.block_entities.TownSquareBlockEntity;
    import net.minecraft.network.FriendlyByteBuf;
    import net.minecraft.world.entity.player.Inventory;
    import net.minecraft.world.entity.player.Player;
    import net.minecraft.world.inventory.AbstractContainerMenu;
    import net.minecraft.world.item.ItemStack;
    import net.minecraft.world.level.block.entity.BlockEntity;

    public class TownSquareMenu extends AbstractContainerMenu {
        private final TownSquareBlockEntity townSquareBlockEntity;

        public TownSquareMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
            this(containerId, playerInventory,
                    playerInventory.player.level().getBlockEntity(extraData.readBlockPos())
            );
        }

        public TownSquareMenu(int containerId, Inventory playerInventory, BlockEntity blockEntity) {
            super(PlatzMenuTypes.TH_BLOCK.get(), containerId);
            this.townSquareBlockEntity = (TownSquareBlockEntity) blockEntity;
        }

        public TownSquareBlockEntity getTownSquareBlockEntity() {
            return townSquareBlockEntity;
        }

        @Override
        public ItemStack quickMoveStack(Player player, int i) {
            return null;
        }

        @Override
        public boolean stillValid(Player player) {
            return true;
        }

    }