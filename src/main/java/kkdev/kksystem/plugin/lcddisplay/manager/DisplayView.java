/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager;

import static java.lang.System.out;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import kkdev.kksystem.base.classes.display.pages.UIFramesKeySet;
import static kkdev.kksystem.base.constants.SystemConsts.KK_BASE_UICONTEXT_GFX2;
import kkdev.kksystem.plugin.lcddisplay.hw.IDisplayConnectorHW;
import kkdev.kksystem.base.classes.display.pages.DisplayPage;

/**
 *
 * @author blinov_is
 */
public class DisplayView {

    public IDisplayConnectorHW Connector;
    public String DisplayID;
    public boolean Enabled;
    public boolean ErrorState;
    public boolean DynamicView;

    String[] UIFrames;
    String[] DisplayedFrames;

    UIFramesKeySet StoredUIFrameValues;

    Timer DynamicTimer;
    int DynamicFramesCounter;

    public DisplayView(IDisplayConnectorHW InitConn) {
        Connector = InitConn;
        //
        DisplayID = Connector.GetDisplayInfo().DisplayID;
        //
        Enabled = true; //Change this!
    }

    private void RunDynamicView() {
        DynamicFramesCounter = 0;
        DynamicTimer = new Timer();
        //
        DynamicTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                if (DynamicFramesCounter < UIFrames.length - 1) {
                    DynamicFramesCounter++;
                } else {
                    DynamicFramesCounter = 0;
                }
                UpdateFrameVariables(StoredUIFrameValues);
            }
        }, 0, 1000);

    }

    private void StopDynamicView() {

        if (DynamicTimer != null) {
            DynamicTimer.cancel();
        }

        DynamicFramesCounter = 0;
        DynamicTimer = null;

    }

    public void ClearDisplay() {
        if (!Enabled) {
            return;
        }
        Connector.ClearDisplay();
    }

    public void PowerOn() {
        Enabled = true;
        Connector.SetPower(Enabled);

    }

    public void PowerOff() {
        Enabled = false;
        Connector.SetPower(Enabled);
    }

    public void SendText(String Text) {
        if (!Enabled) {
            return;
        }
        Connector.DisplayText(false,Text);
    }

    public void UpdateText(String Text, int Col, int Row) {
        if (!Enabled) {
            return;
        }
        Connector.DisplayTextUpdate(Text, Col, Row);
    }

    public synchronized void UpdateFrameVariables(UIFramesKeySet UIFramesValues) {
        if (!Enabled) {
            return;
        }

        if (UIFrames == null) {
            Logger.getLogger("lcddisplay").log(Level.WARNING, "[LCDDisplay][DisplayView]Not UIFrames [" + DisplayID + "]");
            return;
        }
        if (UIFrames == null) {
            Logger.getLogger("lcddisplay").log(Level.WARNING, "[LCDDisplay][DisplayView]Not UIFrames [" + DisplayID + "]");
            return;
        }
         DisplayedFrames = UIFrames.clone();

        StoredUIFrameValues = UIFramesValues;

        //not data
        if (StoredUIFrameValues != null) {
            for (int i = 0; i < DisplayedFrames.length; i++) {
                for (String ii : StoredUIFrameValues.FrameValues.keySet()) {
                    if (DisplayedFrames[i] != null) {
                            DisplayedFrames[i] = DisplayedFrames[i].replace("[" + ii + "]", StoredUIFrameValues.FrameValues.get(ii));

                    }
                }

            }
        }
        DisplayedFrames = FillPluginFeaturedFields(DisplayedFrames);
        //
        //TODO TEMPORARY!!!!
        if (DisplayID == KK_BASE_UICONTEXT_GFX2)
            Connector.DisplayTextSetUIFrames(DisplayedFrames, DynamicFramesCounter, 3);
        else
            Connector.DisplayTextSetUIFrames(DisplayedFrames, DynamicFramesCounter, 2);
    }

    private String[] FillPluginFeaturedFields(String[] DisplayFrames) {
        String CurrTime;
        CurrTime = (new SimpleDateFormat("HH:mm:ss")).format(new Date());

        for (int i = 0; i < DisplayedFrames.length; i++) {
            if (DisplayedFrames[i] != null) {

                DisplayedFrames[i] = DisplayedFrames[i].replace("[KK_PL_TIME]", CurrTime);
            }
        }
        return DisplayFrames;

    }

    public void SetUIFrames(String[] Frames, boolean EnableDynamic) {
   
             
        UIFrames = Frames;
        if (EnableDynamic & (DynamicView != EnableDynamic)) {
            RunDynamicView();
        } else if (!EnableDynamic & (DynamicView != EnableDynamic)) {
            StopDynamicView();
        }

        DynamicView = EnableDynamic;
    }
}
