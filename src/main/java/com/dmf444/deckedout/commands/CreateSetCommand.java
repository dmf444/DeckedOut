package com.dmf444.deckedout.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CreateSetCommand implements CommandExecutor {
    private Plugin deckedOutPlugin;

    public CreateSetCommand(Plugin plugin) {

        this.deckedOutPlugin = plugin;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player && args.length == 1) {
            Player player = (Player) sender;

            CreateSetGui createSetGui = new CreateSetGui(args[0]);
            Bukkit.getServer().getPluginManager().registerEvents(createSetGui, this.deckedOutPlugin);
            createSetGui.openInventory(player);

            return true;
        }

        return false;
    }
}
