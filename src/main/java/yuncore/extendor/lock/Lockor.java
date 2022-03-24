package yuncore.extendor.lock;

import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Lockor implements Serializable {

    private static final long serialVersionUID = 1L;
    private final UUID owner;
    private final List<UUID> available_players = new ArrayList<>();
    private String mode;

    public Lockor(Player player) {
        this.owner = player.getUniqueId();
        this.mode = "unlock";
        this.available_players.add(player.getUniqueId());
    }
    @Nonnull
    public UUID getOwner(){return owner;}
    public List<UUID> getAvailable_players() {return available_players;}
    @Nonnull
    public String getMode() {return mode;}
    public void setMode(String mode) {this.mode = mode;}
    public void modeUpdate() {
        if(this.mode.equalsIgnoreCase("unlock")) {
            this.mode = "private";
        }else if(this.mode.equalsIgnoreCase("private")) {
            this.mode = "group";
        }else if(this.mode.equalsIgnoreCase("group")) {
            this.mode = "unlock";
        }
    }
    public void addAvailable_players(Player player) {
        this.available_players.add(player.getUniqueId());
    }
    public void removeAvailable_players(Player player) {
        this.available_players.remove(player.getUniqueId());
    }
}
