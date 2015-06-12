/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.pearson.cti.settingsreader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author UBOSHET
 */
public class SettingReaderFactory {
    
    private static final String SETTINGS_SPLIT_CHARACTER = "#";
    /**
     * Public such that if they want they can use this setting and ensure 
     * consistency
     */
    public static final String SETTINGS_SAVED_AS_JSON_FILE = "jsonfilelocation";
    public static final String SETTINGS_AVAILABLE_AS_JSON_ON_URL = "jsonurl";
    // Ensures that there is always only on set of settings
    private static SettingReader currentSettings;
    
    public static SettingReader getSettingReader(String criteria) {
        if (currentSettings != null) {
            return currentSettings;
        }
        
        String[] settingParameters = criteria.split(SETTINGS_SPLIT_CHARACTER);
        
        if(settingParameters.length > 0 && settingParameters[0].equalsIgnoreCase(SETTINGS_SAVED_AS_JSON_FILE)) {
            File settingsfileLocation = new File(settingParameters[1]);
            currentSettings = JSONFileSettingReader.getJSONFileSettingReader(settingsfileLocation);
            return currentSettings;
        }
        
        if (settingParameters.length > 0 && settingParameters[0].equalsIgnoreCase(SETTINGS_AVAILABLE_AS_JSON_ON_URL)) {
            try {
                URL url = new URL(settingParameters[1]);
                currentSettings = URLSettingReader.getURLSettingReader(url);
                return currentSettings;
            } catch (MalformedURLException ex) {
                Logger.getLogger(SettingReaderFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // A catch all incase it does not fit into any of the known slots
        // If anyone wants to make this work with Spring or Play it will count
        // as extra credit
        return null;
    }
    
}
