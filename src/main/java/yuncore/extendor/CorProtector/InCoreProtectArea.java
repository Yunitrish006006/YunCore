package yuncore.extendor.CorProtector;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import yuncore.extendor.Extendor;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class InCoreProtectArea {
    private boolean active;
    private UUID Owner;
    private UUID[] Group;
    private double x;
    private double y;
    private double z;
    private World world;
    private Double CoreHealth;
    private Double CoreEnergy;
    private Double CoreShield;
    private int Height;
    private int Deep;
    private int range;

    private static File file;
    private static FileConfiguration fileConfiguration;

    public static void setup() {
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Extendor")).getDataFolder(),"Cores.yml");

        if(!file.exists()) {
            try {
                boolean x = file.createNewFile();
                if(!x) {
                    Extendor.getPlugin().getServer().broadcastMessage("Extender files initialized......");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return fileConfiguration;
    }

    public static void save() {
        try {
            InCoreProtectArea.get().save(file);
        } catch (IOException e) {
            System.out.println("file save error");
        }
    }

    public static void initialize() {
        InCoreProtectArea.setup();
        InCoreProtectArea.save();
    }

    public static void setCore(Block block, Player owner, String name) {
        ConfigurationSection core = fileConfiguration.createSection(name + ".");
        fileConfiguration.set(core + "Owner",owner.getUniqueId());
        fileConfiguration.set(core + "Active",false);
        fileConfiguration.set(core + "Height",4);
        fileConfiguration.set(core + "Deep",4);
        fileConfiguration.set(core + "Range",4);
        fileConfiguration.set(core + "World",block.getWorld());
        fileConfiguration.set(core + "X",block.getLocation().getX());
        fileConfiguration.set(core + "Y",block.getLocation().getY());
        fileConfiguration.set(core + "Z",block.getLocation().getZ());
        fileConfiguration.set(core + "CoreHealth",20.0);
        fileConfiguration.set(core + "CoreEnergy",100.0);
        fileConfiguration.set(core + "CoreShield",4.0);
        fileConfiguration.set(core + "Group","");
        InCoreProtectArea.save();
    }

    public boolean isInCoreArea(Block block) {
        return false;
    }
}
