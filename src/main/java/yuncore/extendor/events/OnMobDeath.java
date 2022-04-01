package yuncore.extendor.events;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.Lootable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.*;

public class OnMobDeath implements Listener {
    @EventHandler
    public static void  ONDamaged(EntityDamageEvent event) {

    }


    @EventHandler
    public static void OnUndeadDies(EntityDeathEvent event) {
        if(event.getEntity().getCategory().equals(EntityCategory.UNDEAD)) {
//            Mob undead = (Mob) event.getEntity();
//            LootTable undead_loottable = undead.getLootTable();
//            event.getEntity().getKiller().sendMessage(undead_loottable.toString());
        }
        if(event.getEntity() instanceof Zombie && !(event.getEntity() instanceof PigZombie)) {
            Random random = new Random();
            if(random.nextInt(100) < 10) {
                Skeleton skeleton = (Skeleton) event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.SKELETON,true);
                skeleton.setTarget(event.getEntity().getKiller());
                skeleton.setHealth(8.0);
                skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_SWORD));
            }
        }
    }
}
