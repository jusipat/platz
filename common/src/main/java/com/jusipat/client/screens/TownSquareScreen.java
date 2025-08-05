package com.jusipat.client.screens;

import com.jusipat.Platz;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class TownSquareScreen extends AbstractContainerScreen<TownSquareMenu> {

    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(Platz.MOD_ID, "textures/gui/container/town_square.png");

    public TownSquareScreen(TownSquareMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 230;
        this.imageHeight = 219;
        this.inventoryLabelY = -99; // hide inventory label
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        //RenderSystem.setShader(GameRenderer::getPositionTexShader);       1.21.1 and earlier
        //RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, x, y, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256); // 1.21.2 and later
        //guiGraphics.blit(BACKGROUND, x, y, 0, 0, imageWidth, imageHeight); // 1.21.1 and earlier
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int i, int j) {
        // todo: add ascii map rendering
        //System.err.println(menu.getTownSquareBlockEntity().getTownMap().getArray().toString());
        //for (String rowString : menu.getMapData().getArray()) {
            //System.err.println(rowString + '\n');
            //guiGraphics.drawCenteredString(this.font, Component.literal(rowString), 62, 10, -2039584);
        //}
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}