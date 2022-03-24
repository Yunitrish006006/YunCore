package yuncore.extendor.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Beehive;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class OnPlayerInteractBeeHive implements Listener {
    @EventHandler
    public void OnRightClick(PlayerInteractEvent event) {
        if(!event.hasBlock()) return;
        Material block_type = Objects.requireNonNull(event.getClickedBlock()).getType();
        if((block_type.equals(Material.BEEHIVE) || block_type.equals(Material.BEE_NEST))) {
            Beehive beehive = (Beehive) event.getClickedBlock().getState();
            int bee_count = beehive.getEntityCount();
            int bee_max = beehive.getMaxEntities();
            int honey_count = ((org.bukkit.block.data.type.Beehive)beehive.getBlockData()).getHoneyLevel();
            int honey_max = ((org.bukkit.block.data.type.Beehive)beehive.getBlockData()).getMaximumHoneyLevel();
            if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(event.getPlayer().isSneaking()){
                    event.getPlayer().sendTitle(" ",ChatColor.GOLD + "Bee released!",10,10,10);
                    beehive.releaseEntities();
                }
                else {
                    event.getPlayer().sendTitle(" ",ChatColor.GOLD + "Bee: " + bee_count + " / " + bee_max + " | Honey: " + honey_count + " / " + honey_max,10,10,10);
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER,0.6f,1.6f);
                }
            }

        }
    }
}
