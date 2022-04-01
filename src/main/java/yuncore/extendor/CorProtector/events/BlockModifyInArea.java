package yuncore.extendor.CorProtector.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockModifyInArea implements Listener {
    @EventHandler
    public void OnPlayerBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
    }
}
