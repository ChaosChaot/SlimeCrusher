package de.chaoschaot.slimecrasher.tileentities;

import de.chaoschaot.slimecrasher.blocks.ModBlocks;
import de.chaoschaot.slimecrasher.items.ItemCompressedSlimeball;
import de.chaoschaot.slimecrasher.items.ItemSlimeCrasher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLoadingStation extends TileEntity implements ISidedInventory {

   public static final String publicName = "tileEntityLoadingStation";
   private String name = "tileEntityLoadingStation";
   protected String customName;

   private ItemStack[] stationStacks = new ItemStack[7];
   private ItemStack testStack;

   private static final int[] slotsTop = new int[] {0, 1, 2, 3, 4, 5};
   private static final int[] slotsBottom = new int[] {};
   private static final int[] slotsSides = new int[] {6};


   public String getName() {
      return name;
   }

   @Override
   public int getSizeInventory() {
      return stationStacks.length;
   }

   @Override
   public ItemStack getStackInSlot(int slot)
   {
      return this.stationStacks[slot];
   }

   @Override
   public ItemStack decrStackSize(int slot, int decreaseAmount)
   {
      /*
      if (this.stationStacks[slot] != null)
      {
         ItemStack itemstack;

         if (this.stationStacks[slot].stackSize <= decreaseAmount)
         {
            itemstack = this.stationStacks[slot];
            this.stationStacks[slot] = null;
            this.markDirty();
            return itemstack;
         }
         else
         {
            itemstack = this.stationStacks[slot].splitStack(decreaseAmount);

            if (this.stationStacks[slot].stackSize == 0)
            {
               this.stationStacks[slot] = null;
            }

            this.markDirty();
            return itemstack;
         }
      }
      else
      {
         return null;
      }
      */
      ItemStack stack = getStackInSlot(slot);
      if (stack != null) {
         if (stack.stackSize <= decreaseAmount) {
            setInventorySlotContents(slot, null);
         } else {
            stack = stack.splitStack(decreaseAmount);
            if (stack.stackSize == 0) {
               setInventorySlotContents(slot, null);
            }
         }
      }
      return stack;
   }

   @Override
   public ItemStack getStackInSlotOnClosing(int slot)
   {
      /*
      if (this.stationStacks[slot] != null)
      {
         ItemStack itemstack = this.stationStacks[slot];
         this.stationStacks[slot] = null;
         return itemstack;
      }
      else
      {
         return null;
      }
      */
      ItemStack stack = getStackInSlot(slot);
      if (stack != null) {
         setInventorySlotContents(slot, null);
      }
      return stack;
   }

   @Override
   public void setInventorySlotContents(int slot, ItemStack stack)
   {
      this.stationStacks[slot] = stack;
      if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
         stack.stackSize = this.getInventoryStackLimit();
      }
   }

   @Override
   public String getInventoryName()
   {
      return this.hasCustomInventoryName() ? this.customName : ModBlocks.loadingStation.getUnlocalizedName() + ".name";
   }

   @Override
   public boolean hasCustomInventoryName()
   {
      return this.customName != null;
   }

   @Override
   public void readFromNBT(NBTTagCompound tag)
   {
      super.readFromNBT(tag);


   }

   @Override
   public void writeToNBT(NBTTagCompound tag)
   {
      super.writeToNBT(tag);

      NBTTagCompound t = new NBTTagCompound();

   }

   @Override
   public int getInventoryStackLimit()
   {
      return 64;
   }

   @Override
   public boolean isUseableByPlayer(EntityPlayer player)
   {
      return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
   }

   @Override
   public void openInventory() {}

   @Override
   public void closeInventory() {}

   @Override
   public boolean isItemValidForSlot(int slot, ItemStack stack)
   {
      return stack != null && (stack.getItem() instanceof ItemCompressedSlimeball || stack.getItem() instanceof ItemSlimeCrasher);
   }

   @Override
   public int[] getAccessibleSlotsFromSide(int side) {
      return side == 0 ? slotsBottom : (side == 1 ? slotsTop : slotsSides);
   }

   @Override
   public boolean canInsertItem(int slot, ItemStack stack, int side) {
      return isItemValidForSlot(slot, stack);
   }

   @Override
   public boolean canExtractItem(int slot, ItemStack stack, int side) {
      return true;
   }
}
