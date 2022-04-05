package yuncore.extendor.CorProtector;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Lightable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import yuncore.extendor.Extendor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CoreData {
    private final String OwnerName;
    private final Block core;
    private boolean active;
    private final UUID Owner;
    private final List<UUID> Group;
    private final double x;
    private final double y;
    private final double z;
    private final World world;
    private final double CoreHealth;
    private final double CoreEnergy;
    private final double CoreShield;
    private final int Height;
    private final int Deep;
    private final int Range;
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
    //是否該方塊在任何Core的範圍之中
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
    //取得目前方塊位置附近的CoreData
    public static CoreData getNearByCore(Block block) {
        for(String key : fileConfiguration.getKeys(false)){
            if(Math.abs(block.getLocation().getX()-Double.parseDouble(Objects.requireNonNull(fileConfiguration.getString(key + ".X")))) <= Integer.parseInt(Objects.requireNonNull(fileConfiguration.getString(key + ".Range"))) && Math.abs(block.getLocation().getZ()-Double.parseDouble(Objects.requireNonNull(fileConfiguration.getString(key + ".Z")))) <= Integer.parseInt(Objects.requireNonNull(fileConfiguration.getString(key + ".Range"))) && block.getLocation().getY() <= Double.parseDouble(Objects.requireNonNull(fileConfiguration.getString(key + ".Y"))) + Integer.parseInt(Objects.requireNonNull(fileConfiguration.getString(key + ".Height"))) && block.getLocation().getY() >= Double.parseDouble(Objects.requireNonNull(fileConfiguration.getString(key + ".Y"))) - Integer.parseInt(Objects.requireNonNull(fileConfiguration.getString(key + ".Deep")))){
                return new CoreData(key);
            }
        }
        return null;
    }
    //取得目前方塊位置的CoreData
    public static CoreData getCurrentCore(Block block) {
        //先把舊檔案儲存後，再重新讀取
        CoreData.save();
        for(String key : fileConfiguration.getKeys(false)){
            if(
                block.getLocation().getX() == fileConfiguration.getInt(key + ".X")
                && block.getLocation().getY() == fileConfiguration.getInt(key + ".Y")
                && block.getLocation().getZ() == fileConfiguration.getInt(key + ".Z")
            ) {
                return new CoreData(key);
            }
        }
        return null;
    }
    //設定CoreData
    public CoreData(Block block,Player player) {
        OwnerName = player.getName();
        active = false;
        Owner = player.getUniqueId();
        Group = new ArrayList<>();
        world = block.getWorld();
        x = block.getX();
        y = block.getY();
        z = block.getZ();
        Height = 4;
        Deep = 4;
        Range = 4;
        CoreHealth = 20.0;
        CoreEnergy = 100.0;
        CoreShield = 4.0;
        core = block;
        saveCore();
    }
    //找到key值符合資料的CoreData並讀取
    public CoreData(String key) {
        OwnerName = key;
        active = fileConfiguration.getBoolean(key + ".active");
        Owner = UUID.fromString(Objects.requireNonNull(fileConfiguration.getString(key + ".Owner")));
        Group = toUUIDList(fileConfiguration.getStringList(key + ".Group"));
        world = Bukkit.getWorld(Objects.requireNonNull(fileConfiguration.getString(key + ".World")));
        x = fileConfiguration.getDouble(key + ".X");
        y = fileConfiguration.getDouble(key + ".Y");
        z = fileConfiguration.getDouble(key + ".Z");
        Height = fileConfiguration.getInt(key + ".Height");
        Deep = fileConfiguration.getInt(key + ".Deep");
        Range = fileConfiguration.getInt(key + ".Range");
        CoreHealth = fileConfiguration.getDouble(key + ".CoreHealth");
        CoreEnergy = fileConfiguration.getDouble(key + ".CoreEnergy");
        CoreShield = fileConfiguration.getDouble(key + ".CoreShield");
        Location location = new Location(getWorld(),getX(),getY(),getZ());
        core = Objects.requireNonNull(Bukkit.getWorld(getWorld().getName())).getBlockAt(location);
        saveCore();
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
    public void saveCore() {
        fileConfiguration.set(OwnerName + ".Owner",Owner.toString());
        fileConfiguration.set(OwnerName + ".Active",active);
        fileConfiguration.set(OwnerName + ".Height",Height);
        fileConfiguration.set(OwnerName + ".Deep",Deep);
        fileConfiguration.set(OwnerName + ".Range",Range);
        fileConfiguration.set(OwnerName + ".World",world.getName());
        fileConfiguration.set(OwnerName + ".X",x);
        fileConfiguration.set(OwnerName + ".Y",y);
        fileConfiguration.set(OwnerName + ".Z",z);
        fileConfiguration.set(OwnerName + ".CoreHealth",CoreHealth);
        fileConfiguration.set(OwnerName + ".CoreEnergy",CoreEnergy);
        fileConfiguration.set(OwnerName + ".CoreShield",CoreShield);
        fileConfiguration.set(OwnerName + ".Group", Group);
        CoreData.save();
    }
    public void deleteCore() {
        Set<String> set = Objects.requireNonNull(fileConfiguration).getKeys(false);
        Set<String> toRemove = new HashSet<>();
        boolean found = false;
        for (String s : set) { if (s.equals(OwnerName)) { toRemove.add(s); found = true; }}
        if(found) {System.out.println("removed " + OwnerName); }
        else { System.out.println("can not found " + "\"" + OwnerName + "\""); }
        for (String s : toRemove) { fileConfiguration.set(s, null); }
        CoreData.save();
    }
    public void showCore(Player player) {
        List<String> strings = getCoreInformationStringList();
        for (String string : strings) {
            player.sendMessage(string);
        }
    }
    public Inventory getCoreMenu(Player player) {
        Inventory CoreGUI = Bukkit.createInventory(player,36,ChatColor.DARK_GRAY + "Core : " + OwnerName);
        //
        ItemStack information = new ItemStack(Material.BOOK);
        ItemMeta information_meta = information.getItemMeta();
        information_meta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + "資訊");
        information_meta.setLocalizedName("CoreGUI.information");
        List<String> information_lore = getCoreInformationStringList();
        information_meta.setLore(information_lore);
        information.setItemMeta(information_meta);
        //
        ItemStack pickup = new ItemStack(Material.WOODEN_PICKAXE);
        ItemMeta pickup_meta = pickup.getItemMeta();
        pickup_meta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + "收回");
        pickup_meta.setLocalizedName("CoreGUI.pickup");
        pickup.setItemMeta(pickup_meta);
        //
        ItemStack active = new ItemStack(Material.LEVER);
        ItemMeta active_meta = active.getItemMeta();
        active_meta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + "開關");
        active_meta.setLocalizedName("CoreGUI.active");
        active.setItemMeta(active_meta);
        //
        ItemStack add = new ItemStack(Material.EMERALD);
        ItemMeta add_meta = add.getItemMeta();
        add_meta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + "Add");
        add_meta.setLocalizedName("CoreGUI.add");
        add.setItemMeta(add_meta);
        //
        ItemStack remove = new ItemStack(Material.REDSTONE);
        ItemMeta remove_meta = add.getItemMeta();
        remove_meta.setDisplayName(ChatColor.RESET + "" + ChatColor.RED + "Remove");
        remove_meta.setLocalizedName("CoreGUI.remove");
        remove.setItemMeta(remove_meta);
        //
        CoreGUI.setItem(0,active);
        CoreGUI.setItem(2,pickup);
        CoreGUI.setItem(4,information);
        CoreGUI.setItem(6,add);
        CoreGUI.setItem(8,remove);
        return CoreGUI;
    }
    public List<String> getCoreInformationStringList() {
        List<String> strings = new ArrayList<>();
        strings.add(ChatColor.GOLD + "Owner: " + ChatColor.WHITE + OwnerName);
        strings.add(ChatColor.GOLD + "Active: " + ChatColor.WHITE + active);
        strings.add(ChatColor.GOLD + "World: " + ChatColor.WHITE +  world.getName());
        strings.add(ChatColor.GOLD + "Location: " + ChatColor.RED + x + " " + ChatColor.GREEN + y + " " + ChatColor.BLUE + z);
        strings.add(ChatColor.GOLD + "Height: " + ChatColor.WHITE +  Height);
        strings.add(ChatColor.GOLD + "Deep: " + ChatColor.WHITE +  Deep);
        strings.add(ChatColor.GOLD + "Range: " + ChatColor.WHITE +  Range);
        strings.add(ChatColor.GOLD + "Health: " + ChatColor.WHITE +  CoreHealth);
        strings.add(ChatColor.GOLD + "Energy: " + ChatColor.WHITE +  CoreHealth);
        strings.add(ChatColor.GOLD + "Shield: " + ChatColor.WHITE +  CoreShield);
        strings.add(ChatColor.GOLD + "Group: " + ChatColor.WHITE +  getGroupName().toString());
        return strings;
    }
    //
    public boolean getActive(){ return active; }
    public void setActive(boolean value) {
        active = value;
        Lightable lightable = (Lightable) core.getBlockData();
        lightable.setLit(active);
        core.setBlockData(lightable);
        for (Player p : Bukkit.getOnlinePlayers()){p.playSound(core.getLocation().add(0,1,0), Sound.UI_BUTTON_CLICK,0.1f,1.0f);}
        CoreData.save();
    }
    public UUID getOwner() { return Owner; }
    public String getOwnerName() { return OwnerName; }
    public List<UUID> getGroup() { return Group; }
    public List<String> getGroupName() {
        List<String> result = new ArrayList<>();
        for(int i=0;i<getGroup().size();i++) {
            result.add(getGroup().get(i).toString());
        }
        return result;
    }
    public void addGroup(Player player) {
        getGroup().add(player.getUniqueId());
        saveCore();
    }
    public void removeGroup(Player player) {
        List<UUID> temp = getGroup();
        temp.remove(player.getUniqueId());
        saveCore();
    }
    public void cleanGroup() {
        List<UUID> temp = new ArrayList<>();
        saveCore();
    }
    public int getDeep() { return Deep; }
    public int getHeight() { return Height; }
    public int getRange() { return Range; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    public double getCoreEnergy() { return CoreEnergy; }
    public double getCoreHealth() { return CoreHealth; }
    public double getCoreShield() { return CoreShield; }
    public World getWorld() { return world; }
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
    public static void OnCoreMined(Player player, Block block, BlockBreakEvent event) {
        for(String key : fileConfiguration.getKeys(false)) {
            if(fileConfiguration.getDouble(key + ".X") == block.getLocation().getX() && fileConfiguration.getDouble(key + ".Y") == block.getLocation().getY() && fileConfiguration.getDouble(key + ".Z") == block.getLocation().getZ()) {
                double health = fileConfiguration.getDouble(key + ".CoreHealth");
                if(health - 1.0 > 0) {
                    fileConfiguration.set(key + ".CoreHealth",health-1.0);
                    for (Player p : Bukkit.getOnlinePlayers()){p.playSound(block.getLocation().add(0,1,0), Sound.ENTITY_GENERIC_EXPLODE,1.0f,1.0f);}
                    event.setCancelled(true);
                }
                else {
                    for(int i=0;i<3;i++) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            Bukkit.getScheduler().runTaskLater(Extendor.getPlugin(), () -> p.playSound(block.getLocation().add(0,1,0), Sound.ENTITY_GENERIC_EXPLODE,1.0f,1.0f),20L*i);
                        }
                    }
                }
            }
        }
    }
}
