package de.chaoschaot.slimecrasher.gui;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.chaoschaot.slimecrasher.inventory.ContainerLoadingStation;
import de.chaoschaot.slimecrasher.tileentities.TileEntityLoadingStation;
import net.minecraft.entity.player.InventoryPlayer;

@SideOnly(Side.CLIENT)
public class GuiLoadingStation extends GuiSlimeCrasherMod {

   public GuiLoadingStation(InventoryPlayer playerInventory, TileEntityLoadingStation te) {
      super(new ContainerLoadingStation(playerInventory, te),"GuiLoadingStation",te);
   }

}
