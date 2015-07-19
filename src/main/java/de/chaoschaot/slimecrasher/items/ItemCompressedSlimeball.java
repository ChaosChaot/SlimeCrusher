package de.chaoschaot.slimecrasher.items;

import cpw.mods.fml.common.registry.GameRegistry;
import de.chaoschaot.slimecrasher.SlimeCrasher;
import de.chaoschaot.slimecrasher.lib.Reference;
import net.minecraft.item.Item;

public class ItemCompressedSlimeball extends Item {

    private String name = "compressedSlimeball";

    public ItemCompressedSlimeball() {
        setUnlocalizedName(Reference.MODID + "_" + name);
        GameRegistry.registerItem(this, name);
        setCreativeTab(SlimeCrasher.slimecrasherTab);
        setTextureName(Reference.MODID + ":" + name);

        this.maxStackSize = 16;
    }
}
