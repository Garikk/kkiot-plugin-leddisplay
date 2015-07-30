/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager.configuration;

import kkdev.kksystem.base.classes.plugins.simple.SettingsManager;


/**
 *
 * @author blinov_is
 */
public abstract class PluginSettings {
   
   private static SettingsManager Settings;
   public static String DISPLAY_CONF;
   public static final String DISPLAY_CONF_FRAMES_DIR="//kk_plugin_lcddisplay_frames//";
    
    public static LcdDisplayConf MainConfiguration;
    
    public static void InitConfig(String GlobalConfigUID, String MyUID)
    {
        DISPLAY_CONF=GlobalConfigUID+"_"+MyUID + ".json";
        
        Settings=new SettingsManager(DISPLAY_CONF,LcdDisplayConf.class);
        
        System.out.println("[LCDDisplay][CONFIG] Load configuration");
        MainConfiguration=(LcdDisplayConf)Settings.LoadConfig();
        
        if (MainConfiguration==null)
        {
            System.out.println("[LCDDisplay][CONFIG] Error Load configuration, try create default config");
            Settings.SaveConfig(kk_DefaultConfig.MakeDefaultConfig());
            MainConfiguration=(LcdDisplayConf)Settings.LoadConfig();
        }
        if (MainConfiguration==null)
        {
            System.out.println("[LCDDisplay][CONFIG] Load configuration, fatal");
            return;
        }
    
    }

}
