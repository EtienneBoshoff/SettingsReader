/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.pearson.cti.settingsreader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This File Reader works on any JSON file format
 * with the settings saved as Setting_Name : Setting_Value
 * this allows you to have global settings saved outside the source
 * code.  Which allows you to change your settings without the need 
 * to recompile the project
 * @author UBOSHET
 */
@Slf4j
public class JSONFileSettingReader implements SettingReader {

    // VARIABLES
    private static JSONFileSettingReader myself;
    private final File fileLocation;
    private final Map<String, String> settings;
    
    
    // Factory Constructor -- SHOULD NOT BE USED OUTSIDE FACTORY
    // See {@link za.ac.pearson.cti.settingsreader.SettingReaderFactory#getSettingReader}
    private JSONFileSettingReader(File settingFile) {
        fileLocation = settingFile;
        settings = new TreeMap<>();
    }
    
    public static JSONFileSettingReader getJSONFileSettingReader(File settingFile) {
        
        if (myself == null) {
            myself = new JSONFileSettingReader(settingFile);
        }
        return myself;
                
    }
    
    @Override
    public Map<String, String> getAllSettings() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(fileLocation));
            JSONObject jsonObject = (JSONObject) obj;
            settings.putAll(jsonObject);
        } catch (FileNotFoundException ex) {
            log.error(ex.toString());
        } catch (IOException | ParseException ex) {
            log.error(ex.toString());
        }
        return settings;
    }

    @Override
    public String getSettingFor(String keyValue) {
        if (settings.isEmpty()) {
            getAllSettings();
        }
        if (settings.containsKey(keyValue)) {
            return settings.get(keyValue);
        }
        return null;
    }
    
    @Override
    public List<String> getAvailableSettingParameters() {
        if (settings.isEmpty()) {
            getAllSettings();
        }
        
        List<String> settingParameters = new ArrayList<>();
        for (Object o : settings.keySet().toArray()) {
            settingParameters.add(o.toString());
        }
        
        return settingParameters;
    }
    
}
