/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kkdev.kksystem.base.classes.base.PinBaseCommand;
import kkdev.kksystem.base.classes.display.DisplayConstants;
import kkdev.kksystem.base.classes.display.DisplayInfo;
import kkdev.kksystem.base.classes.display.PinLedCommand;
import kkdev.kksystem.base.classes.display.PinLedData;
import kkdev.kksystem.base.constants.PluginConsts;
import kkdev.kksystem.plugin.lcddisplay.KKDisplayView;
import kkdev.kksystem.plugin.lcddisplay.KKPlugin;
import kkdev.kksystem.plugin.lcddisplay.hw.debug.DisplayDebug;
import kkdev.kksystem.plugin.lcddisplay.manager.configuration.SettingsManager;
import kkdev.kksystem.plugin.lcddisplay.hw.rpi.HD44780.DisplayHD44780;
import kkdev.kksystem.plugin.lcddisplay.manager.DisplayHW.HWDisplayTypes;
import kkdev.kksystem.plugin.lcddisplay.manager.DisplayHW.HWHostTypes;

/**
 *
 * @author blinov_is
 * 
 * in now, create and manage only one page "Main", and only one hw display
 * 
 */
public abstract class LedDisplayManager {
    
    static String CurrentFeature;
    static KKPlugin Connector;
    static String DefaultDisplay;
    static Map<String,KKDisplayView> Displays;
    static Map<String,Map<String, List<String>>> Pages;
    static Map<String,String> CurrentPage;
        
    public static void Init(KKPlugin Conn){
        Connector=Conn;
        
        SettingsManager.InitConfig();
        //
        CurrentFeature=SettingsManager.MainConfiguration.DefaultFeature;
        //
        ConfigAndHWInit();
    }
    
    private static void ConfigAndHWInit()
    {
        Pages=new HashMap<>();
        Displays=new HashMap<>();

        
        //Add HWDisplays and init
        for (DisplayHW DH:SettingsManager.MainConfiguration.HWDisplays)
        {
            //Init on RPi Host
            if (DH.HWBoard==HWHostTypes.RaspberryPI_B)
            {
                if (DH.HWDisplay==HWDisplayTypes.HD44780_4bit)
                    Displays.put(DH.HWDisplayName, new KKDisplayView(new DisplayHD44780()));
                else
                    System.out.println("[LCDDisplay][CONFLOADER] Unknown display type in config!! + " + DH.HWBoard);
            }
            //Debug host
            else if (DH.HWBoard==HWHostTypes.DisplayDebug)
            {
                 Displays.put(DH.HWDisplayName, new KKDisplayView(new DisplayDebug()));
            }
            //Config error
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
            //
            for (String F:DP.Features)
            {
                if (!Pages.containsKey(F))
                    Pages.put(F, new HashMap<>());
                
                Pages.get(F).put(DP.PageName, LS);
            }
            //
        }
    }

    
    private static void SendTextLineToPage(String FeatureID,String PageID, String Text)
    {
        //Redirect unknown pages to main
        if (!Pages.get(FeatureID).containsKey(PageID))
            PageID="MAIN";
        //
        List<String> DisplayToView;
        DisplayToView=Pages.get(FeatureID).get(PageID);
        
        KKDisplayView DV;
        for (String D:DisplayToView)
        {
            DV=Displays.get(D);
            if (DV!=null)
                if (DV.Enabled & !DV.ErrorState)
                    DV.SendText(Text);
        }
    }
     private static void SendTextLineToPageArr(String FeatureID,String PageID, String[] Text)
    {
        for (String TL:Text)
            SendTextLineToPage(FeatureID,PageID,TL);
    }
     private static void UpdateTextOnPage(String FeatureID, String PageID,String[] Text, int[] PositionsCol, int[] PositionRow)
     {
          //Redirect unknown pages to main
        if (!Pages.get(FeatureID).containsKey(PageID))
            PageID="MAIN";
        //
        List<String> DisplayToView;
        DisplayToView=Pages.get(FeatureID).get(PageID);
        
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
            case PluginConsts.KK_PLUGIN_BASE_PIN_COMMAND:
                PinBaseCommand BaseCMD;
                BaseCMD=(PinBaseCommand)PinData;
                ProcessBaseCommand(BaseCMD);
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
                SendTextLineToPageArr(Data.FeatureUID, Data.TargetPage,Data.Direct_DisplayText);
                break;
            case DISPLAY_KKSYS_TEXT_UPDATE_DIRECT:
                
                break;
             case DISPLAY_KKSYS_TEXT_UPDATE_FRAME:
                
                break;
        }
    }
    private static void ProcessBaseCommand(PinBaseCommand Command)
    {
        switch (Command.BaseCommand)
        {
           case CHANGE_FEATURE:
               CurrentFeature=Command.FeatureUID;
                break;
           case PLUGIN:
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
