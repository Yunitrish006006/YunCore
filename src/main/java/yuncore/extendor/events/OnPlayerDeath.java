package yuncore.extendor.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import yuncore.extendor.sethome.HomeConfiguration;

public class OnPlayerDeath implements Listener {
    @EventHandler
    public void RecordDeathPoint(PlayerDeathEvent event) {
        Player player = event.getEntity();
        HomeConfiguration.addHome(player,"Death");
        player.sendMessage(ChatColor.GOLD + "Death point record at" + "\n" + HomeConfiguration.getLocationInformation(player));
    }
}
