package yuncore.extendor.events;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExpBottleExtract implements Listener {
    @EventHandler
    public void onPlayerRightClickEnchantTable(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        Block block = event.getClickedBlock();

        if(action.equals(Action.RIGHT_CLICK_BLOCK) && player.isSneaking() && block.getType().equals(Material.ENCHANTING_TABLE)) {
            if(player.getInventory().getItemInMainHand().getType().equals(Material.GLASS_BOTTLE)) {
                player.playSound(player,Sound.BLOCK_ANCIENT_DEBRIS_HIT,1.2f,0.1f);
                float experience = player.getExp();
                player.sendMessage("" + experience);
                List<Item> items = getItemOnBlock(block);
                player.sendMessage(items.get(0).getItemStack().getType().toString());
                if(items.size() == 1) {
                    if(items.get(0).getItemStack().getType().equals(Material.LAPIS_LAZULI)) {
//                        Item item = (Item) block.getWorld().spawnEntity(block.getLocation().add(0,1,0),EntityType.DROPPED_ITEM);

                    }
                }
            }
        }
    }

    public List<Item> getItemOnBlock(Block block) {
        List<Item> items = new ArrayList<>();
        Location location = block.getLocation();
        Marker marker = (Marker) Objects.requireNonNull(location.getWorld()).spawnEntity(location.add(0,1,0),EntityType.MARKER);
        List<Entity> entities = marker.getNearbyEntities(0.2,0.2,0.2);
        for (Entity entity : entities) {
            if (entity instanceof Item) {
                items.add((Item) entity);
            }
        }
        for(int i=0;i<items.size();i++) {
            Bukkit.broadcastMessage(items.get(i).getItemStack().getItemMeta().getDisplayName());
        }
        return items;
    }
}
