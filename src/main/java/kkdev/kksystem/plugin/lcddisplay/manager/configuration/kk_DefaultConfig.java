/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager.configuration;

import kkdev.kksystem.plugin.lcddisplay.hw.DisplayHW;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.base.constants.SystemConsts.*;


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

        LcdDisplayConf DefConf = new LcdDisplayConf();
        //
        DefConf.DefaultFeature=SystemConsts.KK_BASE_FEATURES_SYSTEM_UID;
        //
        DefConf.HWDisplays=new DisplayHW[3];
        //
        DefConf.HWDisplays[0] = new DisplayHW();
        DefConf.HWDisplays[1] = new DisplayHW();
        DefConf.HWDisplays[2] = new DisplayHW();
        //
        DefConf.HWDisplays[0].HWBoard = DisplayHW.HWHostTypes.DisplayDebug;
        DefConf.HWDisplays[0].HWDisplay = DisplayHW.HWDisplayTypes.HostDebug;
        DefConf.HWDisplays[0].HWDisplay_UIContext=new String[1];
        DefConf.HWDisplays[0].HWDisplay_UIContext[0] = KK_BASE_UICONTEXT_DEFAULT;
        //
        DefConf.HWDisplays[1].HWBoard = DisplayHW.HWHostTypes.Smarthead_Arduino;
        DefConf.HWDisplays[1].HWDisplay =  DisplayHW.HWDisplayTypes.OLED_VIRTUAL_128x64;  
        DefConf.HWDisplays[1].HWDisplay_UIContext=new String[2];
        DefConf.HWDisplays[1].HWDisplay_UIContext[0] = KK_BASE_UICONTEXT_DEFAULT;
        DefConf.HWDisplays[1].HWDisplay_UIContext[1] = KK_BASE_UICONTEXT_GFX1;
        DefConf.HWDisplays[1].HWBoardPins=new int[1];//DisplayID
        DefConf.HWDisplays[1].HWBoardPins[0]=1;
        //
        DefConf.HWDisplays[2].HWBoard =  DisplayHW.HWHostTypes.Smarthead_Arduino;
        DefConf.HWDisplays[2].HWDisplay = DisplayHW.HWDisplayTypes.OLED_VIRTUAL_128x64;  
        DefConf.HWDisplays[2].HWDisplay_UIContext=new String[1];
        DefConf.HWDisplays[2].HWDisplay_UIContext[0] = KK_BASE_UICONTEXT_GFX2;
        DefConf.HWDisplays[2].HWBoardPins=new int[1]; //DisplayID
        DefConf.HWDisplays[2].HWBoardPins[0]=2;
        
       return DefConf;
    }
}