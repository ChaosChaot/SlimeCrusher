package de.chaoschaot.slimecrasher.gui;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.chaoschaot.slimecrasher.inventory.ContainerLoadingStation;
import de.chaoschaot.slimecrasher.tileentities.TileEntityLoadingStation;
import net.minecraft.entity.player.InventoryPlayer;

@SideOnly(Side.CLIENT)
public class GuiLoadingStation extends GuiSlimeCrasherMod {

   public GuiLoadingStation(InventoryPlayer playerInventory, TileEntityLoadingStation te) {
      super(new ContainerLoadingStation(playerInventory, te));
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {

   }
}
