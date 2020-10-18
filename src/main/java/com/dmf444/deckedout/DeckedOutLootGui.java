package com.dmf444.deckedout;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;

public class DeckedOutLootGui implements Listener {
    private final Inventory inv;
    private ItemStack[] originalInv;
    private ShulkerBox box;


    public DeckedOutLootGui(ShulkerBox box) {
        this.inv = Bukkit.createInventory(null, 9, "☠ Decked Out Loot ☠");
        this.box = box;

        if(this.box.hasMetadata("deckedoutbox") || !this.box.getInventory().isEmpty()) {
            loadBoxContent();
        } else {
            randomizeLootContent();
        }
    }

    private void loadBoxContent() {
        ItemStack[] itemStack = this.box.getInventory().getContents();
        for(int i = 0; i < 9; i++) {
            this.inv.setItem(i, itemStack[i]);
        }
    }


    public void randomizeLootContent() {

        ItemStack[] prizes = PrizeManager.getPrizes();
        this.inv.setContents(prizes);



        this.box.getInventory().setContents(prizes);
        this.box.setMetadata("deckedoutbox", new FixedMetadataValue(Bukkit.getPluginManager().getPlugin("DeckedOut"), true));

    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);

        return item;
    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent) {
        box.open();
        ent.openInventory(inv);

        this.originalInv = ent.getInventory().getContents();

        for(int i = 9; i < 36; i++) {
            ent.getInventory().setItem(i, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, "Artifact Board"));
        }

    }

    @EventHandler
    public void closeInventory(InventoryCloseEvent event) {
        if(event.getInventory() == inv) {
            event.getPlayer().getInventory().setContents(this.originalInv);
            this.box.getInventory().setContents(this.inv.getContents());
            box.close();
        }
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() != inv) return;

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        final Player p = (Player) e.getWhoClicked();

        // Using slots click is a best option for your inventory click's
        p.sendMessage("You clicked at slot " + e.getRawSlot());
        p.sendMessage("With name:" + clickedItem.getItemMeta().getDisplayName() + " aaaand " + clickedItem.getItemMeta().getLocalizedName());
    }

    // Cancel dragging in our inventory
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory() == inv) {
            e.setCancelled(true);
        }
    }
}