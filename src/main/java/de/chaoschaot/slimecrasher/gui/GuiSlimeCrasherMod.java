package de.chaoschaot.slimecrasher.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.chaoschaot.slimecrasher.lib.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public abstract class GuiSlimeCrasherMod extends GuiContainer {

   private ResourceLocation guiTexture;

   public GuiSlimeCrasherMod(Container container, String guiTextureName) {
      super(container);
      xSize = 176;
      ySize = 214;
      this.guiTexture = new ResourceLocation(Reference.MODID + ":textures/client/gui/" + guiTextureName + ".png");
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY) {
      mc.getTextureManager().bindTexture(guiTexture);

      this.drawTexturedModalRect(guiLeft,guiTop, 0,0,xSize, ySize);
   }
}
