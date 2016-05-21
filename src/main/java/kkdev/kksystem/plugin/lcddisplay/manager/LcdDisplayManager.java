/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager;

import kkdev.kksystem.plugin.lcddisplay.hw.DisplayHW;
import kkdev.kksystem.base.classes.display.pages.DisplayPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kkdev.kksystem.base.classes.base.PinBaseCommand;
import kkdev.kksystem.base.classes.base.PinBaseData;
import kkdev.kksystem.base.classes.base.PinBaseDataTaggedObj;
import kkdev.kksystem.base.classes.display.PinLedCommand;
import kkdev.kksystem.base.classes.display.PinLedData;
import kkdev.kksystem.base.classes.display.pages.UIFramesKeySet;
import kkdev.kksystem.base.classes.plugins.simple.managers.PluginManagerLCD;
import kkdev.kksystem.base.constants.PluginConsts;
import kkdev.kksystem.base.constants.SystemConsts;
import kkdev.kksystem.base.interfaces.IKKControllerUtils;
import kkdev.kksystem.plugin.lcddisplay.KKPlugin;
import kkdev.kksystem.plugin.lcddisplay.hw.debug.DisplayDebug;
import kkdev.kksystem.plugin.lcddisplay.manager.configuration.PluginSettings;
import kkdev.kksystem.plugin.lcddisplay.hw.rpi.HD44780.DisplayHD44780onRPI;
import kkdev.kksystem.plugin.lcddisplay.hw.DisplayHW.HWDisplayTypes;
import kkdev.kksystem.plugin.lcddisplay.hw.smarthead.DisplayOLEDOnSmarthead;

/**
 *
 * @author blinov_is
 *
 * in now, create and manage only one page "Main", and only one hw display
 *
 */
public class LcdDisplayManager extends PluginManagerLCD implements IObjPinProcessing {

    static IKKControllerUtils Utils;
    static String DefaultDisplay;
    static Map<String, List<DisplayView>> Displays;                         //UIContext - DView
    static Map<String, Map<String, Map<String, DisplayPage>>> DPages;              //Feature => UIContext => Page=>DisplayPage
    static Map<String, Map<String, String>> CurrentPage;              //Feature => UIContext =>PageName

    public void Init(KKPlugin Conn) {
        Connector = Conn;
        Utils=Conn.GetUtils();

        PluginSettings.InitConfig(Conn.GlobalConfID, Conn.PluginInfo.GetPluginInfo().PluginUUID);
        //
        for (DisplayHW HD:PluginSettings.MainConfiguration.HWDisplays)
        {
            for (String UIC:HD.HWDisplay_UIContext)
            {
                if (!CurrentFeature.containsKey(UIC))
                {
                    System.out.println("[LCD] Reg CTX " + UIC + " " + PluginSettings.MainConfiguration.DefaultFeature);
                    CurrentFeature.put(UIC,PluginSettings.MainConfiguration.DefaultFeature);
                }
            }
        }
        //
        ConfigAndHWInit();
    }

    private void InitDisplayView(String[] UIContext, DisplayView DW)
    {
        for (String CTX:UIContext)
        {
            if (!Displays.containsKey(CTX))
                Displays.put(CTX, new ArrayList<>());
            //
            Displays.get(CTX).add(DW);
            
        }            
                
    }
    
    private void ConfigAndHWInit() {
        //DViews = new HashMap<>();
        Displays = new HashMap<>();
        CurrentPage = new HashMap<>();
        DPages = new HashMap<>();

        //Add HWDisplays and init
        for (DisplayHW DH : PluginSettings.MainConfiguration.HWDisplays) {
            if (null != DH.HWBoard) //Init on RPi Host
            {
                switch (DH.HWBoard) {
                    case RaspberryPI_B:
                        if (DH.HWDisplay == HWDisplayTypes.HD44780_4bit) {
                           InitDisplayView(DH.HWDisplay_UIContext, new DisplayView(new DisplayHD44780onRPI()));
                        } else {
                            System.out.println("[LCDDisplay][CONFLOADER] Unknown display type in config!! + " + DH.HWBoard);
                        }
                        break;
                    case DisplayDebug:
                        InitDisplayView(DH.HWDisplay_UIContext, new DisplayView(new DisplayDebug()));
                        break;
                    case Smarthead_Arduino:
                        if (DH.HWDisplay == HWDisplayTypes.OLED_VIRTUAL_128x64) {
                            InitDisplayView(DH.HWDisplay_UIContext, new DisplayView(new DisplayOLEDOnSmarthead(this,DH.HWBoardPins[0])));
                        } else {
                            System.out.println("[LCDDisplay][CONFLOADER] Unknown display type in config!! + " + DH.HWBoard);
                        }
                        break;
                    default:
                        System.out.println("[LCDDisplay][CONFLOADER] Unknown HW board in config!! + " + DH.HWBoard);
                        break;
                }
            }
        }
}

public void ReceivePin( String FeatureID, String PinName, Object PinData) {

        switch (PinName) {
            case PluginConsts.KK_PLUGIN_BASE_LED_COMMAND:
                PinLedCommand CMD;
                CMD = (PinLedCommand) PinData;
                 ProcessCommand( FeatureID,CMD.ChangeUIContextID, CMD);
                break;
            case PluginConsts.KK_PLUGIN_BASE_LED_DATA:
                PinLedData DAT;
                DAT = (PinLedData) PinData;
                ProcessData(DAT.UIContextID, DAT);
                break;
            case PluginConsts.KK_PLUGIN_BASE_PIN_COMMAND:
                PinBaseCommand BaseCMD;
                BaseCMD = (PinBaseCommand) PinData;

                ProcessBaseCommand(BaseCMD);
        }
    }

    ///////////////////
    ///////////////////
    private void ProcessCommand(String FeatureID,String UIContext,  PinLedCommand Command) {


        switch (Command.Command) {
            case DISPLAY_KKSYS_PAGE_ACTIVATE:

                SetPageToActive(FeatureID,UIContext, Command.PageID);
                break;
            case DISPLAY_KKSYS_GETINFO:
              //  AnswerDisplayInfo();
                break;

        }
    }

    private void ProcessData(String UIContext, PinLedData Data) {

        switch (Data.LedDataType) {
            case DISPLAY_KKSYS_TEXT_SIMPLE_OUT:
                SendTextToPage( Data.FeatureID,UIContext, Data.TargetPage, Data.Direct_DisplayText);
                break;
            case DISPLAY_KKSYS_TEXT_UPDATE_DIRECT:

                break;
            case DISPLAY_KKSYS_TEXT_UPDATE_FRAME:
                UpdatePageUIFrames( Data.FeatureID,UIContext, Data.TargetPage, false, Data.UIFrames);
                break;
        }
    }

    private void ProcessBaseCommand(PinBaseCommand Command) {
        switch (Command.BaseCommand) {
            case CHANGE_FEATURE:
                ChangeFeature( Command.ChangeFeatureID,Command.ChangeUIContextID);
                break;
            case PLUGIN:
                break;
        }
    }


    private DisplayPage GetPage(String FeatureID,String UIContext,String PageName)
    {
        if (!DPages.containsKey(FeatureID))
            DPages.put(FeatureID, new HashMap<>());
        
        if (!DPages.get(FeatureID).containsKey(UIContext))
            DPages.get(FeatureID).put(UIContext, new HashMap<>());
        
        if (!DPages.get(FeatureID).get(UIContext).containsKey(PageName))
            DPages.get(FeatureID).get(UIContext).put(PageName, Utils.DISPLAY_GetUIDisplayPage(PageName));
        
        return DPages.get(FeatureID).get(UIContext).get(PageName);
        
    }
    
    
    private List<DisplayView> GetDisplayViewsForPage(DisplayPage Page)
    {
        List<DisplayView> Ret;
        Ret=new ArrayList();
        
        for (String UICtx:Page.UIContexts)
        {
            if (!Displays.containsKey(UICtx))
            {
                for (DisplayView DW:Displays.get(UICtx))
                    Ret.add(DW);
            }
        }
        
        return Ret;
    }
    //////////////////
    ///////////////////
    private void SendTextToPage(String FeatureID,String UIContext, String PageID, String[] Text) {
        for (String TL : Text) {
            SendTextToPage( FeatureID,UIContext, PageID, TL);
        }
    }

    private void SendTextToPage(String FeatureID,String UIContext, String PageID, String Text) {
        //
        DisplayPage DP;
        DP=GetPage(FeatureID,UIContext,PageID);
        //
        for (DisplayView DV : GetDisplayViewsForPage(DP)) {
            DV.SendText(Text);
        }
    }

    private void UpdateTextOnPage(String FeatureID, String UIContext, String PageID, String[] Text, int[] PositionsCol, int[] PositionRow) {
        DisplayPage DP;
        DP = GetPage(FeatureID, UIContext, PageID);
        //
        for (DisplayView DV : GetDisplayViewsForPage(DP)) {
            for (int i = 0; i <= Text.length; i++) {
                DV.UpdateText(Text[i], PositionsCol[i], PositionRow[i]);
            }
        }

    }

    private void UpdatePageUIFrames(String FeatureID, String UIContext, String PageID, boolean SetUIFrames, UIFramesKeySet UIFrames) {

        DisplayPage DP = GetPage(FeatureID, UIContext, PageID);

        if (UIFrames != null) {
            DP.UIFramesValues = UIFrames;
        }
        //
        if (!CurrentFeature.get(UIContext).equals(FeatureID)) {
            return;
        }
        //
        for (DisplayView DV : GetDisplayViewsForPage(DP)) {
            //When change page, set new uiframes
            if (SetUIFrames) {
                DV.SetUIFrames(DP.UIFrames, DP.DynamicElements);
            }
            //Update values
            DV.UpdateFrameVariables(DP.UIFramesValues);
        }

    }

    private void SetPageToActive(String FeatureID,String UIContext, String PageID) {
        if (!CurrentPage.containsKey(UIContext))
            CurrentPage.put(UIContext, new HashMap<>());
        //
        System.out.println("[LCD] set page active " + UIContext+ " " + FeatureID + " " + PageID);
        //
        CurrentPage.get(UIContext).put(FeatureID, PageID);
        //
        if (!CurrentFeature.get(UIContext).equals(FeatureID)) {
            return;
        }
        //
        UpdatePageUIFrames( FeatureID,UIContext, PageID, true, null);
    }

    private void SetPageToInactive(String FeatureID,String UIContext, String PageID) {
        if (!FeatureID.equals(CurrentFeature.get(UIContext))) {
            return;
        }
        //
        if (PageID==null) // CHECK THIS!!!
            return; 
        //
        GetDisplayViewsForPage(GetPage(FeatureID, UIContext, PageID)).stream().forEach((DV) -> {
            DV.ClearDisplay();
        });
    }

    private void ChangeFeature( String FeatureID,String UIContext) {
        System.out.println("[LCD] Change FTR" + FeatureID +" "+UIContext);
        if (CurrentFeature.get(UIContext).equals(FeatureID)) {
            return;
        }
        // Set Current page of feature to Active
        SetPageToInactive(CurrentFeature.get(UIContext),UIContext, CurrentPage.get(UIContext).get(CurrentFeature));
        CurrentFeature.put(UIContext, FeatureID);
        SetPageToActive(FeatureID,UIContext,CurrentPage.get(UIContext).get(FeatureID));

        //
    }

    @Override
    public void SendPIN_ObjPin(String Tag, Object Data) {
        PinBaseDataTaggedObj ObjDat;
        ObjDat = new PinBaseDataTaggedObj();
        ObjDat.DataType = PinBaseData.BASE_DATA_TYPE.TAGGED_OBJ;
        ObjDat.Tag = Tag;
        ObjDat.Value = Data;
        this.BASE_SendPluginMessage(SystemConsts.KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID, PluginConsts.KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA, ObjDat);
    }

}
