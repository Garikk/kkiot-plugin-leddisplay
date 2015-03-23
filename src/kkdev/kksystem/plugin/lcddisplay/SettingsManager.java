/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay;

import kkdev.kksystem.plugin.lcddisplay.manager.configuration.kk_DefaultConfig;
import kkdev.kksystem.plugin.lcddisplay.manager.configuration.LcdDisplayConf;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileNotFoundException;
import java.io.FileReader;
import kkdev.kksystem.base.constants.SystemConsts;

/**
 *
 * @author blinov_is
 */
public abstract class SettingsManager {
   public static final String DISPLAY_CONF="kk_plugin_lcddisplay.conf";
    
    public static LcdDisplayConf MainConfiguration;
    
    public static void InitConfig()
    {
        LoadConfig();
        
        if (MainConfiguration==null)
        {
            kk_DefaultConfig.MakeDefaultConfig();
            LoadConfig();
        }
        if (MainConfiguration==null)
        {
            System.out.println("[LCDDisplay] Config load error");
            return;
        }
       //

        //
    }
    
    
    private static void LoadConfig()
    {
    try {
            String ConfFilePath = SystemConsts.KK_BASE_CONFPATH + "/" + DISPLAY_CONF;
            FileReader fr;
            try {
                fr = new FileReader(ConfFilePath);
            } catch (FileNotFoundException ex) {
                System.out.println("[LCDDisplayPlugin][SettingsManager]file not found");
                return;
            }

            XStream xstream = new XStream(new DomDriver());
            MainConfiguration = (LcdDisplayConf) xstream.fromXML(fr);

        } catch (StreamException Ex) {
            System.out.println("[LCDDisplayPlugin][SettingsManager] Conf file load error");
            return;
           
        }

    }
    
}
