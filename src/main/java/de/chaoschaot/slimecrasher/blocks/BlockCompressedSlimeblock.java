package de.chaoschaot.slimecrasher.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import de.chaoschaot.slimecrasher.SlimeCrasher;
import de.chaoschaot.slimecrasher.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCompressedSlimeblock extends Block {

   private String name = "compressedSlimeblock";

   protected BlockCompressedSlimeblock() {
      super(Material.clay);

      this.setStepSound(soundTypeCloth);
      this.setHardness(1.0F);

      this.setBlockName(Reference.MODID + "_" + name);
      this.setCreativeTab(SlimeCrasher.slimecrasherTab);
      this.setBlockTextureName(Reference.MODID + ":" + name);

      GameRegistry.registerBlock(this, name);
   }

   @Override
   public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
      Random random = world.rand;
      double d0 = 0.0625D;
      for (int l = 0; l < 12; ++l) {
         double d1 = (double) ((float) x + random.nextFloat() + d0);
         double d2 = (double) ((float) y + random.nextFloat() + d0);
         double d3 = (double) ((float) z + random.nextFloat() + d0);
         world.spawnParticle("slime", d1, d2, d3 + d0, 0.0D, 0.0D, 0.0D);
      }
   }
}
