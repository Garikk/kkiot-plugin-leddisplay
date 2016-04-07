/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager.configuration;

import kkdev.kksystem.plugin.lcddisplay.hw.DisplayHW;
import kkdev.kksystem.base.classes.display.UIFrameData;
import kkdev.kksystem.base.classes.display.UIFramePack;
import static kkdev.kksystem.base.constants.SystemConsts.*;
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
        final int PAGE_SYSMENU = 4;
        final int PAGE_DDISPLAY_CE_LIST = 5;

        UIFramePack[] FPack = GetFramePack();

        LcdDisplayConf DefConf = new LcdDisplayConf();

        DisplayPage[] DP = new DisplayPage[6];
        //
        DisplayHW DHW1 = new DisplayHW();
        DisplayHW DHW2 = new DisplayHW();
        DisplayHW DHW3 = new DisplayHW();
        DisplayHW DHW4 = new DisplayHW();
       
        DHW1.HWDisplay_UIContext = KK_BASE_UICONTEXT_DEFAULT_DEBUG;
        DHW1.HWDisplay_UIContext_MirrorOf = KK_BASE_UICONTEXT_DEFAULT;
        DHW1.HWBoard = DisplayHW.HWHostTypes.DisplayDebug;
        DHW1.HWDisplay = DisplayHW.HWDisplayTypes.HostDebug;
        //
        DHW2.HWBoard = DisplayHW.HWHostTypes.DisplayDebug;
        DHW2.HWDisplay = DisplayHW.HWDisplayTypes.HostDebug;
        //DHW2.HWBoard = DisplayHW.HWHostTypes.RaspberryPI_B;
        //DHW2.HWDisplay = HD44780_4bit;  
        DHW2.HWDisplay_UIContext = KK_BASE_UICONTEXT_DEFAULT;
        //
        DHW3.HWBoard = DisplayHW.HWHostTypes.I2C_Over_Arduino;
        DHW3.HWDisplay =  DisplayHW.HWDisplayTypes.OLED_I2C_128x64;  
        DHW3.HWDisplay_UIContext = KK_BASE_UICONTEXT_GFX1;
        //
        DHW4.HWBoard =  DisplayHW.HWHostTypes.I2C_Over_Arduino;
        DHW4.HWDisplay = DisplayHW.HWDisplayTypes.OLED_I2C_128x64;  
        DHW4.HWDisplay_UIContext = KK_BASE_UICONTEXT_GFX2;


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
        DP[PAGE_DDISPLAY_MAIN].HWDisplays[0] = DHW1.HWDisplay_UIContext;
        DP[PAGE_DDISPLAY_MAIN].IsDefaultPage = false;
        DP[PAGE_DDISPLAY_MAIN].IsMultifeaturePage = false;
        DP[PAGE_DDISPLAY_MAIN].UIFramesPack = FPack[0];

        DP[PAGE_DDISPLAY_DETAIL] = new DisplayPage();
        DP[PAGE_DDISPLAY_DETAIL].HaveDynamicElements = false;
        DP[PAGE_DDISPLAY_DETAIL].PageName = "DETAIL";
        DP[PAGE_DDISPLAY_DETAIL].Features = new String[1];
        DP[PAGE_DDISPLAY_DETAIL].Features[0] = KK_BASE_FEATURES_ODB_DIAG_UID;
        DP[PAGE_DDISPLAY_DETAIL].HWDisplays = new String[1];
        DP[PAGE_DDISPLAY_DETAIL].HWDisplays[0] = DHW1.HWDisplay_UIContext;
        DP[PAGE_DDISPLAY_DETAIL].IsDefaultPage = false;
        DP[PAGE_DDISPLAY_DETAIL].IsMultifeaturePage = false;
        DP[PAGE_DDISPLAY_DETAIL].UIFramesPack = FPack[1];
        //
        DP[PAGE_DDISPLAY_WAIT] = new DisplayPage();
        DP[PAGE_DDISPLAY_WAIT].HaveDynamicElements = false;
        DP[PAGE_DDISPLAY_WAIT].Features = new String[1];
        DP[PAGE_DDISPLAY_WAIT].Features[0] = KK_BASE_FEATURES_ODB_DIAG_UID;
        DP[PAGE_DDISPLAY_WAIT].PageName = "WAIT";
        DP[PAGE_DDISPLAY_WAIT].HWDisplays = new String[1];
        DP[PAGE_DDISPLAY_WAIT].HWDisplays[0] = DHW1.HWDisplay_UIContext;
        DP[PAGE_DDISPLAY_WAIT].IsDefaultPage = true;
        DP[PAGE_DDISPLAY_WAIT].IsMultifeaturePage = false;
        DP[PAGE_DDISPLAY_WAIT].UIFramesPack = FPack[3];
        //
        DP[PAGE_DDISPLAY_ERROR] = new DisplayPage();
        DP[PAGE_DDISPLAY_ERROR].HaveDynamicElements = false;
        DP[PAGE_DDISPLAY_ERROR].Features = new String[1];
        DP[PAGE_DDISPLAY_ERROR].Features[0] = KK_BASE_FEATURES_ODB_DIAG_UID;
        DP[PAGE_DDISPLAY_ERROR].PageName = "ERROR";
        DP[PAGE_DDISPLAY_ERROR].HWDisplays = new String[1];
        DP[PAGE_DDISPLAY_ERROR].HWDisplays[0] = DHW1.HWDisplay_UIContext;
        DP[PAGE_DDISPLAY_ERROR].IsDefaultPage = false;
        DP[PAGE_DDISPLAY_ERROR].IsMultifeaturePage = false;
        DP[PAGE_DDISPLAY_ERROR].UIFramesPack = FPack[4];
        //
        DP[PAGE_SYSMENU] = new DisplayPage();
        DP[PAGE_SYSMENU].HaveDynamicElements = false;
        DP[PAGE_SYSMENU].Features = new String[1];
        DP[PAGE_SYSMENU].Features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        DP[PAGE_SYSMENU].PageName = "SYSMENU_1";
        DP[PAGE_SYSMENU].HWDisplays = new String[1];
        DP[PAGE_SYSMENU].HWDisplays[0] = DHW1.HWDisplay_UIContext;
        DP[PAGE_SYSMENU].IsDefaultPage = false;
        DP[PAGE_SYSMENU].IsMultifeaturePage = true;
        DP[PAGE_SYSMENU].UIFramesPack = FPack[2];
        //
        DP[PAGE_DDISPLAY_CE_LIST] = new DisplayPage();
        DP[PAGE_DDISPLAY_CE_LIST].HaveDynamicElements = false;
        DP[PAGE_DDISPLAY_CE_LIST].Features = new String[1];
        DP[PAGE_DDISPLAY_CE_LIST].Features[0] = KK_BASE_FEATURES_ODB_DIAG_UID;
        DP[PAGE_DDISPLAY_CE_LIST].PageName = "CE_READER";
        DP[PAGE_DDISPLAY_CE_LIST].HWDisplays = new String[1];
        DP[PAGE_DDISPLAY_CE_LIST].HWDisplays[0] = DHW1.HWDisplay_UIContext;
        DP[PAGE_DDISPLAY_CE_LIST].IsDefaultPage = false;
        DP[PAGE_DDISPLAY_CE_LIST].UIFramesPack = FPack[2];
        //
        DefConf.ConfName = "Default config";
        DefConf.DisplayPages = DP;
        DefConf.HWDisplays = new DisplayHW[4];
        DefConf.HWDisplays[0] = DHW1;
        DefConf.HWDisplays[1] = DHW2;
        DefConf.HWDisplays[2] = DHW3;
        DefConf.HWDisplays[3] = DHW4;
        DefConf.DefaultFeature = KK_BASE_FEATURES_SYSTEM_UID;
        DefConf.Features = new String[3];
        DefConf.Features[0] = KK_BASE_FEATURES_SYSTEM_UID;
        DefConf.Features[1] = KK_BASE_FEATURES_ODB_DIAG_UID;
        DefConf.Features[2] = KK_BASE_FEATURES_BLUETOOTH_UID;
        

        return DefConf;
    }

    private static UIFramePack[] GetFramePack() {
        UIFramePack[] Ret = new UIFramePack[5];
        Ret[0] = new UIFramePack();
        Ret[0].Name = "Default Diag Display 2x8 MAIN PAGE";
        Ret[0].PackID = "";
        Ret[0].Data = new UIFrameData[4];
        Ret[0].Data[0] = new UIFrameData();
        Ret[0].Data[1] = new UIFrameData();
        Ret[0].Data[2] = new UIFrameData();
        Ret[0].Data[3] = new UIFrameData();
        Ret[0].Data[0].FrameData = "SPD [SPD] TMP [TMP]\r\nTIME: [KK_PL_TIME] |";
        Ret[0].Data[1].FrameData = "SPD [SPD] TMP [TMP]\r\nTIME: [KK_PL_TIME] /";
        Ret[0].Data[2].FrameData = "SPD [SPD] TMP [TMP]\r\nTIME: [KK_PL_TIME] -";
        Ret[0].Data[3].FrameData = "SPD [SPD] TMP [TMP]\r\nTIME: [KK_PL_TIME] \\";
        //
        Ret[1] = new UIFramePack();
        Ret[1].Name = "Default Diag Display 2x8 DETAILS PAGE";
        Ret[1].PackID = "";
        Ret[1].Data = new UIFrameData[1];
        Ret[1].Data[0] = new UIFrameData();
        Ret[1].Data[0].FrameData = "T: [TMP] V:[VOLTAGE]\r\nS: [SPD] R: [RPM]";
        //
        Ret[2] = new UIFramePack();
        Ret[2].Name = "Default Diag Display 2x8 SYSTEM MENU";
        Ret[2].PackID = "";
        Ret[2].Data = new UIFrameData[1];
        Ret[2].Data[0] = new UIFrameData();
        Ret[2].Data[0].FrameData = "[SEL_0][SYSMENU_0][SEL_0]\r\n[SEL_1][SYSMENU_1][SEL_1]";
        //
        Ret[3] = new UIFramePack();
        Ret[3].Name = "Default Diag Display 2x8 WAIT";
        Ret[3].PackID = "";
        Ret[3].Data = new UIFrameData[1];
        Ret[3].Data[0] = new UIFrameData();
        Ret[3].Data[0].FrameData = "......WAIT......\r\n......WAIT......";
        //
        Ret[4] = new UIFramePack();
        Ret[4].Name = "Default Diag Display 2x8 CEREADER";
        Ret[4].PackID = "";
        Ret[4].Data = new UIFrameData[1];
        Ret[4].Data[0] = new UIFrameData();
        Ret[4].Data[0].FrameData = "Err: [ODB_ADAPTER_STATE]\r\n[ODB_ADAPTER_ERROR]";
        return Ret;

    }

}