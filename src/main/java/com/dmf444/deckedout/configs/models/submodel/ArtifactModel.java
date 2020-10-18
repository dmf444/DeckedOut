package com.dmf444.deckedout.configs.models.submodel;

import com.google.gson.internal.LinkedTreeMap;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Map;

public class ArtifactModel {

    private Rarity rarity;
    private Map<String, Object> stack;
    private int newSetCount;
    private String name;

    public ArtifactModel(ItemStack stack, Rarity rarity, int count, String objectName) {
        this.stack = stack.serialize();
        this.stack.remove("meta");
        this.rarity = rarity;
        this.newSetCount = count;
        this.name = objectName;
    }


    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public ItemStack getStack() {
        ItemStack itemStack = ItemStack.deserialize(stack);

        ItemMeta meta = manualDeserializeMetadata(itemStack.getItemMeta());
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    private ItemMeta manualDeserializeMetadata(ItemMeta itemMeta) {
        itemMeta.setDisplayName(this.name);

        return itemMeta;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack.serialize();
        this.stack.remove("meta");
    }

    public int getNewSetCount() {
        return newSetCount;
    }

    public void setNewSetCount(int newSetCount) {
        this.newSetCount = newSetCount;
    }

}
