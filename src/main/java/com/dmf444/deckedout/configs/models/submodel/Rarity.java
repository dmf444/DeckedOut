package com.dmf444.deckedout.configs.models.submodel;

public enum Rarity {
    COMMON(9, "Common", "§r"),
    UNCOMMON(5, "Uncommon", "§e"),
    RARE(3, "Rare", "§b"),
    ULTRA_RARE(1, "Ultra Rare", "§c");


    private int defaultRarity;
    private String localizedName;
    private String colorString;
    Rarity(int count, String localizedName, String color) {
        this.defaultRarity = count;
        this.localizedName = localizedName;
        this.colorString = color;
    }

    public int getDefaultRarity() {
        return defaultRarity;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public String getColorString() {
        return colorString;
    }
}
