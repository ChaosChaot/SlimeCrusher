package de.chaoschaot.slimecrasher.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import de.chaoschaot.slimecrasher.inventory.ContainerLoadingStation;
import de.chaoschaot.slimecrasher.tileentities.TileEntityLoadingStation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;


public class GuiHandler implements IGuiHandler {

   public enum GuiIDs{
      LOADING_STATION
   }

   @Override
   public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      switch(GuiIDs.values()[ID]){
         case LOADING_STATION:
            return new ContainerLoadingStation(player.inventory, (TileEntityLoadingStation)world.getTileEntity(x,y,z));
      }
      throw new IllegalArgumentException("No gui with id " + ID);
   }

   @Override
   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      switch(GuiIDs.values()[ID]){
         case LOADING_STATION:
            return new GuiLoadingStation(player.inventory, (TileEntityLoadingStation)world.getTileEntity(x,y,z));
      }
      throw new IllegalArgumentException("No gui with id " + ID);
   }
}