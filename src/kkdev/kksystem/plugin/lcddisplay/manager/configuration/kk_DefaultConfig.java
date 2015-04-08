/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager.configuration;

import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.File;
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
        //
        CreateDefaultFrames();
        //

    }

    private static LcdDisplayConf GetDefaultconfig() {
        final int PAGE_MAIN = 0;
        final int PAGE_DETAIL = 1;
        final int PAGE_WAIT = 2;
        
        LcdDisplayConf DefConf = new LcdDisplayConf();

        DisplayPage[] DP = new DisplayPage[3];
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
        DP[PAGE_MAIN].UIFrameFiles=new String[7];
        DP[PAGE_MAIN].UIFrameFiles[0]="kk_lcddisplay_uiframe_main_1.frame";
        DP[PAGE_MAIN].UIFrameFiles[1]="kk_lcddisplay_uiframe_main_2.frame";
        DP[PAGE_MAIN].UIFrameFiles[2]="kk_lcddisplay_uiframe_main_3.frame";
        DP[PAGE_MAIN].UIFrameFiles[3]="kk_lcddisplay_uiframe_main_4.frame";
        DP[PAGE_MAIN].UIFrameFiles[4]="kk_lcddisplay_uiframe_main_5.frame";
        DP[PAGE_MAIN].UIFrameFiles[5]="kk_lcddisplay_uiframe_main_3.frame";
        DP[PAGE_MAIN].UIFrameFiles[6]="kk_lcddisplay_uiframe_main_6.frame";
        

        DP[PAGE_DETAIL]=new DisplayPage();
        DP[PAGE_DETAIL].HaveDynamicElements = false;
        DP[PAGE_DETAIL].PageName = "DETAIL";
        DP[PAGE_DETAIL].HWDisplays = new String[1];
        DP[PAGE_DETAIL].HWDisplays[0] = DHW.HWDisplayName;
        DP[PAGE_DETAIL].UIFrameFiles=new String[1];
        DP[PAGE_DETAIL].UIFrameFiles[0]="kk_lcddisplay_uiframe_detail_1.frame";
        //
        DP[PAGE_WAIT]=new DisplayPage();
        DP[PAGE_WAIT].HaveDynamicElements = false;
        DP[PAGE_WAIT].PageName = "WAIT";
        DP[PAGE_WAIT].HWDisplays = new String[1];
        DP[PAGE_WAIT].HWDisplays[0] = DHW.HWDisplayName;
        DP[PAGE_WAIT].UIFrameFiles=new String[1];
        DP[PAGE_WAIT].UIFrameFiles[0]="kk_lcddisplay_uiframe_wait_1.frame";
        //
        DefConf.ConfName="Default config";
        DefConf.DisplayPages = DP;
        DefConf.HWDisplays=new DisplayHW[1];
        DefConf.HWDisplays[0]=DHW;

        return DefConf;
    }
    
    private static void CreateDefaultFrames()
    {
        File dir=new java.io.File(SystemConsts.KK_BASE_CONFPATH + SettingsManager.DISPLAY_CONF_FRAMES_DIR);
        if (!dir.exists())
            dir.mkdir();
        else
            return;
        
        FileWriter fw;
        BufferedWriter out;
        try {
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + SettingsManager.DISPLAY_CONF_FRAMES_DIR+"kk_lcddisplay_uiframe_main_1.frame");
            out = new BufferedWriter(fw);
            out.write("[R1]SPD [SPD] TMP [TMP] "); //16
            out.newLine();
            out.write("[R2]TIME:  [TIME] |"); //16
            out.flush();
            out.close();
            fw.close();
            
           fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + SettingsManager.DISPLAY_CONF_FRAMES_DIR+"kk_lcddisplay_uiframe_main_2.frame");
            out = new BufferedWriter(fw);
            out.write("[R2]TIME: [TIME] /"); //16
            out.flush();
            out.close();
            fw.close();
            
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + SettingsManager.DISPLAY_CONF_FRAMES_DIR+"kk_lcddisplay_uiframe_main_3.frame");
            out = new BufferedWriter(fw);
            out.write("[R2]TIME: [TIME] -"); //16
            out.flush();
            out.close();
            fw.close();
            
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + SettingsManager.DISPLAY_CONF_FRAMES_DIR+"kk_lcddisplay_uiframe_main_4.frame");
            out = new BufferedWriter(fw);
            out.write("[R2]TIME: [TIME]  |"); //16
            out.flush();
            out.close();
            fw.close();
            
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + SettingsManager.DISPLAY_CONF_FRAMES_DIR+"kk_lcddisplay_uiframe_main_5.frame");
            out = new BufferedWriter(fw);
            out.write("[R2]TIME: [TIME] /"); //16
            out.flush();
            out.close();
            fw.close();
            
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + SettingsManager.DISPLAY_CONF_FRAMES_DIR+"kk_lcddisplay_uiframe_main_6.frame");
            out = new BufferedWriter(fw);
            out.write("[R2]TIME: [TIME] \\"); //16
            out.flush();
            out.close();
            fw.close();
            ///
            ///
            ///
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + SettingsManager.DISPLAY_CONF_FRAMES_DIR+"kk_lcddisplay_uiframe_detail_1.frame");
            out = new BufferedWriter(fw);
            out.write("[R1]T: [TMP] V:[VOLTAGE]"); //16
            out.write("[R2]S: [SPD] ERR: [ERR]"); //16
            out.flush();
            out.close();
            fw.close();
             ///
            ///
            ///
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + SettingsManager.DISPLAY_CONF_FRAMES_DIR+"kk_lcddisplay_uiframe_wait_1.frame");
            out = new BufferedWriter(fw);
            out.write("[R1] Wait data..."); //16
            out.flush();
            out.close();
            fw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(kk_DefaultConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
