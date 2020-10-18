package com.dmf444.deckedout.configs.models.submodel;

import java.util.ArrayList;

public class ArtifactSets {

    private ArrayList<ArtifactModel> artifacts;
    private String name;


    public ArtifactSets(String name, ArrayList<ArtifactModel> artifacts) {
        this.name = name;
        this.artifacts = artifacts;
    }

    public ArrayList<ArtifactModel> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(ArrayList<ArtifactModel> artifacts) {
        this.artifacts = artifacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
