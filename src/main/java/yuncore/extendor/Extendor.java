package yuncore.extendor;

import org.bukkit.plugin.java.JavaPlugin;
import yuncore.extendor.CorProtector.Core;
import yuncore.extendor.CorProtector.CoreData;
import yuncore.extendor.CorProtector.BlockModifyInArea;
import yuncore.extendor.CorProtector.CoreTabComplete;
import yuncore.extendor.PlayerChats.commands.SetChatTabComplete;
import yuncore.extendor.PlayerChats.events.OnPlayerChat;
import yuncore.extendor.PlayerChats.commands.SetChat;
import yuncore.extendor.events.*;
import yuncore.extendor.events.OnPlayerFished;
import yuncore.extendor.lock.OpenTo;
import yuncore.extendor.lock.OnInteractLockableBlock;
import yuncore.extendor.lock.OpenToTabComplete;
import yuncore.extendor.recipes.*;
import yuncore.extendor.sethome.HomeConfiguration;
import yuncore.extendor.sethome.home;
import yuncore.extendor.sethome.homeTabComplete;

import java.util.Objects;

public final class Extendor extends JavaPlugin {

    private static Extendor plugin;
    @Override
    public void onEnable() {
        plugin = this;


        getConfig().options().copyDefaults();
        saveDefaultConfig();

        HomeConfiguration.initialize();
        CoreData.initialize();

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
        getServer().getPluginManager().registerEvents(new BlockModifyInArea(),this);

        Objects.requireNonNull(getCommand("SetChat")).setExecutor(new SetChat());
        Objects.requireNonNull(getCommand("SetChat")).setTabCompleter(new SetChatTabComplete());
        Objects.requireNonNull(getCommand("home")).setExecutor(new home());
        Objects.requireNonNull(getCommand("home")).setTabCompleter(new homeTabComplete());
        Objects.requireNonNull(getCommand("open_to")).setExecutor(new OpenTo());
        Objects.requireNonNull(getCommand("open_to")).setTabCompleter(new OpenToTabComplete());
        Objects.requireNonNull(getCommand("Core")).setExecutor(new Core());
        Objects.requireNonNull(getCommand("Core")).setTabCompleter(new CoreTabComplete());
    }

    @Override
    public void onDisable() {
        HomeConfiguration.reload();
    }

    public static Extendor getPlugin() {
        return plugin;
    }
}
