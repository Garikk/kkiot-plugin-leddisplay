/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager.configuration;

import com.google.gson.Gson;
import kkdev.kksystem.plugin.lcddisplay.manager.configuration.DisplayPage;
import kkdev.kksystem.plugin.lcddisplay.LcdDisplayConf;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.plugin.lcddisplay.manager.configuration.DisplayHW.HWDisplayTypes.MIELT_4bit;
import static kkdev.kksystem.plugin.lcddisplay.manager.configuration.DisplayHW.HWHostTypes.RaspberryPI_B;
import static kkdev.kksystem.plugin.lcddisplay.manager.configuration.SettingsManager.DISPLAY_CONF;

/**
 *
 * @author blinov_is
 *
 * Creating default config
 *
 * RaspberryPI_B and MIELT Display
 *
 */
public abstract class kk_DefaultConfig {

    public static void MakeDefaultConfig() {
       
        try {
            LcdDisplayConf DefConf=GetDefaultconfig();
            
            Gson gson=new Gson();
            
            String Res=gson.toJson(DefConf);
            
            FileWriter fw;
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + "/"+DISPLAY_CONF);
            fw.write(Res);
            fw.flush();
            fw.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(kk_DefaultConfig.class.getName()).log(Level.SEVERE, null, ex);
        }

        

    }

    private static LcdDisplayConf GetDefaultconfig() {
        final int PAGE_MAIN = 0;
        final int PAGE_DETAIL = 1;

        LcdDisplayConf DefConf = new LcdDisplayConf();

        DisplayPage[] DP = new DisplayPage[2];
        //
        DisplayHW DHW = new DisplayHW();
        DHW.HWDisplayName = "MIELT";
        DHW.HWBoard = RaspberryPI_B;
        DHW.HWDisplay = MIELT_4bit;
        //DHW.;
        int PINS[] = new int[6];
        PINS[0] = 15; //RS
        PINS[1] = 16; //Strobe
        PINS[2] = 5;  //Bit1
        PINS[3] = 6;  //Bit2
        PINS[4] = 10; //Bit3
        PINS[5] = 11; //Bit4
        
        DP[PAGE_MAIN]=new DisplayPage();
        DP[PAGE_MAIN].HaveDynamicElements = true;
        DP[PAGE_MAIN].PageName = "MAIN";
        DP[PAGE_MAIN].HWDisplays = new String[1];
        DP[PAGE_MAIN].HWDisplays[0] = DHW.HWDisplayName;

        DP[PAGE_DETAIL]=new DisplayPage();
        DP[PAGE_DETAIL].HaveDynamicElements = false;
        DP[PAGE_DETAIL].PageName = "DETAIL";
        DP[PAGE_DETAIL].HWDisplays = new String[1];
        DP[PAGE_DETAIL].HWDisplays[0] = DHW.HWDisplayName;
        //
        DefConf.ConfName="Default config";
        DefConf.DisplayPages = DP;
        DefConf.HWDisplays=new DisplayHW[1];
        DefConf.HWDisplays[0]=DHW;

        return DefConf;
    }
}
