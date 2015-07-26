package de.chaoschaot.slimecrasher.blocks;


import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class ModBlocks {

   public static Block loadingStation;

   public static void init(){
      loadingStation = new BlockLoadingStation();
   }

   public static void initRecipes() {

      CraftingManager crafting = CraftingManager.getInstance();

      crafting.addRecipe(new ItemStack(loadingStation),
         "SHS",
         "SRS",
         "SES",

         'S',
         Blocks.stone,

         'H',
         Blocks.hopper,

         'R',
         Items.redstone,

         'E',
         Items.ender_pearl
      );
   }
}
