package yuncore.extendor.sethome;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import yuncore.extendor.Extendor;
import yuncore.extendor.sethome.HomeConfiguration;

public class home implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("list")) {
                    HomeConfiguration.listHome(player);
                    return true;
                }
                else if (args[0].equalsIgnoreCase("Death")) {
                    HomeConfiguration.toHome(player,"Death");
                    return true;
                }
            }
            if(args.length == 2) {
                if (args[0].equalsIgnoreCase("set")) {
                    HomeConfiguration.addHome(player,args[1]);
                    player.sendMessage(ChatColor.GOLD + "Set " + args[1] + " at" + "\n" + HomeConfiguration.getLocationInformation(player));
                    return true;
                }
                else if (args[0].equalsIgnoreCase("to")) {
                    HomeConfiguration.toHome(player,args[1]);
                    return true;
                }
                else if (args[0].equalsIgnoreCase("remove")) {
                    HomeConfiguration.deleteHome(player,args[1]);
                    return true;
                }
                else if (args[0].equalsIgnoreCase("player")) {
                    BukkitScheduler scheduler = Bukkit.getScheduler();
                    if(Bukkit.getPlayer(args[1])==null) return true;
                    Player target = Bukkit.getPlayer(args[1]);
                    scheduler.runTaskLater(Extendor.getPlugin(), () -> {
                        player.sendTitle("3", ChatColor.MAGIC + "_____",10,10,10);
                        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.MASTER,0.4f,1.0f);
                    }, 0L);
                    scheduler.runTaskLater(Extendor.getPlugin(), () -> {
                        player.sendTitle("2", ChatColor.MAGIC + "_____",10,10,10);
                        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.MASTER,0.4f,1.0f);
                    }, 20L);
                    scheduler.runTaskLater(Extendor.getPlugin(), () -> {
                        player.sendTitle("1", ChatColor.MAGIC + "_____",10,10,10);
                        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.MASTER,0.4f,1.0f);
                    }, 40L);
                    scheduler.runTaskLater(Extendor.getPlugin(), () -> {
                        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.MASTER,0.4f,1.4f);
                        if (target != null) {
                            player.teleport(target);
                        }
                    }, 60L);
                    return true;
                }
            }
        }
        return false;
    }


}
