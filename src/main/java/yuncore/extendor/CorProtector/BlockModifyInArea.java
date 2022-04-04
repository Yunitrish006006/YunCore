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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import yuncore.extendor.methods.itemStack_in;
import yuncore.extendor.recipes.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class BlockModifyInArea implements Listener {
    @EventHandler
    public void OnPlayerBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        CoreData coreData = CoreData.getNearByCore(block);
        if(CoreData.isInCoreArea(block) && coreData.getActive() && !(coreData.getOwner().equals(player.getUniqueId()))){
            event.setCancelled(true);
        }
        else {
            Bukkit.broadcastMessage("________________________________");
            Bukkit.broadcastMessage("active: " + coreData.getActive());
            Bukkit.broadcastMessage("isInCoreArea: " + CoreData.isInCoreArea(block));
            Bukkit.broadcastMessage("isOwner: " + coreData.getOwner().equals(player.getUniqueId()));
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
            CoreData.setCore(block,player,player.getName());
            CoreData.showCoreFromFile(player,block);
        }
    }
    @EventHandler
    public void OnCoreBreaked(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(block.getType().equals(Material.REDSTONE_LAMP)) {
            CoreData.CoreBreaked(player,block,event);
        }
    }
    @EventHandler
    public void onHammered(PlayerInteractEvent event) {
        if(!(event.hasBlock() && itemStack_in.isItemStackIn(event.getItem(), wrapper.hammers()) && !event.getPlayer().isSneaking() && event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        Player player = event.getPlayer();
        if(event.hasBlock()) {
            Block block = event.getClickedBlock();
            if(block.getType().equals(Material.REDSTONE_LAMP)) {
                Inventory CoreGUI = Bukkit.createInventory(player,36,ChatColor.GOLD + "Core");
                CoreData coreData = new CoreData(block);
                //
                ItemStack information = new ItemStack(Material.BOOK);
                ItemMeta information_meta = information.getItemMeta();
                information_meta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + "資訊");
                information_meta.setLocalizedName("CoreGUI.information");
                List<String> information_lore = new ArrayList<>();
                information_lore.add(ChatColor.GOLD + "Owner: " + ChatColor.WHITE + coreData.getOwnerName());
                information_lore.add(ChatColor.GOLD + "Active: " + ChatColor.WHITE + coreData.getActive());
                information_lore.add(ChatColor.GOLD + "World: " + ChatColor.WHITE +  coreData.getWorld().getName());
                information_lore.add(ChatColor.GOLD + "Location: " + ChatColor.RED + coreData.getX() + " " + ChatColor.GREEN + coreData.getY() + " " + ChatColor.BLUE + coreData.getZ());
                information_lore.add(ChatColor.GOLD + "Height: " + ChatColor.WHITE +  coreData.getHeight());
                information_lore.add(ChatColor.GOLD + "Deep: " + ChatColor.WHITE +  coreData.getDeep());
                information_lore.add(ChatColor.GOLD + "Range: " + ChatColor.WHITE +  coreData.getRange());
                information_lore.add(ChatColor.GOLD + "Health: " + ChatColor.WHITE +  coreData.getCoreHealth());
                information_lore.add(ChatColor.GOLD + "Energy: " + ChatColor.WHITE +  coreData.getCoreEnergy());
                information_lore.add(ChatColor.GOLD + "Shield: " + ChatColor.WHITE +  coreData.getCoreShield());
                information_lore.add(ChatColor.GOLD + "Group: " + ChatColor.WHITE +  coreData.getGroupName().toString());
                information_meta.setLore(information_lore);
                information.setItemMeta(information_meta);
                //
                ItemStack pickup = new ItemStack(Material.WOODEN_PICKAXE);
                ItemMeta pickup_meta = pickup.getItemMeta();
                pickup_meta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + "收回");
                pickup_meta.setLocalizedName("CoreGUI.pickup");
                pickup.setItemMeta(pickup_meta);
                //
                ItemStack active = new ItemStack(Material.LEVER);
                ItemMeta active_meta = active.getItemMeta();
                active_meta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + "開關");
                active_meta.setLocalizedName("CoreGUI.active");
                List<String> active_lore = new ArrayList<>();
                if(coreData.getActive()) {active_lore.add(ChatColor.GREEN + "on");}
                else {active_lore.add(ChatColor.RED + "off");}
                active_meta.setLore(active_lore);
                active.setItemMeta(active_meta);
                //
                ItemStack add = new ItemStack(Material.EMERALD);
                ItemMeta add_meta = add.getItemMeta();
                add_meta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + "Add");
                add_meta.setLocalizedName("CoreGUI.add");
                add.setItemMeta(add_meta);
                //
                ItemStack remove = new ItemStack(Material.REDSTONE);
                ItemMeta remove_meta = add.getItemMeta();
                remove_meta.setDisplayName(ChatColor.RESET + "" + ChatColor.RED + "Remove");
                remove_meta.setLocalizedName("CoreGUI.remove");
                remove.setItemMeta(remove_meta);
                //
                CoreGUI.setItem(0,active);
                CoreGUI.setItem(2,pickup);
                CoreGUI.setItem(6,add);
                CoreGUI.setItem(8,remove);
                CoreGUI.setItem(13,information);
                player.openInventory(CoreGUI);
            }
        }
    }
    @EventHandler
    public void onSetting(InventoryClickEvent event) {
        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "Core")) {
            if(event.getCurrentItem()==null) return;
            if(!event.getCurrentItem().hasItemMeta()) return;
            if(!event.getCurrentItem().getItemMeta().hasLocalizedName()) return;
            String[] ButtonName = event.getCurrentItem().getItemMeta().getLocalizedName().split("\\.");
            if(ButtonName[0].equalsIgnoreCase("CoreGUI")) {
                Block block = event.getWhoClicked().getTargetBlock(null,10);
                if(block.getType().equals(Material.REDSTONE_LAMP)) {
                    CoreData coreData = new CoreData(block);
                    switch (ButtonName[1]) {
                        case "pickup":
                            if(coreData.getOwner().equals(event.getWhoClicked().getUniqueId())) {
                                block.breakNaturally();
                                event.getView().close();
                            }
                            else {
                                event.getWhoClicked().sendMessage(ChatColor.RED + "You have no permission!");
                            }
                            break;
                        case "active":
                            coreData.setActive();
                            ItemMeta itemMeta = event.getCurrentItem().getItemMeta();
                            List<String> lore = itemMeta.getLore();
                            if(lore.get(0).equalsIgnoreCase(ChatColor.RED + "off")) {lore.set(0,ChatColor.GREEN + "on");}
                            else {lore.set(0,ChatColor.RED + "off");}
                            itemMeta.setLore(lore);
                            event.getCurrentItem().setItemMeta(itemMeta);
                            break;
                        case "add":
                            Objects.requireNonNull(Bukkit.getPlayer(event.getWhoClicked().getUniqueId())).playSound(event.getWhoClicked(),Sound.UI_BUTTON_CLICK,1.0f,2.0f);
                            break;
                        case "remove":
                            Objects.requireNonNull(Bukkit.getPlayer(event.getWhoClicked().getUniqueId())).playSound(event.getWhoClicked(),Sound.UI_BUTTON_CLICK,1.0f,0.5f);
                            break;
                        default:
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
