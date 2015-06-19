/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import kkdev.kksystem.plugin.lcddisplay.hw.IDisplayConnectorHW;

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
    
    Timer DynamicTimer;
    int DynamicFramesCounter;

    public DisplayView(IDisplayConnectorHW InitConn) {
        Connector = InitConn;
        //
        DisplayID = Connector.GetDisplayInfo().DisplayID;
        //
        Enabled=true; //Change this!
    }

    private void RunDynamicView()
    {
        DynamicTimer=new Timer();
        //
        DynamicTimer.schedule(DynamicTask,1000,1000);
        DynamicTimer.cancel();
    
    }
    private void StopDynamicView()
    {
        DynamicTimer.cancel();
    }
    
    private TimerTask DynamicTask = new TimerTask(){

        @Override
        public void run() {
           if (DynamicFramesCounter<UIFrames.length)
               DynamicFramesCounter--;
           else
               DynamicFramesCounter=0;
               
        }
    };
    
    
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
        Connector.DisplayText(Text);
    }

    public void UpdateText(String Text, int Col, int Row) {
        if (!Enabled) {
            return;
        }
        Connector.DisplayTextUpdate(Text, Col, Row);
    }

    public void UpdateFrameVariables(String[] Keys, String[] Values) {
        if (!Enabled) {
            return;
        }
        DisplayedFrames = UIFrames.clone();

        if (UIFrames == null) {
            Logger.getLogger("lcddisplay").log(Level.WARNING, "[KKCar][PLUGIN][LCDDisplay][DisplayView]Not UIFrames [" + DisplayID + "]");
            return;
        }
         if (UIFrames == null) {
            Logger.getLogger("lcddisplay").log(Level.WARNING, "[KKCar][PLUGIN][LCDDisplay][DisplayView]Not UIFrames [" + DisplayID + "]");
            return;
        }
        //not data
        if (Keys != null) {
            for (int i = 0; i < DisplayedFrames.length; i++) {
                for (int ii = 0; ii < Keys.length; ii++) {
                    if (DisplayedFrames[i] != null) {
                        DisplayedFrames[i] = DisplayedFrames[i].replace("[" + Keys[ii] + "]", Values[ii]);
                    }
                }
            }
        }
        //
        Connector.DisplayTextSetUIFrames(DisplayedFrames);
    }

    public void SetUIFrames(String[] Frames,boolean EnableDynamic) {
        DynamicView=EnableDynamic;
        UIFrames = Frames;
    }
/*
    public void RefreshDisplayUIFrames() {
        if (!Enabled) {
            return;
        }

        if (UIFrames == null | DisplayedFrames == null) {
            return;
        }
        //
          Connector.DisplayTextSetUIFrames(DisplayedFrames);

    }
    */
}
