package de.chaoschaot.slimecrasher.inventory.slots;

import de.chaoschaot.slimecrasher.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Chaos on 20.07.2015.
 */
public class SlotSlimeCrasher extends Slot {
   public SlotSlimeCrasher(IInventory te, int slot, int x, int y) {
      super(te, slot, x, y);
   }

   @Override
   public boolean isItemValid(ItemStack stack) {
      return stack.getItem() == ModItems.slimeCrasher;
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
