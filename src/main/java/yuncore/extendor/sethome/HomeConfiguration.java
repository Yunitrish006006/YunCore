package yuncore.extendor.sethome;

import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import yuncore.extendor.Extendor;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class HomeConfiguration {

    private static File file;
    private static FileConfiguration fileConfiguration;

    public static void setup() {
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Extendor")).getDataFolder(),"Homes.yml");

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
            HomeConfiguration.get().save(file);
        } catch (IOException e) {
            System.out.println("file save error");
        }
    }

    public static void reload() { fileConfiguration = YamlConfiguration.loadConfiguration(file); }

    public static void toHome(Player player,String name) {
        player.teleport(HomeConfiguration.getHome(player,name));
        player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.HOSTILE,1.0f,0.2f);
        player.sendTitle(ChatColor.GOLD + name,ChatColor.MAGIC + "_________",10,10,10);
    }

    public static void addHome(Player player,String name) {
        fileConfiguration.set(player.getUniqueId() + "." + name + ".world", Objects.requireNonNull(player.getLocation().getWorld()).getName());
        fileConfiguration.set(player.getUniqueId() + "." + name + ".X",player.getLocation().getX());
        fileConfiguration.set(player.getUniqueId() + "." + name + ".Y",player.getLocation().getY());
        fileConfiguration.set(player.getUniqueId() + "." + name + ".Z",player.getLocation().getZ());
        fileConfiguration.set(player.getUniqueId() + "." + name + ".yaw",player.getLocation().getYaw());
        fileConfiguration.set(player.getUniqueId() + "." + name + ".pitch",player.getLocation().getPitch());
        HomeConfiguration.save();
    }@Nonnull
    public static Location getHome(Player player, String name) {
        if(fileConfiguration.get(player.getUniqueId()+"."+name)==null) {
            player.sendMessage(ChatColor.RED + "can not found " + ChatColor.GOLD + "\"" + name + "\"");
        }
        return new Location(
                Bukkit.getServer().getWorld(Objects.requireNonNull(fileConfiguration.get(player.getUniqueId() + "." + name + ".world")).toString()),
                Double.parseDouble(Objects.requireNonNull(fileConfiguration.get(player.getUniqueId() + "." + name + ".X")).toString()),
                Double.parseDouble(Objects.requireNonNull(fileConfiguration.get(player.getUniqueId() + "." + name + ".Y")).toString()),
                Double.parseDouble(Objects.requireNonNull(fileConfiguration.get(player.getUniqueId() + "." + name + ".Z")).toString()),
                Float.parseFloat(Objects.requireNonNull(fileConfiguration.get(player.getUniqueId() + "." + name + ".yaw")).toString()),
                Float.parseFloat(Objects.requireNonNull(fileConfiguration.get(player.getUniqueId() + "." + name + ".pitch")).toString())
                );
    }
    public static void deleteHome(Player player, String name) {
        ConfigurationSection config = fileConfiguration.getConfigurationSection(player.getUniqueId().toString());
        Set<String> set = Objects.requireNonNull(config).getKeys(false);
        Set<String> toRemove = new HashSet<>();
        boolean found = false;
        for (String s : set) { if (s.equals(name)) { toRemove.add(s); found = true; }}
        if(found) { player.sendMessage("You had removed " + ChatColor.GOLD + name); }
        else { player.sendMessage(ChatColor.RED + "can not found " + ChatColor.GOLD + "\"" + name + "\""); }
        for (String s : toRemove) { config.set(s, null); }
    }

    public static void listHome(Player player) {
        ConfigurationSection config = fileConfiguration.getConfigurationSection(player.getUniqueId().toString());
        String[] keys = Objects.requireNonNull(config).getKeys(false).toArray(new String[config.getKeys(false).size()]);
        List<String> homes = new ArrayList<>(Arrays.asList(keys));
        for (String home : homes) {
            player.sendMessage(ChatColor.GOLD + home + ChatColor.WHITE + " : " + getLocationInformation(player));
        }
    }

    public static List<String> getHomeNames(Player player) {
        ConfigurationSection config = fileConfiguration.getConfigurationSection(player.getUniqueId().toString());
        String[] keys = Objects.requireNonNull(config).getKeys(false).toArray(new String[config.getKeys(false).size()]);
        return new ArrayList<>(Arrays.asList(keys));
    }

    public static void initialize() {
        HomeConfiguration.setup();
//        HomeConfiguration.get().addDefault("Version",1.2);
//        HomeConfiguration.get().options().copyDefaults(true);
        HomeConfiguration.save();
    }

    public static String getLocationInformation(Player player) {
        String result = "";
        result += ChatColor.GOLD + " " + Objects.requireNonNull(player.getLocation().getWorld()).getName() + ChatColor.WHITE  + " , ";
        result += ChatColor.RED + " " + Math.round(player.getLocation().getX()*100.0)/100.0 + ChatColor.WHITE  + " , ";
        result += ChatColor.GREEN + " " + Math.round(player.getLocation().getY()*100.0)/100.0 + ChatColor.WHITE  + " , ";
        result += ChatColor.BLUE + " " + Math.round(player.getLocation().getZ()*100.0)/100.0;
        return result;
    }

}
