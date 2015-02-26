/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.leddisplay.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kkdev.kksystem.base.classes.PluginMessage;
import kkdev.kksystem.base.classes.display.DisplayConstants;
import kkdev.kksystem.base.classes.display.DisplayInfo;
import kkdev.kksystem.base.classes.display.PinLedCommand;
import kkdev.kksystem.base.classes.display.PinLedData;
import kkdev.kksystem.base.constants.PluginConsts;
import kkdev.kksystem.plugin.leddisplay.KKDisplayView;
import kkdev.kksystem.plugin.leddisplay.KKPlugin;
import kkdev.kksystem.plugin.leddisplay.hw.MIELT_MT16S2H.DisplayMIELTMT16S2H_4bb;

/**
 *
 * @author blinov_is
 * 
 * in now, create and manage only one page "Main", and only one hw display
 * 
 */
public abstract class LedDisplayManager {
    
    static KKPlugin Connector;
    static String DefaultDisplay;
    static Map<String,KKDisplayView> Displays;
    static Map<String, List<String>> Pages;
        
    public static void Init(KKPlugin Conn){
        Connector=Conn;
        ConfigAndHWInit();
    }
    
    private static void ConfigAndHWInit()
    {
        //TODO: create config loader, detect and test hardware
        // for debug
        
        
        DefaultDisplay="MAIN";
        //
       
        AddDisplayToPage("MAIN",InitAndConnectHWDisplay("MAIN")); //only one hardcoded page by now
    }
    
    private static void AddDisplayToPage(String PageID, KKDisplayView Display)
    {
        if (!Pages.containsKey(PageID))
            Pages.put(PageID, new ArrayList<>());
        
        if (!Pages.get(PageID).contains(Display.DisplayID))
            Pages.get(PageID).add(Display.DisplayID);
    }
    
    //TODO: Made connect displays from configuration scripts, now is hardcoded
    private static KKDisplayView InitAndConnectHWDisplay(String DisplayID)
    {
        //
     KKDisplayView Ret=new KKDisplayView(new DisplayMIELTMT16S2H_4bb());
     Ret.Enabled=true;
     Ret.PowerOn();
     //
     if (Displays==null)
     {
         Displays=new HashMap<>();
         Displays.put(DisplayID, Ret);
     }
     else
     {
         if (!Displays.containsKey(DisplayID))
             Displays.put(DisplayID, Ret);
         else
             System.out.println("Wrong dipsplay configuration!");
     }
     return Ret;
    }
    private static void SendTextLineToPage(String PageID, String Text)
    {
        if (!Pages.containsKey(PageID))
            PageID="MAIN";
        //
        List<String> DisplayToView;
        DisplayToView=Pages.get(PageID);
        
        KKDisplayView DV;
        for (String D:DisplayToView)
        {
            DV=Displays.get(D);
            if (DV!=null)
                if (DV.Enabled & !DV.ErrorState)
                    DV.SendText(Text);
        }
    }
     private static void SendTextLineToPageArr(String PageID, String[] Text)
    {
        for (String TL:Text)
            SendTextLineToPage(PageID,TL);
    }
    public static void RecivePin(PluginMessage Msg)
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
    public static void ProcessCommand(PinLedCommand Command)
    {
        switch (Command.Command)
        {
            case DISPLAY_KKSYS_PAGE_INIT:
                break;
            case DISPLAY_KKSYS_PAGE_ACTIVATE:
                break;
            case DISPLAY_KKSYS_GETINFO:
                AnswerDisplayInfo();
                break;
        
        }
    }
    public static void ProcessData(PinLedData Data)
    {
        
        switch (Data.DataType)
        {
            case DISPLAY_KKSYS_TEXT:
                SendTextLineToPageArr(Data.TargetPage,Data.DisplayText);
                break;
        }
    }
    //////////////////
    ///////////////////
    private static void AnswerDisplayInfo()
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
