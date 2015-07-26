package de.chaoschaot.slimecrasher.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.chaoschaot.slimecrasher.inventory.slots.SlotCompressedSlimeball;
import de.chaoschaot.slimecrasher.inventory.slots.SlotSlimeCrasher;
import de.chaoschaot.slimecrasher.tileentities.TileEntityLoadingStation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLoadingStation extends ContainerSlimeCrasherMod {

   private TileEntityLoadingStation te;
   private int lastItemFillTime;

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
   public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.inventorySlots.get(slotIndex);

      if (slot != null && slot.getHasStack())
      {
         ItemStack itemstack1 = slot.getStack();
         itemstack = itemstack1.copy();

         if (slotIndex < 7)
         {
            if (!this.mergeItemStack(itemstack1, 7, 41, true))
            {
               return null;
            }
         }
         else
         {
            for (int i = 0; i < 6; i++) {
               Slot shiftedInSlot = (Slot)inventorySlots.get(i);
               if(!shiftedInSlot.getHasStack() && shiftedInSlot.isItemValid(itemstack1)) {
                  this.mergeItemStack(itemstack1, i, i + 1, false);
               }
            }

            Slot shiftedInSlot = (Slot)inventorySlots.get(6);
            if(!shiftedInSlot.getHasStack() && shiftedInSlot.isItemValid(itemstack1)) {
               this.mergeItemStack(itemstack1, 6, 7, false);
            }
         }

         if (itemstack1.stackSize == 0)
         {
            slot.putStack((ItemStack)null);
         }
         else
         {
            slot.onSlotChanged();
         }

         if (itemstack1.stackSize == itemstack.stackSize)
         {
            return null;
         }

         slot.onPickupFromSlot(player, itemstack1);
      }

      return itemstack;
   }

   public void addCraftingToCrafters(ICrafting inventory)
   {
      super.addCraftingToCrafters(inventory);
      inventory.sendProgressBarUpdate(this, 0, this.te.itemFillTicks);

   }

   public void detectAndSendChanges()
   {
      super.detectAndSendChanges();

      for (int i = 0; i < this.crafters.size(); ++i)
      {
         ICrafting icrafting = (ICrafting)this.crafters.get(i);

         if (this.lastItemFillTime != this.te.itemFillTicks)
         {
            icrafting.sendProgressBarUpdate(this, 0, this.te.itemFillTicks);
         }

      }

      this.lastItemFillTime = this.te.itemFillTicks;

   }

   @SideOnly(Side.CLIENT)
   public void updateProgressBar(int last, int tick)
   {
      if (last == 0)
      {
         this.te.itemFillTicks = tick;
      }
   }
}
