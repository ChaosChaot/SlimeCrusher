package de.chaoschaot.slimecrasher.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.chaoschaot.slimecrasher.SlimeCrasher;
import de.chaoschaot.slimecrasher.lib.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;


public class ItemSlimeCrasher extends ItemTool {

   private static final Set harvestable = Sets.newHashSet();
   private String name = "slimeCrasher";

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
            if (isJumpActive(itemStack)) {
               setJumpActive(false,itemStack);
            } else {
               setJumpActive(true,itemStack);
            }
            return itemStack;
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
            // Depending on slime size, do x damage (1,2,4)
            int size = ((EntitySlime) e).getSlimeSize();
            itemStack.setItemDamage(itemStack.getItemDamage() + size);

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

   private static boolean isJumpActive(ItemStack itemStack) {
      return itemStack.hasTagCompound() && itemStack.getTagCompound().getBoolean("jumpBoostActive");
   }

   private static void setJumpActive(boolean val, ItemStack itemStack) {
      if(!itemStack.hasTagCompound()) {
         itemStack.setTagCompound(new NBTTagCompound());
      }
      itemStack.getTagCompound().setBoolean("jumpBoostActive",val);
   }

   @Override
   public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean equipped) {
      if (world.isRemote) { return; }
      if (equipped) {
         if (isJumpActive(itemStack) && (itemStack.getItemDamage() < (itemStack.getMaxDamage() - 1))) {
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

   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool) {
      String status = EnumChatFormatting.DARK_RED + "Inactive";
      if(isJumpActive(itemStack)) {
         status = EnumChatFormatting.DARK_GREEN + "Active";
      }
      list.add("Jump: " + status);
   }

   @Override
   public Multimap getAttributeModifiers(ItemStack itemStack) {
      return HashMultimap.create();
   }

   @Override
   public boolean hasEffect(ItemStack itemStack, int pass) {
      return isJumpActive(itemStack);
   }
}
