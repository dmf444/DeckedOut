package com.dmf444.deckedout.configs;


public interface IJsonFile {

    /**
     * The name of this file as a json.
     * @return all lower case name of files, strictly in format: *.json
     */
    public String getFileName();


    public IJsonFile getDefaultJson();


}