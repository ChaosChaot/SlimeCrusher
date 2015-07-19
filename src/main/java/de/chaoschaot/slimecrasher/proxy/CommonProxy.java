package de.chaoschaot.slimecrasher.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import de.chaoschaot.slimecrasher.tileentities.TileEntityLoadingStation;

public class CommonProxy {
   public void registerTileEntities() {
      GameRegistry.registerTileEntity(TileEntityLoadingStation.class, TileEntityLoadingStation.publicName);
   }
}
