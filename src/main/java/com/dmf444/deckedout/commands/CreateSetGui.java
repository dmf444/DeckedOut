package com.dmf444.deckedout.commands;

import com.dmf444.deckedout.DeckedOut;
import com.dmf444.deckedout.configs.FileEnum;
import com.dmf444.deckedout.configs.models.ArtifactConfig;
import com.dmf444.deckedout.configs.models.GeneralConfig;
import com.dmf444.deckedout.configs.models.submodel.ArtifactModel;
import com.dmf444.deckedout.configs.models.submodel.ArtifactSets;
import com.dmf444.deckedout.configs.models.submodel.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateSetGui implements Listener {

    private String setName;
    private Inventory inv;
    private String icon = ((GeneralConfig) DeckedOut.getManager().getConfig(FileEnum.GENERAL)).getIcon();



    public CreateSetGui(String setname) {
        this.setName = setname;

        this.inv = Bukkit.createInventory(null, 18, setname);
        padInventory();
    }

    public void openInventory(HumanEntity entity) {
        entity.openInventory(inv);
    }

    @EventHandler
    public void closeInventory(InventoryCloseEvent event) {
        if(event.getInventory() == inv) {


            if(event.getInventory().firstEmpty() == -1) {

                ArrayList<ArtifactModel> artifacts = new ArrayList<>();

                ItemStack[] stacks = event.getInventory().getContents();
                for(int i = 0; i < 4; i++) {
                    Rarity rarity = Rarity.values()[i];
                    int stackSlot = i + 2;
                    int stackSize = rarity.getDefaultRarity();
                    if(stacks[stackSlot].getAmount() != 1) {
                        stackSize = stacks[stackSlot].getAmount();
                        stacks[stackSlot].setAmount(1);
                    }

                    //ItemMeta meta = stacks[stackSlot].getItemMeta();
                    String displayName = String.format("%4$s%1$s %2$s Set - %3$s %1$s", this.icon, this.setName, rarity.getLocalizedName(), rarity.getColorString());
                    //meta.setDisplayName(displayName);
                    //stacks[stackSlot].setItemMeta(meta);

                    artifacts.add(new ArtifactModel(stacks[stackSlot], rarity, stackSize, displayName));
                }

                ArtifactSets set = new ArtifactSets(this.setName, artifacts);
                ArtifactConfig setConfig = (ArtifactConfig) DeckedOut.getManager().getConfig(FileEnum.ARTIFACT_SETS);
                setConfig.addSet(set);
                DeckedOut.getManager().writeToFile(FileEnum.ARTIFACT_SETS);

                event.getPlayer().sendMessage("Set " + this.setName + " has been created.");
            }
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() != inv) return;

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        ArrayList<Material> ignoreType = new ArrayList<>();
        ignoreType.add(Material.BLACK_STAINED_GLASS_PANE);
        ignoreType.add(Material.GREEN_STAINED_GLASS_PANE);
        ignoreType.add(Material.BLUE_STAINED_GLASS_PANE);
        ignoreType.add(Material.RED_STAINED_GLASS_PANE);
        ignoreType.add(Material.PURPLE_STAINED_GLASS_PANE);
        if (clickedItem == null || clickedItem.getType() == Material.AIR || ignoreType.contains(clickedItem.getType())) {
            e.setCancelled(true);
            return;
        }


    }

    private void padInventory() {
        this.inv.setItem(0, createGuiItem(Material.BLACK_STAINED_GLASS_PANE, "Artifacts"));
        this.inv.setItem(1, createGuiItem(Material.BLACK_STAINED_GLASS_PANE, "Artifacts"));

        for (int i = 6; i < 18; i++) {
            this.inv.setItem(i, createGuiItem(Material.BLACK_STAINED_GLASS_PANE, "Artifacts"));
        }

        //11, 12 ,13, 14
        this.inv.setItem(11, createGuiItem(Material.GREEN_STAINED_GLASS_PANE, "Common"));
        this.inv.setItem(12, createGuiItem(Material.BLUE_STAINED_GLASS_PANE, "Uncommon"));
        this.inv.setItem(13, createGuiItem(Material.RED_STAINED_GLASS_PANE, "Rare"));
        this.inv.setItem(14, createGuiItem(Material.PURPLE_STAINED_GLASS_PANE, "Ultra Rare"));

    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);

        return item;
    }
}
