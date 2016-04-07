/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager;

import kkdev.kksystem.plugin.lcddisplay.hw.DisplayHW;
import kkdev.kksystem.plugin.lcddisplay.manager.configuration.DisplayPage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kkdev.kksystem.base.classes.base.PinBaseCommand;
import kkdev.kksystem.base.classes.display.DisplayConstants;
import kkdev.kksystem.base.classes.display.DisplayInfo;
import kkdev.kksystem.base.classes.display.PinLedCommand;
import kkdev.kksystem.base.classes.display.PinLedData;
import kkdev.kksystem.base.classes.display.UIFramesKeySet;
import kkdev.kksystem.base.classes.plugins.simple.managers.PluginManagerLCD;
import kkdev.kksystem.base.constants.PluginConsts;
import kkdev.kksystem.base.constants.SystemConsts;
import kkdev.kksystem.plugin.lcddisplay.KKPlugin;
import kkdev.kksystem.plugin.lcddisplay.hw.debug.DisplayDebug;
import kkdev.kksystem.plugin.lcddisplay.manager.configuration.PluginSettings;
import kkdev.kksystem.plugin.lcddisplay.hw.rpi.HD44780.DisplayHD44780onRPI;
import kkdev.kksystem.plugin.lcddisplay.hw.DisplayHW.HWDisplayTypes;
import kkdev.kksystem.plugin.lcddisplay.hw.DisplayHW.HWHostTypes;
import kkdev.kksystem.plugin.lcddisplay.hw.i2c.oled.DisplayOLEDOnI2C;

/**
 *
 * @author blinov_is
 *
 * in now, create and manage only one page "Main", and only one hw display
 *
 */
public class LcdDisplayManager extends PluginManagerLCD {

    static String DefaultDisplay;
    static Map<String, DisplayView> Displays;                         //UIContext - DView
    static Map<String, Map<String, Map<String, DisplayPage>>> DPages;              //Feature => UIContext => Page=>DisplayPage
    static Map<String, Map<String, String>> CurrentPage;              //Feature => UIContext =>PageName
    static Map<String, Map<String, List<DisplayView>>> DViews;         //Feature => UIContext =>DView

    public void Init(KKPlugin Conn) {
        Connector = Conn;

        PluginSettings.InitConfig(Conn.GlobalConfID, Conn.PluginInfo.GetPluginInfo().PluginUUID);
        //
        for (DisplayHW HD:PluginSettings.MainConfiguration.HWDisplays)
        {
            CurrentFeature.put(HD.HWDisplay_UIContext,PluginSettings.MainConfiguration.DefaultFeature);
        }
        //
        ConfigAndHWInit();
    }

    private void ConfigAndHWInit() {
        DViews = new HashMap<>();
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
                            Displays.put(DH.HWDisplay_UIContext, new DisplayView(new DisplayHD44780onRPI()));
                        } else {
                            System.out.println("[LCDDisplay][CONFLOADER] Unknown display type in config!! + " + DH.HWBoard);
                        }
                        break;
                    case DisplayDebug:
                        Displays.put(DH.HWDisplay_UIContext, new DisplayView(new DisplayDebug()));
                        break;
                    case I2C_Over_Arduino:
                        if (DH.HWDisplay == HWDisplayTypes.OLED_I2C_128x64) {
                            Displays.put(DH.HWDisplay_UIContext, new DisplayView(new DisplayOLEDOnI2C()));
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

        //
        List<DisplayPage> MultiFeatureDisplayPages;
        MultiFeatureDisplayPages = new ArrayList<>();
        for (DisplayPage DP : PluginSettings.MainConfiguration.DisplayPages) {
            if (DP.IsMultifeaturePage) {
                MultiFeatureDisplayPages.add(DP);
            }
        }
        //
        for (DisplayPage DP : PluginSettings.MainConfiguration.DisplayPages) {
            DP.InitUIFrames();
            //
            List<DisplayView> LS = new ArrayList<>();
            for (String DV : DP.HWDisplays) {
                LS.add(Displays.get(DV));
            }

            //
            for (String F : DP.Features) {
                if (!DPages.containsKey(F)) {
                    DPages.put(F, new HashMap<>());
                }
                //
                for (String UICtx : DP.GetUIContexts()) {
                    if (!DPages.get(F).containsKey(UICtx)) {
                        DPages.get(F).put(UICtx, new HashMap<>());
                    }

                    DPages.get(F).get(UICtx).put(DP.PageName, DP.GetInstance());
                    //
                    if (!DViews.containsKey(F)) {
                        DViews.put(F, new HashMap<>());
                    }
                    //
                    //
                    DViews.get(F).put(DP.PageName, LS);
                    //
                    if (DP.IsDefaultPage) {
                        if (!CurrentPage.containsKey(F)) {
                            CurrentPage.put(F, new HashMap<>());
                        }
                        CurrentPage.get(F).put(UICtx, DP.PageName);

                    }
                }
            }
        }
        //
    
    //
    for (String FTR : PluginSettings.MainConfiguration.Features) {
            for (DisplayPage MDP : MultiFeatureDisplayPages) {
            for (String UICtx : MDP.GetUIContexts()) {
                if (!DViews.containsKey(FTR)) {
                    DViews.put(FTR, new HashMap<>());
                }
                //
                if (!DViews.get(FTR).containsKey(MDP.PageName)) {
                    List<DisplayView> MF_LS = new ArrayList<>();
                    for (String DV : MDP.HWDisplays) {
                        MF_LS.add(Displays.get(DV));
                    }
                    DViews.get(FTR).put(MDP.PageName, MF_LS);
                }
                //
                if (!DPages.containsKey(FTR)) {
                    DPages.put(FTR, new HashMap<>());
                    DPages.get(FTR).put(UICtx, new HashMap<>());
                }
               //
                if (!DPages.get(FTR).get(UICtx).containsKey(MDP.PageName)) {
                    DPages.get(FTR).get(UICtx).put(MDP.PageName, MDP.GetInstance());
                }
                }
        }
    }
}

public void ReceivePin( String FeatureID, String PinName, Object PinData) {

        switch (PinName) {
            case PluginConsts.KK_PLUGIN_BASE_LED_COMMAND:
                PinLedCommand CMD;
                CMD = (PinLedCommand) PinData;
                ProcessCommand(CMD.ChangeUIContextID, FeatureID, CMD);
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
    private void ProcessCommand(String UIContext, String FeatureID, PinLedCommand Command) {

        switch (Command.Command) {
            case DISPLAY_KKSYS_PAGE_ACTIVATE:
                SetPageToActive(UIContext, FeatureID, Command.PageID);
                break;
            case DISPLAY_KKSYS_GETINFO:
                AnswerDisplayInfo();
                break;

        }
    }

    private void ProcessData(String UIContext, PinLedData Data) {

        switch (Data.LedDataType) {
            case DISPLAY_KKSYS_TEXT_SIMPLE_OUT:
                SendTextToPage(UIContext, Data.FeatureID, Data.TargetPage, Data.Direct_DisplayText);
                break;
            case DISPLAY_KKSYS_TEXT_UPDATE_DIRECT:

                break;
            case DISPLAY_KKSYS_TEXT_UPDATE_FRAME:
                UpdatePageUIFrames(UIContext, Data.FeatureID, Data.TargetPage, false, Data.UIFrames);
                break;
        }
    }

    private void ProcessBaseCommand(PinBaseCommand Command) {
        switch (Command.BaseCommand) {
            case CHANGE_FEATURE:
                ChangeFeature(Command.ChangeUIContextID, Command.ChangeFeatureID);
                break;
            case PLUGIN:
                break;
        }
    }

    //////////////////
    ///////////////////
    private void AnswerDisplayInfo() {
        PinLedData Ret;
        DisplayInfo[] DI = new DisplayInfo[Displays.values().size()];
        //
        int cnt = 0;
        //
        for (DisplayView DV : Displays.values()) {
            DI[cnt] = DV.Connector.GetDisplayInfo();
            cnt++;
        }
        //     
        Ret = new PinLedData();
        Ret.DisplayState = DI;
        Ret.LedDataType = DisplayConstants.KK_DISPLAY_DATA.DISPLAY_KKSYS_DISPLAY_STATE;
        //
        DISPLAY_SendPluginMessageData(CurrentFeature.get(SystemConsts.KK_BASE_UICONTEXT_DEFAULT), Ret);
        //
    }

    //////////////////
    ///////////////////
    private void SendTextToPage(String UIContext, String FeatureID, String PageID, String[] Text) {
        for (String TL : Text) {
            SendTextToPage(UIContext, FeatureID, PageID, TL);
        }
    }

    private void SendTextToPage(String UIContext, String FeatureID, String PageID, String Text) {
        //
        for (DisplayView DV : DViews.get(FeatureID).get(PageID)) {
            DV.SendText(Text);
        }
    }

    private void UpdateTextOnPage(String UIContext, String FeatureID, String PageID, String[] Text, int[] PositionsCol, int[] PositionRow) {

        for (DisplayView DV : DViews.get(FeatureID).get(PageID)) {
            for (int i = 0; i <= Text.length; i++) {
                DV.UpdateText(Text[i], PositionsCol[i], PositionRow[i]);
            }
        }

    }

    private void UpdatePageUIFrames(String UIContext, String FeatureID, String PageID, boolean SetUIFrames, UIFramesKeySet UIFrames) {

        DisplayPage DP = DPages.get(FeatureID).get(UIContext).get(PageID);

        if (UIFrames != null) {
            DP.UIFramesValues = UIFrames;
        }
        //
        if (!CurrentFeature.equals(FeatureID)) {
            return;
        }
        //
        for (DisplayView DV : DViews.get(FeatureID).get(PageID)) {
            //When change page, set new uiframes
            if (SetUIFrames) {
                DV.SetUIFrames(DP.UIFrames, DP.HaveDynamicElements);
            }
            //Update values
            DV.UpdateFrameVariables(DP.UIFramesValues);
        }

    }

    private void SetPageToActive(String UIContext, String FeatureID, String PageID) {
        // DisplayPage DP = DPages.get(PageID);
        //
        CurrentPage.get(UIContext).put(FeatureID, PageID);
        //
        if (!CurrentFeature.equals(FeatureID)) {
            return;
        }
        //
        UpdatePageUIFrames(UIContext, FeatureID, PageID, true, null);
    }

    private void SetPageToInactive(String UIContext, String FeatureID, String PageID) {
        if (!FeatureID.equals(CurrentFeature)) {
            return;
        }

        DViews.get(FeatureID).get(PageID).stream().forEach((DV) -> {
            DV.ClearDisplay();
        });
    }

    private void ChangeFeature(String UIContext, String FeatureID) {
        if (CurrentFeature.equals(FeatureID)) {
            return;
        }

        // Set Current page of feature to Active
        SetPageToInactive(CurrentFeature.get(UIContext),UIContext, CurrentPage.get(UIContext).get(CurrentFeature));
        CurrentFeature.put(UIContext, FeatureID);
        SetPageToActive(FeatureID,UIContext,CurrentPage.get(UIContext).get(FeatureID));

        //
    }
}
