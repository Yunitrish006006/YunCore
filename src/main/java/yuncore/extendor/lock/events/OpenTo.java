package yuncore.extendor.lock.events;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import yuncore.extendor.Extendor;
import yuncore.extendor.lock.LockDataType;
import yuncore.extendor.lock.Lockor;
import yuncore.extendor.lock.Methods;

import java.util.Arrays;
import java.util.Objects;

public class OpenTo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Block block = player.getTargetBlock(null,10);
            if(Objects.requireNonNull(block).getState().getClass().getName().endsWith("CraftBlockState")) return false;
            if(!((TileState) block.getState()).getPersistentDataContainer().has(Methods.lockor(), new LockDataType())) return false;
            TileState state = (TileState) Objects.requireNonNull(block).getState();
            if(!state.getPersistentDataContainer().has(Methods.lockor(),new LockDataType())) { player.sendMessage(ChatColor.RED + "this is not a lockable block!" ); }
            if(Objects.requireNonNull(state.getPersistentDataContainer().get(Methods.lockor(), new LockDataType())).getOwner().equals(player.getUniqueId())) {
                if(args[0].equalsIgnoreCase("add")) {
                    Lockor lockor = state.getPersistentDataContainer().get(Methods.lockor(), new LockDataType());
                    Objects.requireNonNull(lockor).addAvailable_players(Objects.requireNonNull(Extendor.getPlugin().getServer().getPlayer(args[1])));
                    player.sendMessage(ChatColor.GOLD + "add " + args[1] + " to group!");
                    PersistentDataContainer container = state.getPersistentDataContainer();
                    container.set(Methods.lockor(),new LockDataType(),lockor);
                    state.update();
                }
                else if(args[0].equalsIgnoreCase("remove")) {
                    Lockor lockor = state.getPersistentDataContainer().get(Methods.lockor(),new LockDataType());
                    Objects.requireNonNull(lockor).removeAvailable_players(Objects.requireNonNull(Extendor.getPlugin().getServer().getPlayer(args[1])));
                    player.sendMessage(ChatColor.GOLD + "remove " + args[1] + " from group!");
                    state.getPersistentDataContainer().set(Methods.lockor(),new LockDataType(),lockor);
                    block.getState().update();
                    return true;
                }
                else if(args[0].equalsIgnoreCase("get")) {
                    Lockor lockor = state.getPersistentDataContainer().get(Methods.lockor(),new LockDataType());
                    player.sendMessage(ChatColor.GOLD + Arrays.toString(Objects.requireNonNull(lockor).getAvailable_players().toArray()));
                    return true;
                }
            }
        }
        return false;
    }
}
