package yuncore.extendor.CorProtector;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import yuncore.extendor.methods.itemStack_in;
import yuncore.extendor.recipes.wrapper;

import java.util.Objects;

public class BlockModifyInArea implements Listener {
    @EventHandler
    public void OnPlayerBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        CoreData coreData = CoreData.getNearByCore(block);
        try { Bukkit.broadcastMessage(ChatColor.GOLD + "active: " + coreData.getActive() + "\nisInCoreArea: " + CoreData.isInCoreArea(block) + "\nisOwner: " + coreData.getOwner().equals(player.getUniqueId()));}
        catch (Exception exception){ Bukkit.broadcastMessage("ERROR"); }
        if(CoreData.isInCoreArea(block) && coreData.getActive() && !(coreData.getOwner().equals(player.getUniqueId()))){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void OnPlayerPlaceBlock(BlockPlaceEvent event) {
        if(event.getPlayer().isFlying()) event.getPlayer().sendMessage("wtf");
    }
    @EventHandler
    public void OnCorePlaced(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(block.getType().equals(Material.REDSTONE_LAMP)) {
            CoreData coreData = new CoreData(block,player);
            coreData.showCore(player);
        }
    }
    @EventHandler
    public void OnCoreBreaked(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(block.getType().equals(Material.REDSTONE_LAMP)) {
            CoreData.OnCoreMined(player,block,event);
        }
    }
    @EventHandler
    public void onHammered(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(!(event.hasBlock() && itemStack_in.isItemStackIn(event.getItem(), wrapper.hammers()) && event.hasBlock())) return;
        Block block = event.getClickedBlock();
        if(!block.getType().equals(Material.REDSTONE_LAMP)) return;
        if(!player.isSneaking() && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            player.playSound(player,Sound.UI_TOAST_IN,1.0f,1.0f);
            CoreData coreData = CoreData.getNearByCore(block);
            player.openInventory(coreData.getCoreMenu(player));
        }
        if(player.isSneaking() && event.getAction() == Action.LEFT_CLICK_BLOCK) {
            CoreData coreData = CoreData.getCurrentCore(block);
            coreData.setActive((!coreData.getActive()));
            CoreData.save();
        }
    }
    @EventHandler
    public void onSetting(InventoryClickEvent event) {
        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "Core")) {
            if(event.getCurrentItem()==null) return;
            if(!event.getCurrentItem().hasItemMeta()) return;
            if(!Objects.requireNonNull(event.getCurrentItem().getItemMeta()).hasLocalizedName()) return;
            String[] ButtonName = event.getCurrentItem().getItemMeta().getLocalizedName().split("\\.");
            if(ButtonName[0].equalsIgnoreCase("CoreGUI")) {
                Block block = event.getWhoClicked().getTargetBlock(null,10);
                Player player = (Player) event.getWhoClicked();
                if(block.getType().equals(Material.REDSTONE_LAMP)) {
                    CoreData coreData = CoreData.getCurrentCore(block);
                    switch (ButtonName[1]) {
                        case "pickup":
                            if(coreData.getOwner().equals(event.getWhoClicked().getUniqueId())) {
                                block.breakNaturally();
                                coreData.deleteCore();
                                event.getView().close();
                            }
                            else {
                                event.getWhoClicked().sendMessage(ChatColor.RED + "You have no permission!");
                            }
                            break;
                        case "information":
                            coreData.setActive(!coreData.getActive());
                            break;
                        case "active":
                            ItemMeta itemMeta = event.getCurrentItem().getItemMeta();


                            break;
                        case "add":
                            Objects.requireNonNull(Bukkit.getPlayer(event.getWhoClicked().getUniqueId())).playSound(event.getWhoClicked(), Sound.UI_BUTTON_CLICK,0.1f,1.0f);
                            break;
                        case "remove":
                            Objects.requireNonNull(Bukkit.getPlayer(event.getWhoClicked().getUniqueId())).playSound(event.getWhoClicked(),Sound.UI_BUTTON_CLICK,0.1f,0.9f);
                            break;
                        default:
                            player.sendMessage("not valid button!");
                            break;
                    }
                }
                else {
                    event.getView().close();
                    event.getWhoClicked().sendMessage("Please stand static when using hammer!");
                }
            }
            event.setCancelled(true);
        }
    }
}
