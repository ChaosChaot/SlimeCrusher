package de.chaoschaot.slimecrasher.tileentities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.chaoschaot.slimecrasher.blocks.ModBlocks;
import de.chaoschaot.slimecrasher.items.ItemCompressedSlimeball;
import de.chaoschaot.slimecrasher.items.ItemSlimeCrasher;
import de.chaoschaot.slimecrasher.items.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLoadingStation extends TileEntity implements ISidedInventory {

   public static final String publicName = "tileEntityLoadingStation";
   private String name = "tileEntityLoadingStation";

   private ItemStack[] stationStacks = new ItemStack[7];

   private static final int[] slotsTop = new int[] {0, 1, 2, 3, 4, 5};
   private static final int[] slotsBottom = new int[] {};
   private static final int[] slotsSides = new int[] {6};

   private static final int[] inputSlots = new int[] {0, 1, 2, 3, 4, 5};
   private static final int outputSlot = 6;
   /** How much item usage will restored */
   private static final int fuelAmount = 5;
   public int cooldownTicks = 100;

   /** Ticks - How long the Item in input slot will be consumed -> - */
   public int itemUseTicks;
   /** Ticks - How long its actually used -> + */
   public int itemFillTicks;

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
      return  ModBlocks.loadingStation.getUnlocalizedName() + ".name";
   }

   @Override
   public boolean hasCustomInventoryName()
   {
      return false;
   }

   @Override
   public void readFromNBT(NBTTagCompound tag)
   {
      super.readFromNBT(tag);
      itemFillTicks = tag.getInteger("itemFillTicks");
      itemUseTicks = tag.getInteger("itemUseTicks");

      NBTTagList nbttaglist = tag.getTagList("Items", 10);
      this.stationStacks = new ItemStack[this.getSizeInventory()];

      /* TODO: Allow custom name
      if (tag.hasKey("CustomName", 8))
      {
         this.customName = tag.getString("CustomName");
      }
      */

      for (int i = 0; i < nbttaglist.tagCount(); ++i)
      {
         NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
         int j = nbttagcompound1.getByte("Slot") & 255;

         if (j >= 0 && j < this.stationStacks.length)
         {
            this.stationStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
         }
      }

   }

   @Override
   public void writeToNBT(NBTTagCompound tag)
   {
      super.writeToNBT(tag);
      tag.setInteger("itemFillTicks", itemFillTicks);
      tag.setInteger("itemUseTicks", itemUseTicks);

      NBTTagList nbttaglist = new NBTTagList();

      for (int i = 0; i < this.stationStacks.length; ++i)
      {
         if (this.stationStacks[i] != null)
         {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.setByte("Slot", (byte)i);
            this.stationStacks[i].writeToNBT(nbttagcompound1);
            nbttaglist.appendTag(nbttagcompound1);
         }
      }

      tag.setTag("Items", nbttaglist);

   }

   @Override
   public int getInventoryStackLimit()
   {
      return 64;
   }

   @SideOnly(Side.CLIENT)
   public int getFilledProgress(int ticks) {
      return this.itemFillTicks * ticks / 200;
   }



   @Override
   public void updateEntity() {
      if (!worldObj.isRemote) {
         if (itemsInInputSlots() && damagedItemInOutputSlot()) {
            ++this.itemFillTicks;
            /*Todo: Switch f�r Spezialitem einbauen*/
            if (this.itemFillTicks == this.cooldownTicks) {
               this.itemFillTicks = 0;
               if (itemsInInputSlots()) {
                  chooseItem();
               }

               this.markDirty();
            }
         } else {
            this.itemFillTicks = 0;
         }
      }
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
   public boolean isItemValidForSlot(int slot, ItemStack stack) {
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

   public boolean itemsInInputSlots() {
      for(int slot : inputSlots) {
         if(this.stationStacks[slot] != null) {
            return true;
         }
      }
      return false;
   }

   public boolean damagedItemInOutputSlot() {
      if (this.stationStacks[outputSlot] != null) {
         if (this.stationStacks[outputSlot].isItemDamaged()) {
            return true;
         }
      }
      return false;
   }

   /**
    * Handle the selection of items that are
    * allowed to consume!
    */
   public void chooseItem() {
      for(int slot : inputSlots) {
         if(this.stationStacks[slot] != null) {
            if (this.stationStacks[slot].getItem() == ModItems.compressedSlimeball) {
               if(consumeCompressedSlimeball(slot)) {
                  break;
               }
            }
            /** Curently disabled ;)
            else if (this.stationStacks[slot].getUnlocalizedName().equals("item.netherCrystal")) {
               consumeSpecialItem();
            } */
         }
      }
   }

   /**
    * Handle the consume of an compressedSlimeball
    * @return true if consumed
    */
   public boolean consumeCompressedSlimeball(Integer slot) {
      ItemStack slimeCrasher = this.stationStacks[outputSlot];
      if (slimeCrasher != null && slimeCrasher.isItemDamaged()) {
         if (this.stationStacks[slot] != null) {
            if ((--this.stationStacks[slot].stackSize) <= 0) {
               this.stationStacks[slot] = null;
            }
            int max = Math.max(slimeCrasher.getItemDamage() - fuelAmount, 0);
            slimeCrasher.setItemDamage(max);
            return true;
         }
      }
      return false;
   }

   /**
    * Handle the consum of the "special" item :)
    * @return true if item is consumed
    */
   public boolean consumeSpecialItem() {
      ItemStack slimeCrasher = this.stationStacks[outputSlot];
      if (slimeCrasher != null
            && slimeCrasher.isItemDamaged()) {
         for (int slot : inputSlots) {
            ItemStack item = this.stationStacks[slot];
            if (item != null) {
               int max = Math.max(slimeCrasher.getItemDamage() - (item.getMaxDamage() / 2), 0);
               item.setItemDamage(max);

               slimeCrasher.addEnchantment(Enchantment.unbreaking, 2);
               slimeCrasher.addEnchantment(Enchantment.respiration, 2);
               return true;
            }
         }
      }
      return false;
   }
}
