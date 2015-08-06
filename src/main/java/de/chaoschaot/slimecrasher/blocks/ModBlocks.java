package de.chaoschaot.slimecrasher.blocks;


import de.chaoschaot.slimecrasher.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class ModBlocks {

   public static Block loadingStation;
   public static Block compressedSlimeblock;

   public static void init(){
      loadingStation = new BlockLoadingStation();
      compressedSlimeblock = new BlockCompressedSlimeblock();
   }

   public static void initRecipes() {

      CraftingManager crafting = CraftingManager.getInstance();

      crafting.addRecipe(new ItemStack(compressedSlimeblock),
            "SSS",
            "SSS",
            "SSS",

            'S',
            ModItems.compressedSlimeball
      );

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
