package yuncore.extendor.lock.events;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class OpenToTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(args.length == 1) {
                List<String> options = new ArrayList<>();
                options.add("add");
                options.add("remove");
                return options;
            }
            else if(args.length == 2) {
                return null;
            }
        }
        return new ArrayList<>();
    }
}
