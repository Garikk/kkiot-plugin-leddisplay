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
import kkdev.kksystem.base.classes.base.PinData;
import kkdev.kksystem.base.classes.base.PinDataFtrCtx;
import kkdev.kksystem.base.classes.base.PinDataTaggedObj;
import kkdev.kksystem.base.classes.base.PinDataTaggedString;
import kkdev.kksystem.base.classes.display.PinDataLed;
import kkdev.kksystem.base.classes.display.pages.framesKeySet;
import kkdev.kksystem.base.classes.notify.PinDataNotifySystemState;
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
    static Map<String, Map<String, String>> CurrentPage;              //Feature => UIContext =>pageName

    public void Init(KKPlugin Conn) {
        setPluginConnector(Conn);
        Utils = Conn.GetUtils();

        PluginSettings.InitConfig(Conn.globalConfID, Conn.pluginInfo.getPluginInfo().PluginUUID);
        //
        for (DisplayHW HD : PluginSettings.MainConfiguration.HWDisplays) {
            for (String UIC : HD.HWDisplay_UIContext) {
                if (!currentFeature.containsKey(UIC)) {
                    currentFeature.put(UIC, PluginSettings.MainConfiguration.DefaultFeature);
                }
            }
        }
        //
        configAndHWInit();
    }

    private void initDisplayView(String[] UIContext, DisplayView DW) {
        for (String CTX : UIContext) {
            if (!Displays.containsKey(CTX)) {
                Displays.put(CTX, new ArrayList<>());
                Utils.UICONTEXT_AddUIContext(CTX);
                Utils.UICONTEXT_UpdateDisplayInUIContext(CTX, DW.Connector.GetDisplayInfo());
            }
            //
            // System.out.println("[LCD] ADD DW "+CTX +" " + DW);
            Displays.get(CTX).add(DW);

        }

    }

    private void configAndHWInit() {
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
                            initDisplayView(DH.HWDisplay_UIContext, new DisplayView(new DisplayHD44780onRPI()));
                        } else {
                            System.out.println("[LCDDisplay][CONFLOADER] Unknown display type in config!! + " + DH.HWBoard);
                        }
                        break;
                    case DisplayDebug:
                        initDisplayView(DH.HWDisplay_UIContext, new DisplayView(new DisplayDebug()));
                        break;
                    case Smarthead_Arduino:
                        if (DH.HWDisplay == HWDisplayTypes.OLED_VIRTUAL_128x64) {
                            initDisplayView(DH.HWDisplay_UIContext, new DisplayView(new DisplayOLEDOnSmarthead(this, DH.HWBoardPins[0])));
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

    public void receivePin(String FeatureID, String UIContext, String PinName, Object PinData) {
        switch (PinName) {
            case PluginConsts.KK_PLUGIN_BASE_LED_COMMAND:
                PinDataLed CMD;
                CMD = (PinDataLed) PinData;
                ProcessCommand(FeatureID, UIContext, CMD);
                break;
            case PluginConsts.KK_PLUGIN_BASE_LED_DATA:
                PinDataLed DAT;
                DAT = (PinDataLed) PinData;
                ProcessData(UIContext, DAT);
                break;
            case PluginConsts.KK_PLUGIN_BASE_PIN_COMMAND:
                ProcessBaseCommand((PinDataFtrCtx)PinData);
                break;
        }
    }

    ///////////////////
    ///////////////////
    private void ProcessCommand(String FeatureID, String UIContext, PinDataLed Command) {

        switch (Command.command) {
            case DISPLAY_KKSYS_PAGE_ACTIVATE:

                setPageToActive(FeatureID, UIContext, Command.pageID);
                break;
            case DISPLAY_KKSYS_GETINFO:
                //  AnswerDisplayInfo();
                break;

        }
    }

    private void ProcessData(String UIContext, PinDataLed Data) {

        switch (Data.ledDataType) {
            case DISPLAY_KKSYS_TEXT_SIMPLE_OUT:
                SendTextToPage(Data.featureID, UIContext, Data.targetPage, Data.directDisplayText);
                break;
            case DISPLAY_KKSYS_TEXT_UPDATE_DIRECT:

                break;
            case DISPLAY_KKSYS_TEXT_UPDATE_FRAME:
                updatePageUIFrames(Data.featureID, UIContext, Data.targetPage, false, Data.displayFrames);
                break;
        }
    }

    private void ProcessBaseCommand(PinDataFtrCtx Command) {
        switch (Command.managementCommand) {
            case ChangeFeature:
                changeFeature(Command.manageFeatureID, Command.manageUIContextID);
                break;
        }
    }

    private DisplayPage GetPage(String FeatureID, String UIContext, String PageName) {
        if (!DPages.containsKey(FeatureID)) {
            DPages.put(FeatureID, new HashMap<>());
        }

        if (!DPages.get(FeatureID).containsKey(UIContext)) {
            DPages.get(FeatureID).put(UIContext, new HashMap<>());
        }

        if (!DPages.get(FeatureID).get(UIContext).containsKey(PageName)) {
            DPages.get(FeatureID).get(UIContext).put(PageName, Utils.DISPLAY_GetUIDisplayPage(PageName));
            DPages.get(FeatureID).get(UIContext).get(PageName).initUIFrames(Utils.UICONTEXT_GetUIContextInfo(UIContext).UIDisplay.textMode_Rows);
        }

        return DPages.get(FeatureID).get(UIContext).get(PageName);

    }

    private List<DisplayView> GetDisplayViewsForPage(DisplayPage Page) {
        List<DisplayView> Ret;
        Ret = new ArrayList();
        for (String UICtx : Page.contexts) {
            if (Displays.containsKey(UICtx)) {
                for (DisplayView DV : Displays.get(UICtx)) {
                    Ret.add(DV);
                }
            }
        }

        return Ret;
    }

    //////////////////
    ///////////////////
    private void SendTextToPage(String FeatureID, String UIContext, String PageID, String[] Text) {
        for (String TL : Text) {
            sendTextToPage(FeatureID, UIContext, PageID, TL);
        }
    }

    private void sendTextToPage(String FeatureID, String UIContext, String PageID, String Text) {
        //
        DisplayPage DP;
        DP = GetPage(FeatureID, UIContext, PageID);
        //
        for (DisplayView DV : GetDisplayViewsForPage(DP)) {
            DV.SendText(Text);
        }
    }

    private void updateTextOnPage(String FeatureID, String UIContext, String PageID, String[] Text, int[] PositionsCol, int[] PositionRow) {
        DisplayPage DP;
        DP = GetPage(FeatureID, UIContext, PageID);
        //
        for (DisplayView DV : GetDisplayViewsForPage(DP)) {
            for (int i = 0; i <= Text.length; i++) {
                DV.UpdateText(Text[i], PositionsCol[i], PositionRow[i]);
            }
        }

    }

    private void updatePageUIFrames(String FeatureID, String UIContext, String PageID, boolean SetUIFrames, framesKeySet UIFrames) {

        DisplayPage DP = GetPage(FeatureID, UIContext, PageID);
        if (UIFrames != null) {
            DP.framesValues = UIFrames;
        }
        //
        if (!currentFeature.get(UIContext).equals(FeatureID)) {
            return;
        }
        //
        for (DisplayView DV : GetDisplayViewsForPage(DP)) {
            //When change page, set new uiframes
            if (SetUIFrames) {
                DV.SetUIFrames(DP.frames, DP.dynamicElements);
            }
            //Update values
            // System.out.println("[LCD][DBG]" + SetUIFrames+ " " + DP.pageName);
            DV.UpdateFrameVariables(DP.framesValues);
        }

    }

    private void setPageToActive(String FeatureID, String UIContext, String PageID) {
        if (!CurrentPage.containsKey(UIContext)) {
            CurrentPage.put(UIContext, new HashMap<>());
        }
        //
        //System.out.println("[LCD] set page active " + FeatureID + " " + UIContext + " " + PageID);
        //
        CurrentPage.get(UIContext).put(FeatureID, PageID);
        //
        if (!currentFeature.get(UIContext).equals(FeatureID)) {
            return;
        }
        //
        updatePageUIFrames(FeatureID, UIContext, PageID, true, null);
    }

    private void SetPageToInactive(String FeatureID, String UIContext, String PageID) {
        if (!FeatureID.equals(currentFeature.get(UIContext))) {
            return;
        }
        //
        if (PageID == null) // CHECK THIS!!!
        {
            return;
        }
        //
        GetDisplayViewsForPage(GetPage(FeatureID, UIContext, PageID)).stream().forEach((DV) -> {
            DV.ClearDisplay();
        });
    }

    private void changeFeature(String FeatureID, String UIContext) {
        if (currentFeature.get(UIContext).equals(FeatureID)) {
            return;
        }
        // Set Current page of feature to Active
        SetPageToInactive(currentFeature.get(UIContext), UIContext, CurrentPage.get(UIContext).get(currentFeature));
        currentFeature.put(UIContext, FeatureID);
        setPageToActive(FeatureID, UIContext, CurrentPage.get(UIContext).get(FeatureID));

        //
    }

    @Override
    public void sendPIN_StringPin(String Tag, String Data) {
        PinDataTaggedString ObjDat;
        ObjDat = new PinDataTaggedString();
        ObjDat.tag = Tag;
        ObjDat.value = Data;
        this.BASE_SendPluginMessage(SystemConsts.KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID, SystemConsts.KK_BASE_UICONTEXT_DEFAULT, PluginConsts.KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA, ObjDat);
    }

    @Override
    public void sendPIN_ObjPin(String Tag, Object Data) {
        PinDataTaggedObj ObjDat;
        ObjDat = new PinDataTaggedObj();
        ObjDat.tag = Tag;
        ObjDat.value = Data;
        this.BASE_SendPluginMessage(SystemConsts.KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID, SystemConsts.KK_BASE_UICONTEXT_DEFAULT, PluginConsts.KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA, ObjDat);
    }
}
