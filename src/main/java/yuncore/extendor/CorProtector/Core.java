package yuncore.extendor.CorProtector;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Core implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Block block = player.getTargetBlock(null, 16);
            if(block.getType().equals(Material.REDSTONE_LAMP)){
                CoreData coreData = new CoreData(block);
                if(args[0].equalsIgnoreCase("add")) {
                    coreData.addGroup(Objects.requireNonNull(Bukkit.getPlayer(args[1])));
                    player.sendMessage("add " + args[1] + " to Group");
                    return true;
                }
                else if(args[0].equalsIgnoreCase("remove")){
                    coreData.removeGroup(Objects.requireNonNull(Bukkit.getPlayer(args[1])));
                    player.sendMessage("remove " + args[1] + " from Group");
                    return true;
                }
                else if(args[0].equalsIgnoreCase("clear")){
                    coreData.cleanGroup();
                    player.sendMessage("clear the Group");
                    return true;
                }
            }
        }
        return false;
    }
}
