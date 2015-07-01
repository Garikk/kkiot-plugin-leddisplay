/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager.configuration;

import kkdev.kksystem.plugin.lcddisplay.hw.DisplayHW;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_ODB_DIAG_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_MENUMAKER_UID;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
import static kkdev.kksystem.plugin.lcddisplay.hw.DisplayHW.HWDisplayTypes.HD44780_4bit;
import static kkdev.kksystem.plugin.lcddisplay.hw.DisplayHW.HWHostTypes.RaspberryPI_B;

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

    public static LcdDisplayConf MakeDefaultConfig() {
        final int PAGE_DDISPLAY_MAIN = 0;
        final int PAGE_DDISPLAY_DETAIL = 1;
        final int PAGE_DDISPLAY_WAIT = 2;
        final int PAGE_DDISPLAY_ERROR = 3;
        final int PAGE_SYSMENU_P1 = 4;
        final int PAGE_SYSMENU_VER = 5;
        final int PAGE_SYSMENU_SETT = 6;
        final int PAGE_DDISPLAY_CE_LIST = 7;

        LcdDisplayConf DefConf = new LcdDisplayConf();

        DisplayPage[] DP = new DisplayPage[8];
        //
        DisplayHW DHW = new DisplayHW();

        //DHW.HWBoard = RaspberryPI_B;
        //DHW.HWDisplay = HD44780_4bit;  
        //DHW.HWDisplayName = "RPIAdapter";
        DHW.HWDisplayName = "DEBUG";
        DHW.HWBoard = DisplayHW.HWHostTypes.DisplayDebug;
        DHW.HWDisplay = DisplayHW.HWDisplayTypes.HostDebug;
        //DHW.;
        int PINS[] = new int[6];
        PINS[0] = 15; //RS
        PINS[1] = 16; //Strobe
        PINS[2] = 5;  //Bit1
        PINS[3] = 6;  //Bit2
        PINS[4] = 10; //Bit3
        PINS[5] = 11; //Bit4

        DP[PAGE_DDISPLAY_MAIN] = new DisplayPage();
        DP[PAGE_DDISPLAY_MAIN].Features = new String[1];
        DP[PAGE_DDISPLAY_MAIN].Features[0] = KK_BASE_FEATURES_ODB_DIAG_UID;
        DP[PAGE_DDISPLAY_MAIN].HaveDynamicElements = true;
        DP[PAGE_DDISPLAY_MAIN].PageName = "MAIN";
        DP[PAGE_DDISPLAY_MAIN].HWDisplays = new String[1];
        DP[PAGE_DDISPLAY_MAIN].HWDisplays[0] = DHW.HWDisplayName;
        DP[PAGE_DDISPLAY_MAIN].UIFrameFiles = new String[8];
        DP[PAGE_DDISPLAY_MAIN].UIFrameFiles[0] = "kk_lcddisplay_uiframe_main_1.frame";
        DP[PAGE_DDISPLAY_MAIN].UIFrameFiles[1] = "kk_lcddisplay_uiframe_main_2.frame";
        DP[PAGE_DDISPLAY_MAIN].UIFrameFiles[2] = "kk_lcddisplay_uiframe_main_3.frame";
        DP[PAGE_DDISPLAY_MAIN].UIFrameFiles[3] = "kk_lcddisplay_uiframe_main_4.frame";
        DP[PAGE_DDISPLAY_MAIN].UIFrameFiles[4] = "kk_lcddisplay_uiframe_main_1.frame";
        DP[PAGE_DDISPLAY_MAIN].UIFrameFiles[5] = "kk_lcddisplay_uiframe_main_5.frame";
        DP[PAGE_DDISPLAY_MAIN].UIFrameFiles[6] = "kk_lcddisplay_uiframe_main_3.frame";
        DP[PAGE_DDISPLAY_MAIN].UIFrameFiles[7] = "kk_lcddisplay_uiframe_main_4.frame";
        //DP[PAGE_DDISPLAY_MAIN].UIFrameFiles[7] = "kk_lcddisplay_uiframe_main_6.frame";
        DP[PAGE_DDISPLAY_MAIN].IsDefaultPage = false;

        DP[PAGE_DDISPLAY_DETAIL] = new DisplayPage();
        DP[PAGE_DDISPLAY_DETAIL].HaveDynamicElements = false;
        DP[PAGE_DDISPLAY_DETAIL].PageName = "DETAIL";
        DP[PAGE_DDISPLAY_DETAIL].Features = new String[1];
        DP[PAGE_DDISPLAY_DETAIL].Features[0] = KK_BASE_FEATURES_ODB_DIAG_UID;
        DP[PAGE_DDISPLAY_DETAIL].HWDisplays = new String[1];
        DP[PAGE_DDISPLAY_DETAIL].HWDisplays[0] = DHW.HWDisplayName;
        DP[PAGE_DDISPLAY_DETAIL].UIFrameFiles = new String[1];
        DP[PAGE_DDISPLAY_DETAIL].UIFrameFiles[0] = "kk_lcddisplay_uiframe_detail_1.frame";
        DP[PAGE_DDISPLAY_DETAIL].IsDefaultPage = false;
        //
        DP[PAGE_DDISPLAY_WAIT] = new DisplayPage();
        DP[PAGE_DDISPLAY_WAIT].HaveDynamicElements = false;
        DP[PAGE_DDISPLAY_WAIT].Features = new String[1];
        DP[PAGE_DDISPLAY_WAIT].Features[0] = KK_BASE_FEATURES_ODB_DIAG_UID;
        DP[PAGE_DDISPLAY_WAIT].PageName = "WAIT";
        DP[PAGE_DDISPLAY_WAIT].HWDisplays = new String[1];
        DP[PAGE_DDISPLAY_WAIT].HWDisplays[0] = DHW.HWDisplayName;
        DP[PAGE_DDISPLAY_WAIT].UIFrameFiles = new String[1];
        DP[PAGE_DDISPLAY_WAIT].UIFrameFiles[0] = "kk_lcddisplay_uiframe_wait_1.frame";
        DP[PAGE_DDISPLAY_WAIT].IsDefaultPage = true;
        //
        DP[PAGE_DDISPLAY_ERROR] = new DisplayPage();
        DP[PAGE_DDISPLAY_ERROR].HaveDynamicElements = false;
        DP[PAGE_DDISPLAY_ERROR].Features = new String[1];
        DP[PAGE_DDISPLAY_ERROR].Features[0] = KK_BASE_FEATURES_ODB_DIAG_UID;
        DP[PAGE_DDISPLAY_ERROR].PageName = "ERROR";
        DP[PAGE_DDISPLAY_ERROR].HWDisplays = new String[1];
        DP[PAGE_DDISPLAY_ERROR].HWDisplays[0] = DHW.HWDisplayName;
        DP[PAGE_DDISPLAY_ERROR].UIFrameFiles = new String[1];
        DP[PAGE_DDISPLAY_ERROR].UIFrameFiles[0] = "kk_lcddisplay_uiframe_error_1.frame";
        DP[PAGE_DDISPLAY_ERROR].IsDefaultPage = false;
        //
        DP[PAGE_SYSMENU_P1] = new DisplayPage();
        DP[PAGE_SYSMENU_P1].HaveDynamicElements = false;
        DP[PAGE_SYSMENU_P1].Features = new String[2];
        DP[PAGE_SYSMENU_P1].Features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        DP[PAGE_SYSMENU_P1].Features[1] = KK_BASE_FEATURES_SYSTEM_MENUMAKER_UID;
        DP[PAGE_SYSMENU_P1].PageName = "SYSMENU_1";
        DP[PAGE_SYSMENU_P1].HWDisplays = new String[1];
        DP[PAGE_SYSMENU_P1].HWDisplays[0] = DHW.HWDisplayName;
        DP[PAGE_SYSMENU_P1].UIFrameFiles = new String[1];
        DP[PAGE_SYSMENU_P1].UIFrameFiles[0] = "kk_sysmenu_p1_uiframe_1.frame";
        DP[PAGE_SYSMENU_P1].IsDefaultPage = true;
        //
        DP[PAGE_SYSMENU_VER] = new DisplayPage();
        DP[PAGE_SYSMENU_VER].HaveDynamicElements = false;
        DP[PAGE_SYSMENU_VER].Features = new String[1];
        DP[PAGE_SYSMENU_VER].Features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        DP[PAGE_SYSMENU_VER].PageName = "SYSMENU_VI";
        DP[PAGE_SYSMENU_VER].HWDisplays = new String[1];
        DP[PAGE_SYSMENU_VER].HWDisplays[0] = DHW.HWDisplayName;
        DP[PAGE_SYSMENU_VER].UIFrameFiles = new String[1];
        DP[PAGE_SYSMENU_VER].UIFrameFiles[0] = "kk_sysmenu_vi_uiframe_1.frame";
        DP[PAGE_SYSMENU_VER].IsDefaultPage = false;
        //
        DP[PAGE_SYSMENU_SETT] = new DisplayPage();
        DP[PAGE_SYSMENU_SETT].HaveDynamicElements = false;
        DP[PAGE_SYSMENU_SETT].Features = new String[1];
        DP[PAGE_SYSMENU_SETT].Features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        DP[PAGE_SYSMENU_SETT].PageName = "SYSMENU_SETT";
        DP[PAGE_SYSMENU_SETT].HWDisplays = new String[1];
        DP[PAGE_SYSMENU_SETT].HWDisplays[0] = DHW.HWDisplayName;
        DP[PAGE_SYSMENU_SETT].UIFrameFiles = new String[1];
        DP[PAGE_SYSMENU_SETT].UIFrameFiles[0] = "kk_sysmenu_sett_uiframe_1.frame";
        DP[PAGE_SYSMENU_SETT].IsDefaultPage = false;
         //
        DP[PAGE_DDISPLAY_CE_LIST] = new DisplayPage();
        DP[PAGE_DDISPLAY_CE_LIST].HaveDynamicElements = false;
        DP[PAGE_DDISPLAY_CE_LIST].Features = new String[1];
        DP[PAGE_DDISPLAY_CE_LIST].Features[0] = KK_BASE_FEATURES_ODB_DIAG_UID;
        DP[PAGE_DDISPLAY_CE_LIST].PageName = "CE_READER";
        DP[PAGE_DDISPLAY_CE_LIST].HWDisplays = new String[1];
        DP[PAGE_DDISPLAY_CE_LIST].HWDisplays[0] = DHW.HWDisplayName;
        DP[PAGE_DDISPLAY_CE_LIST].UIFrameFiles = new String[1];
        DP[PAGE_DDISPLAY_CE_LIST].UIFrameFiles[0] = "kk_ddisplay_cereader_uiframe_1.frame";
        DP[PAGE_DDISPLAY_CE_LIST].IsDefaultPage = false;
        //
        DefConf.ConfName = "Default config";
        DefConf.DisplayPages = DP;
        DefConf.HWDisplays = new DisplayHW[1];
        DefConf.HWDisplays[0] = DHW;
        DefConf.DefaultFeature = KK_BASE_FEATURES_SYSTEM_UID;
        
        CreateDefaultFrames();
        
        return DefConf;
    }

    private static void CreateDefaultFrames() {
        File dir = new java.io.File(SystemConsts.KK_BASE_CONFPATH + PluginSettings.DISPLAY_CONF_FRAMES_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            return;
        }

        FileWriter fw;
        BufferedWriter out;
        try {
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + PluginSettings.DISPLAY_CONF_FRAMES_DIR + "kk_lcddisplay_uiframe_main_1.frame");
            out = new BufferedWriter(fw);
            out.write("SPD [SPD] TMP [TMP] "); //16
            out.newLine();
            out.write("TIME: [KK_PL_TIME] |"); //16
            out.flush();
            out.close();
            fw.close();

            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + PluginSettings.DISPLAY_CONF_FRAMES_DIR + "kk_lcddisplay_uiframe_main_2.frame");
            out = new BufferedWriter(fw);
            out.write("SPD [SPD] TMP [TMP] "); //16
            out.newLine();
            out.write("TIME: [KK_PL_TIME] /"); //16
            out.flush();
            out.close();
            fw.close();

            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + PluginSettings.DISPLAY_CONF_FRAMES_DIR + "kk_lcddisplay_uiframe_main_3.frame");
            out = new BufferedWriter(fw);
            out.write("SPD [SPD] TMP [TMP] "); //16
            out.newLine();
            out.write("TIME: [KK_PL_TIME] -"); //16
            out.flush();
            out.close();
            fw.close();

            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + PluginSettings.DISPLAY_CONF_FRAMES_DIR + "kk_lcddisplay_uiframe_main_4.frame");
            out = new BufferedWriter(fw);
            out.write("SPD [SPD] TMP [TMP] "); //16
            out.newLine();
            out.write("TIME: [KK_PL_TIME] \\"); //16
            out.flush();
            out.close();
            fw.close();

            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + PluginSettings.DISPLAY_CONF_FRAMES_DIR + "kk_lcddisplay_uiframe_main_5.frame");
            out = new BufferedWriter(fw);
            out.write("SPD [SPD] TMP [TMP] "); //16
            out.newLine();
            out.write("TIME: [KK_PL_TIME] /"); //16
            out.flush();
            out.close();
            fw.close();

          
            ///
            ///
            ///
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + PluginSettings.DISPLAY_CONF_FRAMES_DIR + "kk_lcddisplay_uiframe_detail_1.frame");
            out = new BufferedWriter(fw);
            out.write("T: [TMP] V:[VOLTAGE]"); //16
            out.newLine();
            out.write("S: [SPD] R: [RPM]"); //16
            out.flush();
            out.close();
            fw.close();
            ///
            ///
            ///
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + PluginSettings.DISPLAY_CONF_FRAMES_DIR + "kk_sysmenu_p1_uiframe_1.frame");
            out = new BufferedWriter(fw);
            out.write("[SEL_0][SYSMENU_0][SEL_0]"); //16
            out.newLine();
            out.write("[SEL_1][SYSMENU_1][SEL_1]"); //16
            out.flush();
            out.close();
            fw.close();
            ///
            ///
            ///
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + PluginSettings.DISPLAY_CONF_FRAMES_DIR + "kk_ddisplay_cereader_uiframe_1.frame");
            out = new BufferedWriter(fw);
            out.write("[SEL_0][SYSMENU_0][SEL_0]"); //16
            out.newLine();
            out.write("[SEL_1][SYSMENU_1][SEL_1]"); //16
            out.flush();
            out.close();
            fw.close();
            ///
            ///
            ///
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + PluginSettings.DISPLAY_CONF_FRAMES_DIR + "kk_sysmenu_vi_uiframe_1.frame");
            out = new BufferedWriter(fw);
            out.write(" KKSystem V.1.0 "); //16
            out.newLine();
            out.write("......BETA......"); //16
            out.flush();
            out.close();
            fw.close();
            ///
            ///
            ///
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + PluginSettings.DISPLAY_CONF_FRAMES_DIR + "kk_sysmenu_sett_uiframe_1.frame");
            out = new BufferedWriter(fw);
            out.write("Settings page"); //16
            out.newLine();
            out.write("......BETA......"); //16
            out.flush();
            out.close();
            fw.close();
            //
            //
            //
            
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + PluginSettings.DISPLAY_CONF_FRAMES_DIR + "kk_lcddisplay_uiframe_error_1.frame");
            out = new BufferedWriter(fw);
            out.write("Error"); //16
            out.newLine();
            out.write("......ERROR......"); //16
            out.flush();
            out.close();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(kk_DefaultConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
           //
            //
            //
            try{
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + PluginSettings.DISPLAY_CONF_FRAMES_DIR + "kk_lcddisplay_uiframe_wait_1.frame");
            out = new BufferedWriter(fw);
            out.write("Wait"); //16
            out.newLine();
            out.write("......WAIT......"); //16
            out.flush();
            out.close();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(kk_DefaultConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
