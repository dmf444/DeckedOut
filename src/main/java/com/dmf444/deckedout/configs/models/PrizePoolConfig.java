package com.dmf444.deckedout.configs.models;

import com.dmf444.deckedout.configs.IJsonFile;
import com.dmf444.deckedout.configs.models.submodel.ArtifactModel;

import java.util.ArrayList;

public class PrizePoolConfig implements IJsonFile {

    private ArrayList<ArtifactModel> prizes;


    @Override
    public String getFileName() {
        return "prize_pool.json";
    }

    @Override
    public IJsonFile getDefaultJson() {
        PrizePoolConfig config = new PrizePoolConfig();
        config.setPrizes(new ArrayList<>());
        return config;
    }

    public ArrayList<ArtifactModel> getPrizes() {
        return prizes;
    }

    public void setPrizes(ArrayList<ArtifactModel> prizes) {
        this.prizes = prizes;
    }
}
