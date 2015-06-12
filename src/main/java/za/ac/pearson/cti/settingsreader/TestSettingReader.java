/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.pearson.cti.settingsreader;

import java.util.Map;

/**
 *
 * @author UBOSHET
 */
public class TestSettingReader {
    
    public static void main(String... args) {
        SettingReader settingsReader = SettingReaderFactory.getSettingReader("jsonfilelocation#src/main/resources/settingTest.json");
        Map<String,String> settings = settingsReader.getAllSettings();
        System.out.println("Number of settings extracted : " + settings.keySet().size());
        System.out.println("The value setting for passWord is : " + settingsReader.getSettingFor("passWord"));
        
    }
    
}
