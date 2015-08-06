package de.chaoschaot.slimecrasher.items;

import de.chaoschaot.slimecrasher.blocks.ModBlocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.util.EnumHelper;


public class ModItems {

    public static Item slimeCrasher;
    public static Item compressedSlimeball;

    public static void init() {

        slimeCrasher    = new ItemSlimeCrasher();
        compressedSlimeball = new ItemCompressedSlimeball();
    }

    public static void initRecipes()
    {
        CraftingManager crafting = CraftingManager.getInstance();

        crafting.addRecipe(new ItemStack(compressedSlimeball),
                "SSS",
                "SES",
                "SSS",

                'S',
                Items.slime_ball,

                'E',
                Items.ender_pearl
        );

        crafting.addRecipe(new ItemStack(slimeCrasher),
                " CC",
                " LC",
                "S  ",

                'C',
                compressedSlimeball,

                'L',
                new ItemStack(Items.dye,1,4),

                'S',
                Items.stick
        );

       crafting.addShapelessRecipe(new ItemStack(ModItems.compressedSlimeball,9), new ItemStack(ModBlocks.compressedSlimeblock,1));
    }

    public static Item.ToolMaterial compressedslime = EnumHelper.addToolMaterial("compressedslime",0,100,2.0F,0.0F,5);

}
