package com.dmf444.deckedout.configs.models;

import com.dmf444.deckedout.configs.IJsonFile;
import com.dmf444.deckedout.configs.models.submodel.ArtifactSets;

import java.util.ArrayList;

public class ArtifactConfig implements IJsonFile {

    private ArrayList<ArtifactSets> artifactSets;


    @Override
    public String getFileName() {
        return "artifact_sets.json";
    }

    @Override
    public IJsonFile getDefaultJson() {
        ArtifactConfig config = new ArtifactConfig();
        config.setArtifactSets(new ArrayList<>());
        return config;
    }


    public ArrayList<ArtifactSets> getArtifactSets() {
        return artifactSets;
    }

    public void setArtifactSets(ArrayList<ArtifactSets> artifactSets) {
        this.artifactSets = artifactSets;
    }

    public void addSet(ArtifactSets set) {
        artifactSets.add(set);
    }
}
