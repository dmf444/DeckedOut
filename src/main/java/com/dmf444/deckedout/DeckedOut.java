package com.dmf444.deckedout;

import com.dmf444.deckedout.commands.CreateSetCommand;
import com.dmf444.deckedout.configs.JsonManager;
import org.bukkit.plugin.java.JavaPlugin;


public final class DeckedOut extends JavaPlugin {

    private static JsonManager manager;


    @Override
    public void onEnable() {
        // Plugin startup logic

        manager = new JsonManager(getDataFolder());
        manager.initializeConfigs();


        getServer().getPluginManager().registerEvents(new ShulkerShellListener(this), this);
        this.getCommand("createset").setExecutor(new CreateSetCommand(this));
        getLogger().warning("[Decked Out] Initialization Completed");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JsonManager getManager() {
        return manager;
    }

}
