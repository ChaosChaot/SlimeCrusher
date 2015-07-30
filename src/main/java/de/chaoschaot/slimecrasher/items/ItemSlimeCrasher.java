package de.chaoschaot.slimecrasher.items;

import com.google.common.collect.Sets;
import cpw.mods.fml.common.registry.GameRegistry;
import de.chaoschaot.slimecrasher.SlimeCrasher;
import de.chaoschaot.slimecrasher.lib.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;


public class ItemSlimeCrasher extends ItemTool {

   private static final Set harvestable = Sets.newHashSet();
   private String name = "slimeCrasher";

   private boolean isJumpActive = false;
   private EntityPlayer player;
   private int tickTimer = 40;

   public ItemSlimeCrasher() {
      super(0.0F, ModItems.compressedslime, harvestable);

      setUnlocalizedName(Reference.MODID + "_" + name);
      GameRegistry.registerItem(this, name);
      setCreativeTab(SlimeCrasher.slimecrasherTab);
      setTextureName(Reference.MODID + ":" + name);

      this.maxStackSize = 1;

   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
      this.player = player;
      if (!world.isRemote) {
         if (player.isSneaking()) {
            if (getIsJumpActive()) {
               setIsJumpActive(false);
            } else {
               setIsJumpActive(true);
            }

         }
      }

      if (world.isRemote) {
         player.swingItem();
      }

      AxisAlignedBB playerBounding = player.boundingBox.expand(32, 32, 32); //AxisAlignedBB.getBoundingBox(player.posX - 32, player.posY - 32, player.posZ - 32, 64, 64, 64)
      List<EntityLiving> entities = world.getEntitiesWithinAABB(EntityLiving.class, playerBounding);
      int slimeKillCounter = 0;

      for (EntityLiving e : entities) {

         if (e instanceof EntitySlime) {
            // pink fluffy unicorns ~ thx to NPException
            if (itemStack.getItemDamage() >= itemStack.getMaxDamage()) {
               break;
            }
            itemStack.setItemDamage(itemStack.getItemDamage() + 1);
            if (world.isRemote) {
               e.spawnExplosionParticle();
            } else {
               e.setDead();
               slimeKillCounter++;
            }

         }

      }
      if (!world.isRemote && slimeKillCounter > 0) {
         SlimeCrasher.analytics.eventDesign("SlimesKilled",Integer.valueOf(slimeKillCounter));
      }
      return itemStack;
   }

   private boolean getIsJumpActive() {
      return this.isJumpActive;
   }

   private void setIsJumpActive(boolean val) {
      this.isJumpActive = val;
   }

   @Override
   public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean equipped) {
      if (!world.isRemote && equipped) {
         if (getIsJumpActive() && (itemStack.getItemDamage() < (itemStack.getMaxDamage() - 1))) {
            if (this.tickTimer == 0) {
               itemStack.setItemDamage(itemStack.getItemDamage() + 1);
               player.addPotionEffect(new PotionEffect(Potion.jump.id, 100, 4));
               this.tickTimer = 40;
            }
            this.tickTimer--;
         } else if (this.tickTimer > 0) {
            this.tickTimer = 0;
         }
      }
   }

}
