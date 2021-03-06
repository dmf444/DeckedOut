package com.dmf444.deckedout.configs;


import com.dmf444.deckedout.configs.models.ArtifactConfig;
import com.dmf444.deckedout.configs.models.GeneralConfig;
import com.dmf444.deckedout.configs.models.PrizePoolConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.*;
import java.util.HashMap;

public class JsonManager {

    private static File configLocation;
    private static Gson json = new GsonBuilder().setPrettyPrinting().create();

    private static HashMap<String, IJsonFile> fileTable = new HashMap<>();


    public JsonManager(File folderLocation) {
        configLocation = folderLocation;
    }


    public void initializeConfigs(){
        loadAllConfigs();

        if(!configLocation.exists()) {
            configLocation.mkdirs();
        }

        readFileToRuntime(FileEnum.GENERAL);
        readFilesToRuntime();
    }

    private void loadAllConfigs() {
        this.addToManager(FileEnum.GENERAL, new GeneralConfig());
        this.addToManager(FileEnum.ARTIFACT_SETS, new ArtifactConfig());
        this.addToManager(FileEnum.PRIZE_POOL, new PrizePoolConfig());
        //this.addToManager(FileEnum.FTB_UTILITIES, new FTBUtilsConfig());
    }

    public <T> T getConfig(Class<T> configClass) {
        for(IJsonFile fileTypes : fileTable.values()) {
            if(fileTypes.getClass().equals(configClass)) {
                return configClass.cast(fileTypes);
            }
        }
        return null;
    }


    public void writeToFiles() {
        for(String fileTypes : fileTable.keySet()) {
            writeToFile(fileTypes);
        }
    }

    public void readFilesToRuntime(){

        for(String fileTypes : fileTable.keySet()) {
            readFileToRuntime(fileTypes);
        }
    }

    public void readFileToRuntime(FileEnum fileEnum) {
        readFileToRuntime(fileEnum.name());
    }

    private void readFileToRuntime(String localName) {
        IJsonFile file = fileTable.get(localName);
        File configFile = getFileFromString(file.getFileName());

        try {
            if(configFile.createNewFile() || configFile.length() == 0) {
                writeFile(configFile, file.getDefaultJson());
                fileTable.replace(localName, file.getDefaultJson());
            } else {
                IJsonFile loadFile = loadFile(configFile, file.getClass());
                fileTable.replace(localName, loadFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(FileEnum fileEnum) {
        writeToFile(fileEnum.name());
    }

    private void writeToFile(String localName) {
        IJsonFile file = fileTable.get(localName);
        File configFile = getFileFromString(file.getFileName());
        writeFile(configFile, file);
    }

    public void addToManager(FileEnum fileType, IJsonFile jsonContainer){
        fileTable.put(fileType.name(), jsonContainer);
    }

    public IJsonFile getConfig(FileEnum fileEnum) {
        return fileTable.get(fileEnum.name());
    }



    private static File getFileFromString(String fileName){
        return new File(configLocation + File.separator + fileName);
    }


    /**
     * Takes in a string path and a class type. Uses GSON to read the path type,
     * and returns the model, if it exists. Null otherwise.
     *
     * @param file Files you want to read from.
     * @param type the class type for GSON to reflect
     * @param <T> Type for GSON to load from
     * @return Object of type T, or null on any IOException
     */
    private static <T extends IJsonFile> T loadFile(File file, Class<T> type) {
        Reader reader;

        try {
            reader = new FileReader(file);
            T objInstance = json.fromJson(reader, type);
            reader.close();
            return objInstance;
        } catch (IOException e) {

        }
        return null;
    }

    /**
     * Writes a file to the disk.
     * @param file the file that's to be written.
     * @param object the IJsonData object to write to the file.
     */
    private static void writeFile(File file, IJsonFile object) {
        String fileText = json.toJson(object, object.getClass());
        try (PrintWriter out = new PrintWriter(file)) {
            out.println(fileText);
        } catch (IOException e) {

        }
    }
}
