/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.leddisplay.manager;

import kkdev.kksystem.base.classes.PluginMessage;
import kkdev.kksystem.base.classes.led.DisplayConstants;
import kkdev.kksystem.base.classes.led.DisplayInfo;
import kkdev.kksystem.base.classes.led.PinLedCommand;
import kkdev.kksystem.base.classes.led.PinLedData;
import kkdev.kksystem.base.constants.PluginConsts;

/**
 *
 * @author blinov_is
 */
public class LedDisplayManager {
    
    
    
    
     public void RecivePin(PluginMessage Msg)
    {
        switch (Msg.PinName)
        { case PluginConsts.KK_PLUGIN_BASE_PLUGIN_DEF_PIN_LED_COMMAND:
                PinLedCommand CMD;
                CMD=(PinLedCommand)Msg.PinData;
                ProcessCommand(CMD);
                break;
            case PluginConsts.KK_PLUGIN_BASE_PLUGIN_DEF_PIN_LED_DATA:
                PinLedData DAT;
                DAT=(PinLedData)Msg.PinData;
                ProcessData(DAT);
                break;
        }
    }
    ///////////////////
    ///////////////////
    public void ProcessCommand(PinLedCommand Command)
    {
        switch (Command.Command)
        {
            case DISPLAY_KKSYS_GETINFO:
                break;
        
        }
    }
    public void ProcessData(PinLedData Data)
    {
        
        switch (Data.DataType)
        {
//            case DISPLAY_KKSYS_DISPLAY_STATE:
                
  //              break;
        }
    }
    
}
