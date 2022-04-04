package yuncore.extendor.sethome;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class homeTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(args.length == 1) {
                List<String> options = new ArrayList<>();
                options.add("to");
                options.add("set");
                options.add("remove");
                options.add("death");
                options.add("list");
                options.add("player");
                return options;
            }
            else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("to") || args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("remove")) {
                    return HomeConfiguration.getHomeNames((Player) sender);
                }
                else if(args[0].equalsIgnoreCase("player")){
                    return null;
                }
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }
}
