package yuncore.extendor;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import yuncore.extendor.PlayerChats.commands.SetChatTabComplete;
import yuncore.extendor.PlayerChats.events.OnPlayerChat;
import yuncore.extendor.PlayerChats.commands.SetChat;
import yuncore.extendor.events.*;
import yuncore.extendor.events.OnPlayerFished;
import yuncore.extendor.lock.events.OpenTo;
import yuncore.extendor.lock.events.OnInteractLockableBlock;
import yuncore.extendor.lock.events.OpenToTabComplete;
import yuncore.extendor.recipes.*;
import yuncore.extendor.sethome.HomeConfiguration;
import yuncore.extendor.sethome.commands.home;
import yuncore.extendor.sethome.commands.homeTabComplete;
import yuncore.extendor.to.commands.to;
import yuncore.extendor.to.commands.toTabComplete;

import java.util.Objects;

public final class Extendor extends JavaPlugin {

    private static Extendor plugin;
    @Override
    public void onEnable() {
        plugin = this;


        getConfig().options().copyDefaults();
        saveDefaultConfig();

        HomeConfiguration.initialize();

        System.out.println(ChatColor.GOLD + "Extendor V1.3");
        wood_fix.init();
        extra_tools.init();
        extra_foods.init();
        extra_materials.init();
        getServer().getPluginManager().registerEvents(new OnPlayerGetItem(),this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(),this);
        getServer().getPluginManager().registerEvents(new OnPlayerChat(),this);
        getServer().getPluginManager().registerEvents(new OnInteractLockableBlock(),this);
        getServer().getPluginManager().registerEvents(new OnMobDeath(),this);
        getServer().getPluginManager().registerEvents(new OnPlayerInteractBeeHive(),this);
        getServer().getPluginManager().registerEvents(new OnPlayerFished(),this);
        getServer().getPluginManager().registerEvents(new OnPlayerDeath(),this);
        Objects.requireNonNull(getCommand("SetChat")).setExecutor(new SetChat());
        Objects.requireNonNull(getCommand("SetChat")).setTabCompleter(new SetChatTabComplete());
        Objects.requireNonNull(getCommand("home")).setExecutor(new home());
        Objects.requireNonNull(getCommand("home")).setTabCompleter(new homeTabComplete());
        Objects.requireNonNull(getCommand("open_to")).setExecutor(new OpenTo());
        Objects.requireNonNull(getCommand("open_to")).setTabCompleter(new OpenToTabComplete());
        Objects.requireNonNull(getCommand("to")).setExecutor(new to());
        Objects.requireNonNull(getCommand("to")).setTabCompleter(new toTabComplete());

    }

    @Override
    public void onDisable() {
        HomeConfiguration.reload();
    }

    public static Extendor getPlugin() {
        return plugin;
    }
}
