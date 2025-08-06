package com.jusipat.client.screens;

import com.jusipat.Platz;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;

public class TownSquareScreen extends AbstractContainerScreen<TownSquareMenu> {

    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(Platz.MOD_ID, "textures/gui/container/town_square.png");

    public TownSquareScreen(TownSquareMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 230;
        this.imageHeight = 219;
        //this.inventoryLabelY = -99; // hide inventory label
        if (menu.getTownSquareBlockEntity() != null) {
            menu.getTownSquareBlockEntity().refreshMap();
        }
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
        guiGraphics.drawString(this.font, String.valueOf(menu.getTownSquareBlockEntity().getTownMap().getBeautyScore()), this.leftPos + 125, this.topPos + 30, 0xFFFFFFFF);

    }
}