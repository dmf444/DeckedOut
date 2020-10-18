package com.dmf444.deckedout;

import com.dmf444.deckedout.configs.FileEnum;
import com.dmf444.deckedout.configs.models.ArtifactConfig;
import com.dmf444.deckedout.configs.models.PrizePoolConfig;
import com.dmf444.deckedout.configs.models.submodel.ArtifactModel;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class PrizeManager {

    public static ItemStack[] getPrizes() {

        ArrayList<ArtifactModel> prizePool = ((PrizePoolConfig) DeckedOut.getManager().getConfig(FileEnum.PRIZE_POOL)).getPrizes();

        Random r = new Random();
        int result = r.nextInt(2) + 3;

        if(prizePool.size() < result) setReinitialization();
        prizePool = ((PrizePoolConfig) DeckedOut.getManager().getConfig(FileEnum.PRIZE_POOL)).getPrizes();
        ItemStack[] prizes = new ItemStack[result + 1];

        
        int coinCount = 12;
        for(int i = 0; i < result; i++) {
            r = new Random();
            int selection = r.nextInt(prizePool.size());
            ArtifactModel prizeArtifact = prizePool.remove(selection);

            prizes[i] = prizeArtifact.getStack();
            switch (prizeArtifact.getRarity()) {
                case ULTRA_RARE:
                    coinCount -= 1;
                case RARE:
                    coinCount -= 1;
                case UNCOMMON:
                    coinCount -= 1;
                case COMMON:
                    coinCount -= 1;
            }
        }

        DeckedOut.getManager().writeToFile(FileEnum.PRIZE_POOL);

        ItemStack item = new ItemStack(Material.EMERALD, coinCount);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("☠ Decked Out Coin ☠");
        item.setItemMeta(meta);
        prizes[result] = item;

        return prizes;
    }

    private static void setReinitialization() {
        PrizePoolConfig config = ((PrizePoolConfig) DeckedOut.getManager().getConfig(FileEnum.PRIZE_POOL));

        ArrayList<ArtifactModel> reinitPrizePool = new ArrayList<>();
        reinitPrizePool.addAll(config.getPrizes());

        ArtifactConfig artifactConfig = ((ArtifactConfig) DeckedOut.getManager().getConfig(FileEnum.ARTIFACT_SETS));
        artifactConfig.getArtifactSets().forEach(artifactSets -> {
            artifactSets.getArtifacts().forEach(artifactModel -> {
                for(int i = 0; i < artifactModel.getNewSetCount(); i++) {
                    reinitPrizePool.add(artifactModel);
                }
            });
        });

        Collections.shuffle(reinitPrizePool, new Random());

        config.setPrizes(reinitPrizePool);
        DeckedOut.getManager().writeToFile(FileEnum.PRIZE_POOL);
    }


}
