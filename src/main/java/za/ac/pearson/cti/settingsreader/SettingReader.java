/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.pearson.cti.settingsreader;

import java.util.List;
import java.util.Map;

/**
 *
 * @author UBOSHET
 */
public interface SettingReader {
    
    /**
     * This method returns to you the settings in the following form 
     * Key pair: Setting name, Setting value
     * @return The settings for your program
     */
    public Map<String, String> getAllSettings();
    
    /**
     * Gives you the setting value for a specific key
     * @param keyValue The setting you are looking for example "port"
     * @return the value of that setting, null if it does not exist
     */
    public String getSettingFor(String keyValue);
    
    /** 
     * Gives you a list of all available settings in the file
     * @return a list of all available settings
     */
    public List<String> getAvailableSettingParameters();
    
}
