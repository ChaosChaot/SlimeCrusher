package de.chaoschaot.slimecrasher.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import de.chaoschaot.slimecrasher.SlimeCrasher;
import de.chaoschaot.slimecrasher.gui.GuiHandler;
import de.chaoschaot.slimecrasher.lib.Reference;
import de.chaoschaot.slimecrasher.tileentities.TileEntityLoadingStation;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockLoadingStation extends Block implements ITileEntityProvider {

   private String name = "loadingStation";
   protected Random direction = new Random();

   public BlockLoadingStation() {
      super(Material.anvil);

      this.setBlockName(Reference.MODID + "_" + name);
      this.setCreativeTab(SlimeCrasher.slimecrasherTab);
      this.setBlockTextureName(Reference.MODID + ":" + name);

      GameRegistry.registerBlock(this, name);
   }

   @Override
   public TileEntity createNewTileEntity(World world, int meta) {
      return new TileEntityLoadingStation();
   }

   @Override
   public boolean hasTileEntity(int metadata) {
      return true;
   }

   @Override
   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
      if (!world.isRemote) {
         if (world.getTileEntity(x,y,z) != null) {
            player.openGui(SlimeCrasher.instance, GuiHandler.GuiIDs.LOADING_STATION.ordinal(), world, x, y, z);
         }
         return true;
      }
      return true;
   }

   @Override
   public void breakBlock(World world, int x, int y, int z, Block block, int hasTileEntity)
   {
      TileEntityLoadingStation tileEntityLoadingStation = (TileEntityLoadingStation)world.getTileEntity(x, y, z);

      if (tileEntityLoadingStation != null)
      {
         for (int i1 = 0; i1 < tileEntityLoadingStation.getSizeInventory(); ++i1)
         {
            ItemStack itemstack = tileEntityLoadingStation.getStackInSlot(i1);

            if (itemstack != null)
            {
               float f = this.direction.nextFloat() * 0.8F + 0.1F;
               float f1 = this.direction.nextFloat() * 0.8F + 0.1F;
               float f2 = this.direction.nextFloat() * 0.8F + 0.1F;

               while (itemstack.stackSize > 0)
               {
                  int j1 = this.direction.nextInt(21) + 10;

                  if (j1 > itemstack.stackSize)
                  {
                     j1 = itemstack.stackSize;
                  }

                  itemstack.stackSize -= j1;
                  EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                  if (itemstack.hasTagCompound())
                  {
                     entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                  }

                  float f3 = 0.05F;
                  entityitem.motionX = (double)((float)this.direction.nextGaussian() * f3);
                  entityitem.motionY = (double)((float)this.direction.nextGaussian() * f3 + 0.2F);
                  entityitem.motionZ = (double)((float)this.direction.nextGaussian() * f3);
                  world.spawnEntityInWorld(entityitem);
               }
            }
         }

         world.func_147453_f(x, y, z, block);
      }

      super.breakBlock(world, x, y, z, block, hasTileEntity);
   }

}
