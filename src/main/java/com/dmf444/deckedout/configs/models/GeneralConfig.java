package com.dmf444.deckedout.configs.models;

import com.dmf444.deckedout.configs.IJsonFile;

public class GeneralConfig implements IJsonFile {


    private String icon;
    private String lootboxName;


    @Override
    public String getFileName() {
        return "general.json";
    }

    @Override
    public IJsonFile getDefaultJson() {
        GeneralConfig config = new GeneralConfig();
        config.setIcon("☠");
        config.setLootboxName("Decked Out Loot");
        return config;
    }

    public String getLootboxName() {
        return lootboxName;
    }

    public void setLootboxName(String lootboxName) {
        this.lootboxName = lootboxName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
