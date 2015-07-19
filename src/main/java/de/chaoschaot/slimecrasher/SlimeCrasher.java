package de.chaoschaot.slimecrasher;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import de.chaoschaot.slimecrasher.blocks.ModBlocks;
import de.chaoschaot.slimecrasher.gui.GuiHandler;
import de.chaoschaot.slimecrasher.items.ModItems;
import de.chaoschaot.slimecrasher.lib.Reference;
import de.chaoschaot.slimecrasher.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;


@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION)
public class SlimeCrasher {

   @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
   public static CommonProxy proxy;

   @Mod.Instance(Reference.MODID)
   public static SlimeCrasher instance;

    public static CreativeTabs slimecrasherTab = new CreativeTabs(Reference.MODID) {
        @Override
        public Item getTabIconItem() {
            return ModItems.slimeCrasher ;
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ModBlocks.init();
        ModItems.init();
        ModItems.initRecipes();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
       proxy.registerTileEntities();
       NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
