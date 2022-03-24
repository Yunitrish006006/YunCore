package yuncore.extendor.to.commands;

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

public class to implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 1) {
                BukkitScheduler scheduler = Bukkit.getScheduler();
                Player target = Bukkit.getPlayer(args[0]);
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
        return false;
    }
}
