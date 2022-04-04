package yuncore.extendor.CorProtector;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Lightable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import yuncore.extendor.Extendor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CoreData {
    private String OwnerName;
    private Block core;
    private boolean active;
    private UUID Owner;
    private List<UUID> Group;
    private double x;
    private double y;
    private double z;
    private World world;
    private double CoreHealth;
    private double CoreEnergy;
    private double CoreShield;
    private int Height;
    private int Deep;
    private int Range;
    private static File file;
    private static FileConfiguration fileConfiguration;
    //
    public List<UUID> toUUIDList(List<String> strings){
        List<UUID> list = new ArrayList<>();
        for (String string : strings) {
            list.add(UUID.fromString(string));
        }
        return list;
    }
    //
    public CoreData(Block block) {
        for(String key : fileConfiguration.getKeys(false)){
            if(fileConfiguration.getDouble(key + ".X") == block.getLocation().getX() && fileConfiguration.getDouble(key + ".Y") == block.getLocation().getY() && fileConfiguration.getDouble(key + ".Z") == block.getLocation().getZ()) {
                this.OwnerName = key;
                this.active = fileConfiguration.getBoolean(key + ".active");
                this.Owner = UUID.fromString(Objects.requireNonNull(fileConfiguration.getString(key + ".Owner")));
                this.Group = toUUIDList(fileConfiguration.getStringList(key + ".Group"));
                this.world = Bukkit.getWorld(Objects.requireNonNull(fileConfiguration.getString(key + ".World")));
                this.x = fileConfiguration.getDouble(key + ".X");
                this.y = fileConfiguration.getDouble(key + ".Y");
                this.z = fileConfiguration.getDouble(key + ".Z");
                this.Height = fileConfiguration.getInt(key + ".Height");
                this.Deep = fileConfiguration.getInt(key + ".Deep");
                this.Range = fileConfiguration.getInt(key + ".Range");
                this.CoreHealth = fileConfiguration.getDouble(key + ".CoreHealth");
                this.CoreEnergy = fileConfiguration.getDouble(key + ".CoreEnergy");
                this.CoreShield = fileConfiguration.getDouble(key + ".CoreShield");
                this.core = block;
            }
        }
    }
    public CoreData(String ID) {
        this.OwnerName = ID;
        this.active = fileConfiguration.getBoolean(ID + ".active");
        this.Owner = UUID.fromString(Objects.requireNonNull(fileConfiguration.getString(ID + ".Owner")));
        this.Group = toUUIDList(fileConfiguration.getStringList(ID + ".Group"));
        this.world = Bukkit.getWorld(Objects.requireNonNull(fileConfiguration.getString(ID + ".World")));
        this.x = fileConfiguration.getDouble(ID + ".X");
        this.y = fileConfiguration.getDouble(ID + ".Y");
        this.z = fileConfiguration.getDouble(ID + ".Z");
        this.Height = fileConfiguration.getInt(ID + ".Height");
        this.Deep = fileConfiguration.getInt(ID + ".Deep");
        this.Range = fileConfiguration.getInt(ID + ".Range");
        this.CoreHealth = fileConfiguration.getDouble(ID + ".CoreHealth");
        this.CoreEnergy = fileConfiguration.getDouble(ID + ".CoreEnergy");
        this.CoreShield = fileConfiguration.getDouble(ID + ".CoreShield");
        Location location = new Location(getWorld(),getX(),getY(),getZ());
        this.core = Bukkit.getWorld(getWorld().getName()).getBlockAt(location);
    }
    //
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
            CoreData.get().save(file);
        } catch (IOException e) {
            System.out.println("file save error");
        }
    }
    public static void initialize() {
        CoreData.setup();
        CoreData.save();
    }
    //
    public static void setCore(Block block, Player owner, String name) {
        fileConfiguration.set(name + ".Owner",owner.getUniqueId().toString());
        fileConfiguration.set(name + ".Active",false);
        fileConfiguration.set(name + ".Height",4);
        fileConfiguration.set(name + ".Deep",4);
        fileConfiguration.set(name + ".Range",4);
        fileConfiguration.set(name + ".World",block.getWorld().getName());
        fileConfiguration.set(name + ".X",block.getLocation().getX());
        fileConfiguration.set(name + ".Y",block.getLocation().getY());
        fileConfiguration.set(name + ".Z",block.getLocation().getZ());
        fileConfiguration.set(name + ".CoreHealth",20.0);
        fileConfiguration.set(name + ".CoreEnergy",100.0);
        fileConfiguration.set(name + ".CoreShield",4.0);
        fileConfiguration.set(name + ".Group",new ArrayList<>());
        CoreData.save();
    }
    public void refreshCore() {
        fileConfiguration.set(OwnerName + ".Owner",getOwner().toString());
        fileConfiguration.set(OwnerName + ".Active",getActive());
        fileConfiguration.set(OwnerName + ".Height",getHeight());
        fileConfiguration.set(OwnerName + ".Deep",getDeep());
        fileConfiguration.set(OwnerName + ".Range",getRange());
        fileConfiguration.set(OwnerName + ".World",getWorld().getName());
        fileConfiguration.set(OwnerName + ".X",getX());
        fileConfiguration.set(OwnerName + ".Y",getY());
        fileConfiguration.set(OwnerName + ".Z",getZ());
        fileConfiguration.set(OwnerName + ".CoreHealth",getCoreHealth());
        fileConfiguration.set(OwnerName + ".CoreEnergy",getCoreEnergy());
        fileConfiguration.set(OwnerName + ".CoreShield",getCoreShield());
        fileConfiguration.set(OwnerName + ".Group", getGroup());
        CoreData.save();
    }
    //
    public boolean getActive(){
        return active;
    }
    public void setActive() {
        active = !active;
        for (Player p : Bukkit.getOnlinePlayers()){p.playSound(core.getLocation().add(0,1,0), Sound.UI_BUTTON_CLICK,0.1f,1.0f);}
        Lightable lightable = (Lightable) core.getBlockData();
        lightable.setLit(active);
        core.setBlockData(lightable);
        refreshCore();
    }
    public UUID getOwner() {
        return Owner;
    }
    public String getOwnerName() {
        return OwnerName;
    }
    public List<UUID> getGroup() {
        return Group;
    }
    public List<String> getGroupName() {
        List<String> result = new ArrayList<>();
        for(int i=0;i<getGroup().size();i++) {
            result.add(getGroup().get(i).toString());
        }
        return result;
    }
    public void addGroup(Player player) {
        List<UUID> temp = getGroup();
        temp.add(player.getUniqueId());
        refreshCore();
    }
    public void removeGroup(Player player) {
        List<UUID> temp = getGroup();
        temp.remove(player.getUniqueId());
        refreshCore();
    }
    public void cleanGroup() {
        List<UUID> temp = new ArrayList<>();
        refreshCore();
    }
    public int getDeep() {
        return Deep;
    }
    public int getHeight() {
        return Height;
    }
    public int getRange() {
        return Range;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getZ() {
        return z;
    }
    public double getCoreEnergy() {
        return CoreEnergy;
    }
    public double getCoreHealth() {
        return CoreHealth;
    }
    public double getCoreShield() {
        return CoreShield;
    }
    public World getWorld() {
        return world;
    }
    //
    public static void showCoreFromFile(Player player, Block block) {
        for(String key : fileConfiguration.getKeys(false)) {
            if (fileConfiguration.getDouble(key + ".X") == block.getLocation().getX() && fileConfiguration.getDouble(key + ".Y") == block.getLocation().getY() && fileConfiguration.getDouble(key + ".Z") == block.getLocation().getZ()) {
                player.sendMessage("Owner: " + fileConfiguration.get(key + ".Owner"));
                player.sendMessage("Active: " + fileConfiguration.get(key + ".Active"));
                player.sendMessage("Height: " + fileConfiguration.get(key + ".Height"));
                player.sendMessage("Deep: " + fileConfiguration.get(key + ".Deep"));
                player.sendMessage("Range: " + fileConfiguration.get(key + ".Range"));
                player.sendMessage("World: " + fileConfiguration.get(key + ".World"));
                player.sendMessage("X: " + fileConfiguration.get(key + ".X"));
                player.sendMessage("Y: " + fileConfiguration.get(key + ".Y"));
                player.sendMessage("Z: " + fileConfiguration.get(key + ".Z"));
                player.sendMessage("CoreHealth: " + fileConfiguration.get(key + ".CoreHealth"));
                player.sendMessage("CoreEnergy: " + fileConfiguration.get(key + ".CoreEnergy"));
                player.sendMessage("CoreShield: " + fileConfiguration.get(key + ".CoreShield"));
                player.sendMessage("Group: " + fileConfiguration.get(key + ".Group"));
            }
            else {
                player.sendMessage(ChatColor.RED + "Core Not Exist!!");
            }
        }
    }
    public static void CoreBreaked(Player player, Block block, BlockBreakEvent event) {
        for(String key : fileConfiguration.getKeys(false)) {
            if(fileConfiguration.getDouble(key + ".X") == block.getLocation().getX() && fileConfiguration.getDouble(key + ".Y") == block.getLocation().getY() && fileConfiguration.getDouble(key + ".Z") == block.getLocation().getZ()) {

                double health = fileConfiguration.getDouble(key + ".CoreHealth");
                double energy = fileConfiguration.getDouble(key + ".CoreEnergy");
                double shield = fileConfiguration.getDouble(key + ".CoreShield");

                if(health - 1.0 > 0) {
                    fileConfiguration.set(key + ".CoreHealth",health-1.0);
                    for (Player p : Bukkit.getOnlinePlayers()){p.playSound(block.getLocation().add(0,1,0), Sound.ENTITY_GENERIC_EXPLODE,1.0f,1.0f);}
                    event.setCancelled(true);
                }
                else {
                    for (Player p : Bukkit.getOnlinePlayers()){p.playSound(block.getLocation().add(0,1,0), Sound.ENTITY_GENERIC_EXPLODE,1.0f,1.0f);}
                    for (Player p : Bukkit.getOnlinePlayers()){Bukkit.getScheduler().runTaskLater(Extendor.getPlugin(), () -> p.playSound(block.getLocation().add(0,1,0), Sound.ENTITY_GENERIC_EXPLODE,1.0f,1.0f),20L);}
                    for (Player p : Bukkit.getOnlinePlayers()){Bukkit.getScheduler().runTaskLater(Extendor.getPlugin(), () -> p.playSound(block.getLocation().add(0,1,0), Sound.ENTITY_GENERIC_EXPLODE,1.0f,1.0f),40L);}
                }
            }
        }
    }
    public void showCore(Player player,Block block) {
        for(String key : fileConfiguration.getKeys(false)){
            if(fileConfiguration.getDouble(key + ".X") == block.getLocation().getX() && fileConfiguration.getDouble(key + ".Y") == block.getLocation().getY() && fileConfiguration.getDouble(key + ".Z") == block.getLocation().getZ()) {
                player.sendMessage("Owner: " + getOwner().toString());
                player.sendMessage("Active: " + getActive());
                player.sendMessage("Height: " + getHeight());
                player.sendMessage("Deep: " + getDeep());
                player.sendMessage("Range: " + getRange());
                player.sendMessage("World: " + getWorld());
                player.sendMessage("X: " + getX());
                player.sendMessage("Y: " + getY());
                player.sendMessage("Z: " + getZ());
                player.sendMessage("CoreHealth: " + getCoreHealth());
                player.sendMessage("CoreEnergy: " + getCoreEnergy());
                player.sendMessage("CoreShield: " + getCoreShield());
                player.sendMessage("Group: " + getGroup().toString());
            }
            else {
                player.sendMessage(ChatColor.RED + "Core Not Exist!!");
            }
        }
    }
    public static boolean isInCoreArea(Block block) {
        for(String key : fileConfiguration.getKeys(false)){
            if(
                Math.abs(block.getLocation().getX()-Double.parseDouble(Objects.requireNonNull(fileConfiguration.getString(key + ".X")))) <= Integer.parseInt(Objects.requireNonNull(fileConfiguration.getString(key + ".Range"))) &&
                Math.abs(block.getLocation().getZ()-Double.parseDouble(Objects.requireNonNull(fileConfiguration.getString(key + ".Z")))) <= Integer.parseInt(Objects.requireNonNull(fileConfiguration.getString(key + ".Range"))) &&
                block.getLocation().getY() <= Double.parseDouble(Objects.requireNonNull(fileConfiguration.getString(key + ".Y"))) + Integer.parseInt(Objects.requireNonNull(fileConfiguration.getString(key + ".Height"))) &&
                block.getLocation().getY() >= Double.parseDouble(Objects.requireNonNull(fileConfiguration.getString(key + ".Y"))) - Integer.parseInt(Objects.requireNonNull(fileConfiguration.getString(key + ".Deep")))
            )
            {
                return true;
            }
        }
        return false;
    }
    public static CoreData getNearByCore(Block block) {
        for(String key : fileConfiguration.getKeys(false)){
            if(Math.abs(block.getLocation().getX()-Double.parseDouble(Objects.requireNonNull(fileConfiguration.getString(key + ".X")))) <= Integer.parseInt(Objects.requireNonNull(fileConfiguration.getString(key + ".Range"))) && Math.abs(block.getLocation().getZ()-Double.parseDouble(Objects.requireNonNull(fileConfiguration.getString(key + ".Z")))) <= Integer.parseInt(Objects.requireNonNull(fileConfiguration.getString(key + ".Range"))) && block.getLocation().getY() <= Double.parseDouble(Objects.requireNonNull(fileConfiguration.getString(key + ".Y"))) + Integer.parseInt(Objects.requireNonNull(fileConfiguration.getString(key + ".Height"))) && block.getLocation().getY() >= Double.parseDouble(Objects.requireNonNull(fileConfiguration.getString(key + ".Y"))) - Integer.parseInt(Objects.requireNonNull(fileConfiguration.getString(key + ".Deep")))){
                CoreData result = new CoreData(key);
                return result;
            }
        }
        return null;
    }
}
