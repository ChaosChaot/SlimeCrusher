package de.chaoschaot.slimecrasher.inventory;

import de.chaoschaot.slimecrasher.tileentities.TileEntityLoadingStation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class ContainerLoadingStation extends ContainerSlimeCrasherMod {

   private TileEntityLoadingStation te;

   public ContainerLoadingStation(InventoryPlayer playerInventory, TileEntityLoadingStation te) {
   /*
      this.addSlotToContainer(new Slot(te, 0, 50, 50));
      this.addSlotToContainer(new Slot(te, 1, 50, 30));
      this.addSlotToContainer(new Slot(te, 2, 50, 10));
      this.addSlotToContainer(new Slot(te, 3, 30, 50));
      this.addSlotToContainer(new Slot(te, 4, 10, 50));
      this.addSlotToContainer(new Slot(te, 5, 30, 30));
      this.addSlotToContainer(new Slot(te, 6, 10, 30));
   */

      this.addPlayerSlots(playerInventory, 8, 84);
      this.te = te;
   }

   @Override
   public boolean canInteractWith(EntityPlayer player) {
      return te.isUseableByPlayer(player);
   }


   @Override
   public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
      return null;
   }
}
