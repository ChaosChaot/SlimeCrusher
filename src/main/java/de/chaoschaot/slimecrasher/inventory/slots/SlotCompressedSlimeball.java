package de.chaoschaot.slimecrasher.inventory.slots;

import de.chaoschaot.slimecrasher.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCompressedSlimeball extends Slot {

   public SlotCompressedSlimeball(IInventory te, int slot, int x, int y) {
      super(te, slot, x, y);
   }

   @Override
   public boolean isItemValid(ItemStack stack) {

      return (stack.getItem() == ModItems.compressedSlimeball /* ||
            stack.getUnlocalizedName().equals("item.netherCrystal")*/);
   }

   @Override
   public ItemStack decrStackSize(int amount) {
      return super.decrStackSize(amount);
   }

   @Override
   public boolean canTakeStack(EntityPlayer player) {
      return true;
   }
}
