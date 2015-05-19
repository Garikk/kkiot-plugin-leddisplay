/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay;

import kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_TYPE;
import kkdev.kksystem.base.classes.plugins.PluginInfo;
import kkdev.kksystem.base.constants.PluginConsts;

/**
 *
 * @author blinov_is
 */
public final class LEDPluginInfo  {
    public static PluginInfo GetPluginInfo()
    {
        PluginInfo Ret=new PluginInfo();
        
        Ret.PluginUUID="7fbac0f7-6939-4380-bcb0-0ef8b1580fbf";
        Ret.PluginName="KKLCDDisplay";
        Ret.PluginDescription="Basic LCDDisplay";
        Ret.PluginType = KK_PLUGIN_TYPE.PLUGIN_OUTPUT;
        Ret.PluginVersion=1;
        Ret.Enabled=true;
        Ret.ReceivePins = GetMyReceivePinInfo();
        Ret.TransmitPins = GetMyTransmitPinInfo();
        return Ret;
    }
    
    
    private static String[] GetMyReceivePinInfo(){
    
        String[] Ret=new String[4];
    
        Ret[0]=PluginConsts.KK_PLUGIN_BASE_PIN_COMMAND;
        Ret[1]=PluginConsts.KK_PLUGIN_BASE_LED_COMMAND;
        Ret[2]=PluginConsts.KK_PLUGIN_BASE_LED_DATA;
        Ret[3]=PluginConsts.KK_PLUGIN_BASE_LED_RAW;
        
        return Ret;
    }
    private static String[] GetMyTransmitPinInfo(){
    
        String[] Ret=new String[2];
    
        Ret[0]=PluginConsts.KK_PLUGIN_BASE_LED_DATA;
        Ret[1]=PluginConsts.KK_PLUGIN_BASE_LED_RAW;
        
        return Ret;
    }
    
}
