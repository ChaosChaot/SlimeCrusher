package de.chaoschaot.slimecrasher.items;

import com.google.common.collect.Sets;
import cpw.mods.fml.common.registry.GameRegistry;
import de.chaoschaot.slimecrasher.SlimeCrasher;
import de.chaoschaot.slimecrasher.lib.Reference;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;


public class ItemSlimeCrasher extends ItemTool {

    private static final Set harvestable = Sets.newHashSet();
    private String name = "slimeCrasher";

    public ItemSlimeCrasher() {
        super(0.0F,ModItems.compressedslime,harvestable);

        setUnlocalizedName(Reference.MODID + "_" + name);
        GameRegistry.registerItem(this, name);
        setCreativeTab(SlimeCrasher.slimecrasherTab);
        setTextureName(Reference.MODID + ":" + name);

        this.maxStackSize = 1;

    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote) {
            player.swingItem();
        }

        AxisAlignedBB playerBounding = player.boundingBox.expand(32, 32, 32); //AxisAlignedBB.getBoundingBox(player.posX - 32, player.posY - 32, player.posZ - 32, 64, 64, 64)
        List<EntityLiving> entities = world.getEntitiesWithinAABB(EntityLiving.class,playerBounding );

        for (EntityLiving e : entities)  {

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
                }

            }

        }

        return itemStack;
    }
}
