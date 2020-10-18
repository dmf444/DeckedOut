package com.dmf444.deckedout;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class ShulkerShellListener implements Listener {

    Plugin deckedOut;

    public ShulkerShellListener(Plugin plugin) {
        this.deckedOut = plugin;
    }



    @EventHandler
    public void onShulkerOpenEvent(PlayerInteractEvent event) {

        Bukkit.getLogger().warning("[Decked Out] Event Called");

        if(event.hasBlock()){
            Bukkit.getLogger().warning("[Decked Out] Event Has Block");
            Block block = event.getClickedBlock();

            if (block.getState() instanceof ShulkerBox && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Bukkit.getLogger().warning("[Decked Out] Block is a shell");

                ShulkerBox box = (ShulkerBox) block.getState();
                Bukkit.getLogger().warning("[Decked Out] Box Name:" + box.getCustomName());
                if (box.getCustomName() != null && box.getCustomName().equals("☠ Decked Out Loot ☠")) {

                    Bukkit.getLogger().warning("[Decked Out] Cancelled and opened");
                    event.setCancelled(true);

                    DeckedOutLootGui gui = new DeckedOutLootGui(box);
                    Bukkit.getServer().getPluginManager().registerEvents(gui, this.deckedOut);
                    gui.openInventory(event.getPlayer());

                }
            }
        }
    }
}
