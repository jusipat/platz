package com.jusipat.client.screens;

import com.google.common.collect.Lists;
import com.jusipat.Platz;
import com.jusipat.netcode.GossipPacket;
import commonnetwork.api.Dispatcher;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import org.apache.commons.lang3.tuple.Pair;

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
        guiGraphics.drawString(this.font, String.valueOf((int) menu.getTownSquareBlockEntity().getTownMap().getBeautyScore()), this.leftPos + 125, this.topPos + 34, 0xFFFFFFFF);
        guiGraphics.drawString(this.font, String.valueOf(menu.getTownSquareBlockEntity().getTrackedEntity(EntityType.VILLAGER, 50).size()), this.leftPos + 130, this.topPos + 50, 0xFFFFFFFF);
        guiGraphics.drawString(this.font, String.valueOf(menu.getTownSquareBlockEntity().getTrackedEntity(EntityType.IRON_GOLEM, 50).size()), this.leftPos + 130, this.topPos + 70, 0xFFFFFFFF);
    }

    @Environment(EnvType.CLIENT)
    abstract static class TownSquareScreenButton extends AbstractButton implements TownSquareButton {
        private boolean selected;

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

    // provides a dynamic component for the Tooltip.create method used in power button constructors
    private Component buildTooltipWithStatus(Component title, Component description, List<Pair<Boolean, MutableComponent>> conditions) {
        MutableComponent component = Component.empty()
                .append(title)
                .append("\n")
                .append(description);

        if (!conditions.isEmpty()) {
            component.append("\n\n");

            for (Pair<Boolean, MutableComponent> condition : conditions) {
                boolean isMet = condition.getLeft();
                MutableComponent conditionText = condition.getRight();

                component.append(
                        Component.literal(isMet ? "✓ " : "✗ ")
                                .withStyle(isMet ? ChatFormatting.GREEN : ChatFormatting.RED)
                                .append(conditionText.withStyle(ChatFormatting.GRAY))
                                .append("\n"));
            }
        }

        return component;
    }

    private List<Pair<Boolean, MutableComponent>> conditionList(final int v, final int g, final int b) {
        return List.of(
                Pair.of(menu.getTownSquareBlockEntity().getTrackedEntity(EntityType.VILLAGER, 50).size() >= v,
                        Component.translatable(Platz.MOD_ID + ".condition.villagers", v)),
                Pair.of(menu.getTownSquareBlockEntity().getTrackedEntity(EntityType.IRON_GOLEM, 50).size() >= g,
                        Component.translatable(Platz.MOD_ID + ".condition.golems", g)),
                Pair.of(menu.getTownSquareBlockEntity().getTownMap().getBeautyScore() >= b,
                        Component.translatable(Platz.MOD_ID + ".condition.beauty", b))
        );
    }

    @Override
    protected void init() {
        super.init();
        this.buttons.clear();

        List<Pair<Boolean, MutableComponent>> l1 = conditionList(15, 3, 1000);
        this.addTownSquareButton(new TownSquarePowerButton(this.leftPos + 150, this.topPos + 27,
                BARGAIN_SPRITE,
                Tooltip.create(buildTooltipWithStatus(
                        Component.translatable(Platz.MOD_ID + ".container.tooltip.bargain").withStyle(ChatFormatting.GOLD),
                        Component.translatable(Platz.MOD_ID + ".container.tooltip.bargain.desc").withStyle(ChatFormatting.GRAY),
                        l1
                )), l1.stream().allMatch(Pair::getLeft))); // check if all conditions are met to set button active

        List<Pair<Boolean, MutableComponent>> l2 = conditionList(20, 2, 1250);
        this.addTownSquareButton(new TownSquarePowerButton(this.leftPos + 170, this.topPos + 27,
                TRADERDEAL_SPRITE, Tooltip.create(buildTooltipWithStatus(Component.translatable(Platz.MOD_ID + ".container.tooltip.deal").withStyle(ChatFormatting.GOLD),
                Component.translatable(Platz.MOD_ID + ".container.tooltip.deal.desc").withStyle(ChatFormatting.GRAY),
                l2
        )), l2.stream().allMatch(Pair::getLeft)));

        List<Pair<Boolean, MutableComponent>> l3 = conditionList(15, 3, 1000);
        this.addTownSquareButton(new TownSquarePowerButton(this.leftPos + 150, this.topPos + 47,
                GOLEMBUFF_SPRITE, Tooltip.create(buildTooltipWithStatus(Component.translatable(Platz.MOD_ID + ".container.tooltip.golem").withStyle(ChatFormatting.YELLOW),
                Component.translatable(Platz.MOD_ID + ".container.tooltip.golem.desc").withStyle(ChatFormatting.GRAY),
                l3
        )), l3.stream().allMatch(Pair::getLeft)));

        List<Pair<Boolean, MutableComponent>> l4 = conditionList(15, 3, 1000);
        this.addTownSquareButton(new TownSquarePowerButton(this.leftPos + 170, this.topPos + 47,
                ANTIRAID_SPRITE, Tooltip.create(buildTooltipWithStatus(Component.translatable(Platz.MOD_ID + ".container.tooltip.antiraid").withStyle(ChatFormatting.YELLOW),
                Component.translatable(Platz.MOD_ID + ".container.tooltip.antiraid.desc").withStyle(ChatFormatting.GRAY),
                l4
        )), l4.stream().allMatch(Pair::getLeft)));

        List<Pair<Boolean, MutableComponent>> l5 = conditionList(15, 3, 1000);
        this.addTownSquareButton(new TownSquarePowerButton(this.leftPos + 150, this.topPos + 67,
                CROPBUFF_SPRITE, Tooltip.create(buildTooltipWithStatus(Component.translatable(Platz.MOD_ID + ".container.tooltip.crop").withStyle(ChatFormatting.GREEN),
                Component.translatable(Platz.MOD_ID + ".container.tooltip.crop.desc").withStyle(ChatFormatting.GRAY),
                l5
        )), l5.stream().allMatch(Pair::getLeft)));

        List<Pair<Boolean, MutableComponent>> l6 = conditionList(15, 3, 1000);
        this.addTownSquareButton(new TownSquarePowerButton(this.leftPos + 170, this.topPos + 67,
                LUCKBUFF_SPRITE, Tooltip.create(buildTooltipWithStatus(Component.translatable(Platz.MOD_ID + ".container.tooltip.luck").withStyle(ChatFormatting.GREEN),
                Component.translatable(Platz.MOD_ID + ".container.tooltip.luck.desc").withStyle(ChatFormatting.GRAY),
                l6
        )), l6.stream().allMatch(Pair::getLeft)));
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
        public TownSquarePowerButton(final int i, final int j, ResourceLocation res, Tooltip tooltip, boolean conditionsMet) {
            super(i, j, res, CommonComponents.EMPTY);
            this.setTooltip(tooltip); // only sets one tooltip with one component, does not accept a List<Tooltip>, either
            this.active = conditionsMet;
        }

        public void onPress() {
            Dispatcher.sendToServer(new GossipPacket()); // todo: testing packets
            // TownSquareScreen.this.minecraft.player.closeContainer();
        }

        public void updateStatus(int i) {}
    }

    @Environment(EnvType.CLIENT)
    interface TownSquareButton {
        void updateStatus(int i);
    }
}