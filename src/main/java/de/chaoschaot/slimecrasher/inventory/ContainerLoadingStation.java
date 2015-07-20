package de.chaoschaot.slimecrasher.inventory;

import de.chaoschaot.slimecrasher.inventory.slots.SlotCompressedSlimeball;
import de.chaoschaot.slimecrasher.inventory.slots.SlotSlimeCrasher;
import de.chaoschaot.slimecrasher.tileentities.TileEntityLoadingStation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class ContainerLoadingStation extends ContainerSlimeCrasherMod {

   private TileEntityLoadingStation te;

   public ContainerLoadingStation(InventoryPlayer playerInventory, TileEntityLoadingStation te) {

      this.addSlotToContainer(new SlotCompressedSlimeball(te, 0, 62, 20));
      this.addSlotToContainer(new SlotCompressedSlimeball(te, 1, 80, 20));
      this.addSlotToContainer(new SlotCompressedSlimeball(te, 2, 98, 20));
      this.addSlotToContainer(new SlotCompressedSlimeball(te, 3, 62, 38));
      this.addSlotToContainer(new SlotCompressedSlimeball(te, 4, 80, 38));
      this.addSlotToContainer(new SlotCompressedSlimeball(te, 5, 98, 38));
      this.addSlotToContainer(new SlotSlimeCrasher(te, 6, 80, 96));


      this.addPlayerSlots(playerInventory, 8, 132);
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
