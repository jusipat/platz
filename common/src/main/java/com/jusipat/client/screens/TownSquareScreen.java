package com.jusipat.client.screens;

import com.google.common.collect.Lists;
import com.jusipat.Platz;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.BeaconScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class TownSquareScreen extends AbstractContainerScreen<TownSquareMenu> {

    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(Platz.MOD_ID, "textures/gui/container/town_square.png");
    static final ResourceLocation BARGAIN_SPRITE = ResourceLocation.fromNamespaceAndPath(Platz.MOD_ID, "textures/gui/container/bargain.png");
    static final ResourceLocation TRADERDEAL_SPRITE = ResourceLocation.fromNamespaceAndPath(Platz.MOD_ID, "textures/gui/container/trader_deal.png");
    static final ResourceLocation GOLEMBUFF_SPRITE = ResourceLocation.fromNamespaceAndPath(Platz.MOD_ID, "textures/gui/container/golem_buff.png");
    static final ResourceLocation ANTIRAID_SPRITE = ResourceLocation.fromNamespaceAndPath(Platz.MOD_ID, "textures/gui/container/antiraid.png");
    static final ResourceLocation CROPBUFF_SPRITE = ResourceLocation.fromNamespaceAndPath(Platz.MOD_ID, "textures/gui/container/crop_buff.png");
    static final ResourceLocation LUCKBUFF_SPRITE = ResourceLocation.fromNamespaceAndPath(Platz.MOD_ID, "textures/gui/container/luck_buff.png");

    static final ResourceLocation BUTTON_DISABLED_SPRITE = ResourceLocation.withDefaultNamespace("container/beacon/button_disabled");
    static final ResourceLocation BUTTON_SELECTED_SPRITE = ResourceLocation.withDefaultNamespace("container/beacon/button_selected");
    static final ResourceLocation BUTTON_HIGHLIGHTED_SPRITE = ResourceLocation.withDefaultNamespace("container/beacon/button_highlighted");
    static final ResourceLocation BUTTON_SPRITE = ResourceLocation.withDefaultNamespace("container/beacon/button");
    static final ResourceLocation CONFIRM_SPRITE = ResourceLocation.withDefaultNamespace("container/beacon/confirm");
    private final List<TownSquareButton> buttons = Lists.newArrayList();

    public TownSquareScreen(TownSquareMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 230;
        this.imageHeight = 219;
        if (menu.getTownSquareBlockEntity() != null) {
            menu.getTownSquareBlockEntity().refreshMap(); // refresh on open screen
        }
    }

    private <T extends AbstractWidget & TownSquareButton> void addTownSquareButton(T abstractWidget) {
        this.addRenderableWidget(abstractWidget);
        this.buttons.add(abstractWidget);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        //RenderSystem.setShader(GameRenderer::getPositionTexShader); 1.21.1 and earlier
        //RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, x, y, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256); // 1.21.2 and later
        //guiGraphics.blit(BACKGROUND, x, y, 0, 0, imageWidth, imageHeight); // 1.21.1 and earlier
    }

    public void drawAsciiMap(GuiGraphics guiGraphics, Font font, ArrayList<String> rowStrings, int startX, int startY, int cellWidth, int cellHeight) {
        for (int row = 0; row < rowStrings.size(); row++) {
            String line = rowStrings.get(row);
            for (int col = 0; col < line.length(); col++) {
                char c = line.charAt(col);
                int x = startX + col * cellWidth;
                int y = startY + row * cellHeight;
                guiGraphics.drawString(font, String.valueOf(c), x, y,
                        menu.getTownSquareBlockEntity().getTownMap().getColourMap().getOrDefault(c, 0xFF2E2E2E));
            }
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int i, int j) {
        drawAsciiMap(
                guiGraphics,
                this.font,
                menu.getTownSquareBlockEntity().getTownMap().getAsciiMap(),
                15, 3,
                3, 3
        );
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
        guiGraphics.drawString(this.font, Component.translatable(Platz.MOD_ID + ".container.town_square"), this.leftPos + 125, this.topPos + 10, 0xFFFFFFFF);
        guiGraphics.drawString(this.font, String.valueOf((int) menu.getTownSquareBlockEntity().getTownMap().getBeautyScore()), this.leftPos + 125, this.topPos + 30, 0xFFFFFFFF);
        guiGraphics.drawString(this.font, String.valueOf(menu.getTownSquareBlockEntity().getTrackedEntity(EntityType.VILLAGER, 50).size()), this.leftPos + 125, this.topPos + 50, 0xFFFFFFFF);
        guiGraphics.drawString(this.font, String.valueOf(menu.getTownSquareBlockEntity().getTrackedEntity(EntityType.IRON_GOLEM, 50).size()), this.leftPos + 125, this.topPos + 70, 0xFFFFFFFF);
    }

    @Environment(EnvType.CLIENT)
    abstract static class TownSquareScreenButton extends AbstractButton implements TownSquareButton {
        private boolean selected;

        protected TownSquareScreenButton(int i, int j) {
            super(i, j, 22, 22, CommonComponents.EMPTY);
        }

        protected TownSquareScreenButton(int i, int j, Component component) {
            super(i, j, 22, 22, component);
            this.active = false; // disabled by default
        }

        public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
            ResourceLocation resourceLocation;
            if (!this.active) {
                resourceLocation = TownSquareScreen.BUTTON_DISABLED_SPRITE;
            } else if (this.selected) {
                resourceLocation = TownSquareScreen.BUTTON_SELECTED_SPRITE;
            } else if (this.isHoveredOrFocused()) {
                resourceLocation = TownSquareScreen.BUTTON_HIGHLIGHTED_SPRITE;
            } else {
                resourceLocation = TownSquareScreen.BUTTON_SPRITE;
            }

            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, resourceLocation, this.getX(), this.getY(), this.width, this.height);
            this.renderIcon(guiGraphics);
        }

        protected abstract void renderIcon(GuiGraphics guiGraphics);

        public boolean isSelected() {
            return this.selected;
        }

        public void setSelected(boolean bl) {
            this.selected = bl;
        }

        public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
            this.defaultButtonNarrationText(narrationElementOutput);
        }
    }

    @Override
    protected void init() {
        super.init();
        this.buttons.clear();
        this.addTownSquareButton(new TownSquarePowerButton(this.leftPos + 150, this.topPos + 27,
                BARGAIN_SPRITE, Tooltip.create(Component.translatable(Platz.MOD_ID + ".container.tooltip.bargain"))));
        this.addTownSquareButton(new TownSquarePowerButton(this.leftPos + 170, this.topPos + 27,
                TRADERDEAL_SPRITE, Tooltip.create(Component.translatable(Platz.MOD_ID + ".container.tooltip.deal"))));
        this.addTownSquareButton(new TownSquarePowerButton(this.leftPos + 150, this.topPos + 47,
                GOLEMBUFF_SPRITE, Tooltip.create(Component.translatable(Platz.MOD_ID + ".container.tooltip.golem"))));
        this.addTownSquareButton(new TownSquarePowerButton(this.leftPos + 170, this.topPos + 47,
                ANTIRAID_SPRITE, Tooltip.create(Component.translatable(Platz.MOD_ID + ".container.tooltip.antiraid"))));
        this.addTownSquareButton(new TownSquarePowerButton(this.leftPos + 150, this.topPos + 67,
                CROPBUFF_SPRITE, Tooltip.create(Component.translatable(Platz.MOD_ID + ".container.tooltip.crop"))));
        this.addTownSquareButton(new TownSquarePowerButton(this.leftPos + 170, this.topPos + 67,
                LUCKBUFF_SPRITE, Tooltip.create(Component.translatable(Platz.MOD_ID + ".container.tooltip.luck"))));
    }

    @Environment(EnvType.CLIENT)
    abstract static class TownSquareSpriteScreenButton extends TownSquareScreenButton {
        private final ResourceLocation sprite;

        protected TownSquareSpriteScreenButton(int i, int j, ResourceLocation resourceLocation, Component component) {
            super(i, j, component);
            this.sprite = resourceLocation;
        }

        protected void renderIcon(GuiGraphics guiGraphics) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.sprite, this.getX()+2, this.getY(), 0.0F, 0.0F, 18, 18, 18, 18); // 1.21.2 and later
        }
    }

    @Environment(EnvType.CLIENT)
    class TownSquarePowerButton extends TownSquareSpriteScreenButton {
        public TownSquarePowerButton(final int i, final int j, ResourceLocation res, Tooltip tooltip) {
            super(i, j, res, CommonComponents.EMPTY);
            this.setTooltip(tooltip);

        }

        public void onPress() {
            TownSquareScreen.this.minecraft.player.closeContainer();
        }

        public void updateStatus(int i) {
            //this.active = ((TownSquareMenu) TownSquareScreen.this.menu).hasPayment() && TownSquareScreen.this.primary != null;
        }
    }

    @Environment(EnvType.CLIENT)
    interface TownSquareButton {
        void updateStatus(int i);
    }
}