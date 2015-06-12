/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.pearson.cti.settingsreader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author uboshet
 */
@Slf4j
public class URLSettingReader implements SettingReader {
    
    // Variables
    private static URLSettingReader myself;
    private final URL url;
    private final Map<String,String> settings;
    
    // Factory Constructor -- SHOULD NOT BE USED OUTSIDE FACTORY
    // See {@link za.ac.pearson.cti.settingsreader.SettingReaderFactory#getSettingReader}
    private URLSettingReader(URL theURL) {
        url = theURL;
        settings = new TreeMap<>();
    }
    
    public static URLSettingReader getURLSettingReader(URL theURL) {
        if (myself == null) {
            myself = new URLSettingReader(theURL);
        }   
        return myself;
    }
    
    @Override
    public Map<String, String> getAllSettings() {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new InputStreamReader((InputStream) url.getContent()));
            JSONObject jsonObject = (JSONObject) obj;
            settings.putAll(jsonObject);
        } catch (ParseException | IOException ex) {
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
