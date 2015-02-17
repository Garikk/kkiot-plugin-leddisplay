/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.leddisplay.manager;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import kkdev.kksystem.base.classes.PluginMessage;
import kkdev.kksystem.base.classes.led.DisplayConstants;
import kkdev.kksystem.base.classes.led.DisplayInfo;
import kkdev.kksystem.base.classes.led.PinLedCommand;
import kkdev.kksystem.base.classes.led.PinLedData;
import kkdev.kksystem.base.constants.PluginConsts;
import kkdev.kksystem.plugin.leddisplay.KKDisplayView;
import kkdev.kksystem.plugin.leddisplay.KKPlugin;

/**
 *
 * @author blinov_is
 */
public class LedDisplayManager {
    
    KKPlugin Connector;
    String DefaultDisplay;
    Map<String,KKDisplayView> Displays;
    Map<String, List<String>> Pages;
        
    public LedDisplayManager(KKPlugin Conn){
        Connector=Conn;
        ConfigAndHWInit();
    }
    
    private void ConfigAndHWInit()
    {
        //TODO: create config loader, detect and test hardware
        // for debug
        Displays=kk_defaultConfig.GetDefConfig();
        DefaultDisplay="MAIN";
        //
        AddDisplayPage("MAIN","MAIN");
    }
    
    private void AddDisplayPage(String PageID, String DisplayID)
    {
        if (!Pages.containsKey(PageID))
            Pages.put(PageID, new ArrayList<>());
        
        if (!Pages.get(PageID).contains(DisplayID))
            Pages.get(PageID).add(DisplayID);
    }
    private void SendTextLineToPage(String PageID, String Text)
    {
        if (!Pages.containsKey(PageID))
            PageID="MAIN";
        //
        KKDisplayView DV=Displays.get(Pages.get(PageID));
        //
        if (DV.Enabled & !DV.ErrorState)
            DV.SendText(Text);
    }
    
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
                AnswerDisplayInfo();
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
    //////////////////
    ///////////////////
    private void AnswerDisplayInfo()
    {
        PinLedData Ret;
        DisplayInfo[] DI = new DisplayInfo[Displays.values().size()];
        //
        int cnt=0;
        //
        for (KKDisplayView DV:Displays.values())
        {
            DI[cnt]=DV.Connector.GetDisplayInfo();
            cnt++;
        }
       //     
        Ret=new PinLedData();
        Ret.DisplayState=DI;
       //
       Connector.SendPluginMessageData(DisplayConstants.KK_DISPLAY_DATA.DISPLAY_KKSYS_DISPLAY_STATE, Ret);
       //
    }
}
