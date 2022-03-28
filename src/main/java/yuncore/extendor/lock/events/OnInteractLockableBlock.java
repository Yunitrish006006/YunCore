package yuncore.extendor.lock.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import yuncore.extendor.lock.LockDataType;
import yuncore.extendor.lock.Lockor;
import yuncore.extendor.lock.Methods;
import yuncore.extendor.methods.itemStack_in;
import yuncore.extendor.recipes.wrapper;

import java.util.Locale;
import java.util.Objects;

public class OnInteractLockableBlock implements Listener {

    @EventHandler
    public void onHammered(PlayerInteractEvent event) {
        if(!(event.hasBlock() && itemStack_in.isItemStackIn(event.getItem(), wrapper.hammers()) && event.getPlayer().isSneaking() && event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        Player player = event.getPlayer();
        if(Objects.requireNonNull(event.getClickedBlock()).getState().getClass().getName().endsWith("CraftBlockState")) return;
        Block block = event.getClickedBlock();
        TileState state = (TileState) Objects.requireNonNull(block).getState();
        if(!state.getPersistentDataContainer().has(Methods.lockor(),new LockDataType())) return;
        if(Objects.requireNonNull(state.getPersistentDataContainer().get(Methods.lockor(), new LockDataType())).getOwner().equals(player.getUniqueId())) {
            Lockor lockor = state.getPersistentDataContainer().get(Methods.lockor(),new LockDataType());
            Objects.requireNonNull(lockor).modeUpdate();
            state.getPersistentDataContainer().set(Methods.lockor(),new LockDataType(),lockor);
            state.update();
            player.sendTitle(" ",ChatColor.GOLD + Objects.requireNonNull(state.getPersistentDataContainer().get(Methods.lockor(), new LockDataType())).getMode().toLowerCase(Locale.ROOT),10,10,10);
        }
        else {
            player.sendTitle(" ","access denied!",10,10,10);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(!(event.getBlock().getState() instanceof TileState)) return;
        if(Methods.isSign(event.getBlock()))
        {
            event.getPlayer().sendMessage("not sign");
            TileState state = (TileState) event.getBlock().getState();
            state.getPersistentDataContainer().set(Methods.lockor(),new LockDataType(),new Lockor(event.getPlayer()));
            state.update();
            event.getPlayer().sendTitle(" ",ChatColor.GOLD + Objects.requireNonNull(state.getPersistentDataContainer().get(Methods.lockor(), new LockDataType())).getMode().toLowerCase(Locale.ROOT),10,10,10);
        }
        else {
            event.getPlayer().sendMessage("is sign");
        }
    }

    @EventHandler
    public void onOpen(PlayerInteractEvent event) {
        if(!event.hasBlock()) return;
        if(Objects.requireNonNull(event.getClickedBlock()).getState().getClass().getName().endsWith("CraftBlockState")) return;
        if(!((TileState) event.getClickedBlock().getState()).getPersistentDataContainer().has(Methods.lockor(), new LockDataType())) return;
        Lockor lockor = ((TileState) event.getClickedBlock().getState()).getPersistentDataContainer().get(Methods.lockor(),new LockDataType());
        if(Objects.requireNonNull(lockor).getMode().equalsIgnoreCase("unlocked")) {
            event.setCancelled(false);
        }
        else if(lockor.getMode().equalsIgnoreCase("private")) {
            if(!event.getPlayer().getUniqueId().equals(lockor.getOwner())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.GOLD + "Locked!(Private)");
            }
        }
        else if(lockor.getMode().equalsIgnoreCase("group")) {
            if(!Methods.canOpen(event.getPlayer(),event.getClickedBlock())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.GOLD + "Locked!(Group)");
            }
        }
    }

    @EventHandler
    public void OnBlockDestroyed(BlockBreakEvent event) {
        if(event.getBlock().getState().getClass().getName().endsWith("CraftBlockState")) return;
        if(!((TileState) event.getBlock().getState()).getPersistentDataContainer().has(Methods.lockor(), new LockDataType())) return;
        Lockor lockor = ((TileState) event.getBlock().getState()).getPersistentDataContainer().get(Methods.lockor(),new LockDataType());
        if(Objects.requireNonNull(lockor).getMode().equalsIgnoreCase("unlocked")) {
            event.setCancelled(false);
        }
        else if(lockor.getMode().equalsIgnoreCase("private")) {
            if(!event.getPlayer().getUniqueId().equals(lockor.getOwner())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.GOLD + "Chest Locked!");
            }
        }
        else if(lockor.getMode().equalsIgnoreCase("group")) {
            if(!Methods.canOpen(event.getPlayer(),event.getBlock())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.GOLD + "Chest Locked!");
            }
        }
    }

}