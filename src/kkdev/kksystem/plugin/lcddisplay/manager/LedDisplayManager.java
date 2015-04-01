/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager;

import com.sun.media.jfxmedia.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kkdev.kksystem.base.classes.PluginMessage;
import kkdev.kksystem.base.classes.display.DisplayConstants;
import kkdev.kksystem.base.classes.display.DisplayInfo;
import kkdev.kksystem.base.classes.display.PinLedCommand;
import kkdev.kksystem.base.classes.display.PinLedData;
import kkdev.kksystem.base.constants.PluginConsts;
import kkdev.kksystem.plugin.lcddisplay.IDisplayConnectorHW;
import kkdev.kksystem.plugin.lcddisplay.KKDisplayView;
import kkdev.kksystem.plugin.lcddisplay.KKPlugin;
import kkdev.kksystem.plugin.lcddisplay.manager.configuration.SettingsManager;
import kkdev.kksystem.plugin.lcddisplay.hw.rpi.MIELT_MT16S2H.DisplayMIELTMT16S2H_4bb;
import kkdev.kksystem.plugin.lcddisplay.manager.configuration.DisplayHW;
import kkdev.kksystem.plugin.lcddisplay.manager.configuration.DisplayHW.HWDisplayTypes;
import kkdev.kksystem.plugin.lcddisplay.manager.configuration.DisplayHW.HWHostTypes;
import kkdev.kksystem.plugin.lcddisplay.manager.configuration.DisplayPage;

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
        SettingsManager.InitConfig();
        //
        ConfigAndHWInit();
    }
    
    private static void ConfigAndHWInit()
    {
        Pages=new HashMap<>();
        Displays=new HashMap<>();
        
        DefaultDisplay="MAIN";
        //Add HWDisplays and init
        for (DisplayHW DH:SettingsManager.MainConfiguration.HWDisplays)
        {
            if (DH.HWBoard==HWHostTypes.RaspberryPI_B)
            {
                if (DH.HWDisplay==HWDisplayTypes.MIELT_4bit)
                    Displays.put(DH.HWDisplayName, new KKDisplayView(new DisplayMIELTMT16S2H_4bb()));
                else
                    System.out.println("[LCDDisplay][CONFLOADER] Unknown display type in config!! + " + DH.HWBoard);
            }
            else
            {
                System.out.println("[LCDDisplay][CONFLOADER] Unknown HW board in config!! + " + DH.HWBoard);
            }
        
        }
        //Add Pages
        for (DisplayPage DP:SettingsManager.MainConfiguration.DisplayPages)
        {
            List<String> LS=new ArrayList<>();
            LS.addAll(Arrays.asList(DP.HWDisplays));
            
            Pages.put(DP.PageName, LS);
        }
    }

    
    private static void SendTextLineToPage(String PageID, String Text)
    {
        //Redirect unknown pages to main
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
     private static void UpdateTextOnPage(String PageID,String[] Text, int[] PositionsCol, int[] PositionRow)
     {
          //Redirect unknown pages to main
        if (!Pages.containsKey(PageID))
            PageID="MAIN";
        //
        List<String> DisplayToView;
        DisplayToView=Pages.get(PageID);
        
        for (int i=0;i<=Text.length;i++)
        {   
            KKDisplayView DV;
            for (String D:DisplayToView)
            {
                DV=Displays.get(D);
                if (DV!=null)
                    if (DV.Enabled & !DV.ErrorState)
                        DV.UpdateText(Text[i], PositionsCol[i], PositionRow[i]);
            } 
        }
     
     }
    public static void ReceivePin(String PinName, Object PinData)
    {
        
        switch (PinName)
        { case PluginConsts.KK_PLUGIN_BASE_LED_COMMAND:
                PinLedCommand CMD;
                CMD=(PinLedCommand)PinData;
                ProcessCommand(CMD);
                break;
            case PluginConsts.KK_PLUGIN_BASE_LED_DATA:
                PinLedData DAT;
                DAT=(PinLedData)PinData;
                ProcessData(DAT);
                break;
        }
    }
    ///////////////////
    ///////////////////
    private  static void ProcessCommand(PinLedCommand Command)
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
    private  static void ProcessData(PinLedData Data)
    {
        
        switch (Data.DataType)
        {
            case DISPLAY_KKSYS_TEXT_SIMPLE_OUT:
                SendTextLineToPageArr(Data.TargetPage,Data.Direct_DisplayText);
                break;
            case DISPLAY_KKSYS_TEXT_UPDATE_DIRECT:
                
                break;
             case DISPLAY_KKSYS_TEXT_UPDATE_FRAME:
                
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
