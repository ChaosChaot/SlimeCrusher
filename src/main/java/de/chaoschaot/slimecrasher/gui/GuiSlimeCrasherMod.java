package de.chaoschaot.slimecrasher.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

@SideOnly(Side.CLIENT)
public abstract class GuiSlimeCrasherMod extends GuiContainer {

   public GuiSlimeCrasherMod(Container container) {
      super(container);
   }
}
