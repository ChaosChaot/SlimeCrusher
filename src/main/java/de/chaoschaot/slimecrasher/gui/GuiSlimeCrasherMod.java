package de.chaoschaot.slimecrasher.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.chaoschaot.slimecrasher.lib.Reference;
import de.chaoschaot.slimecrasher.tileentities.TileEntityLoadingStation;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public abstract class GuiSlimeCrasherMod extends GuiContainer {

   private final ResourceLocation guiTexture;
   private final IInventory inventory;
   private TileEntityLoadingStation tileLoadingStation;

   public GuiSlimeCrasherMod(Container container, String guiTextureName, IInventory inventory) {
      super(container);
      xSize = 176;
      ySize = 214;
      this.guiTexture = new ResourceLocation(Reference.GUI_TEXTURE_PATH + guiTextureName + ".png");
      this.inventory = inventory;
      this.tileLoadingStation = (TileEntityLoadingStation)inventory;
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      mc.getTextureManager().bindTexture(guiTexture);
      this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);


         int i1 = this.tileLoadingStation.getFilledProgress(48);
         this.drawTexturedModalRect(guiLeft + 74, guiTop + 61, 178, 2, 29, i1 + 1);



   }

   @Override
   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
      String s = inventory.hasCustomInventoryName() ? inventory.getInventoryName() : I18n.format(inventory.getInventoryName());
      fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
      fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);

   }
}
