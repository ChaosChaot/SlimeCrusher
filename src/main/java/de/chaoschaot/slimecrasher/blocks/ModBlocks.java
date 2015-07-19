package de.chaoschaot.slimecrasher.blocks;


import net.minecraft.block.Block;

public class ModBlocks {

   public static Block loadingStation;

   public static void init(){
      loadingStation = new BlockLoadingStation();
   }
}
