package yuncore.extendor.lock;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import yuncore.extendor.Extendor;

import java.util.Arrays;
import java.util.Objects;

public class Methods {
    public static Material[] lockableBlocks() {
        return new Material[]{
                Material.BARREL,
                Material.CHEST,
                Material.TRAPPED_CHEST,
                Material.DROPPER,
                Material.DISPENSER,
                Material.HOPPER,
                Material.FURNACE,
                Material.SMOKER,
                Material.BLAST_FURNACE
        };
    }
    public static boolean notInType(Block block) {
        return !Arrays.asList(lockableBlocks()).contains(block.getType());
    }
    public static boolean isOwner(Player player,Block block) {
        return Objects.requireNonNull(((TileState)block.getState()).getPersistentDataContainer().get(Methods.lockor(), new LockDataType())).getOwner().equals(player.getUniqueId());
    }
    public static boolean canOpen(Player player,Block block) {
        TileState state = (TileState) Objects.requireNonNull(block).getState();
        PersistentDataContainer container = state.getPersistentDataContainer();
        Lockor lockor = container.get(Methods.lockor(),new LockDataType());
        if (lockor != null) {
            return lockor.getAvailable_players().contains(player.getUniqueId());
        }
        else return false;
    }
    public static NamespacedKey lockor() {return new NamespacedKey(Extendor.getPlugin(),"Lockor");}
}
