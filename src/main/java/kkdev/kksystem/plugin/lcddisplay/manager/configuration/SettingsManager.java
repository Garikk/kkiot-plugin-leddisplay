/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager.configuration;

import com.google.gson.Gson;
import java.io.BufferedReader;
import kkdev.kksystem.plugin.lcddisplay.manager.configuration.kk_DefaultConfig;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import kkdev.kksystem.base.constants.SystemConsts;


/**
 *
 * @author blinov_is
 */
public abstract class SettingsManager {
   public static final String DISPLAY_CONF="kk_plugin_lcddisplay.json";
   public static final String DISPLAY_CONF_FRAMES_DIR="//kk_plugin_lcddisplay_frames//";
    
    public static LcdDisplayConf MainConfiguration;
    
    public static void InitConfig()
    {
        System.out.println("[LCDDisplay][CONFIG] Load configuration");
        LoadConfig();
        
        if (MainConfiguration==null)
        {
            System.out.println("[LCDDisplay][CONFIG] Error Load configuration, try create default config");
            kk_DefaultConfig.MakeDefaultConfig();
            LoadConfig();
        }
        if (MainConfiguration==null)
        {
            System.out.println("[LCDDisplay][CONFIG] Load configuration, fatal");
            return;
        }
       //

        //
    }
    
    
    private static void LoadConfig() 
    {
       try {
           Gson gson=new Gson();
           BufferedReader br = new BufferedReader(  
                 new FileReader(SystemConsts.KK_BASE_FORPLUGINS_CONFPATH + "/"+DISPLAY_CONF));  

           MainConfiguration = (LcdDisplayConf)gson.fromJson(br, LcdDisplayConf.class);
           
           
       } catch (FileNotFoundException ex) {
           Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        
    }

        
    
   
}
