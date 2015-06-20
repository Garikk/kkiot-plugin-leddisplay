/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    
    String[] UIFValues;
    String[] UIFKeys;
    
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
        System.out.println("Start");
        DynamicFramesCounter=0;
        DynamicTimer=new Timer();
        //
        DynamicTimer.scheduleAtFixedRate(DynamicTask,0,1000);
        
        
    
    }
    private void StopDynamicView()
    {
        System.out.println("Stop");
        
        if (DynamicTimer!=null)
            DynamicTimer.cancel();
        
        DynamicTimer=null;
    }
    
    private TimerTask DynamicTask = new TimerTask(){

        @Override
        public void run() {
            System.out.println("Tick " + DynamicFramesCounter);
            
           if (DynamicFramesCounter<UIFrames.length-1)
               DynamicFramesCounter+=2;
           else
               DynamicFramesCounter=0;
           
            System.out.println("Tick " + DynamicFramesCounter);
            //Connector.DisplayTextSetUIFrames(DisplayedFrames,DynamicFramesCounter);
            UpdateFrameVariables(UIFKeys,UIFValues);
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
         UIFKeys=Keys;
         UIFValues=Values;
         
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
        DisplayedFrames=FillPluginFeaturedFields(DisplayedFrames);
        //
        Connector.DisplayTextSetUIFrames(DisplayedFrames,DynamicFramesCounter);
    }

    private String[] FillPluginFeaturedFields(String[] DisplayFrames)
    {
        String CurrTime;
        CurrTime =(new SimpleDateFormat("HH:mm:ss")).format(new Date());
        
         for (int i = 0; i < DisplayedFrames.length; i++) {
                     if (DisplayedFrames[i] != null) {
                         
                        DisplayedFrames[i] = DisplayedFrames[i].replace("[KK_PL_TIME]", CurrTime);
                }
            }
        return DisplayFrames;
    
    }
    public void SetUIFrames(String[] Frames,boolean EnableDynamic) {
        UIFrames = Frames;
       
        if (EnableDynamic & (DynamicView!=EnableDynamic))
            RunDynamicView();
        else if (!EnableDynamic & (DynamicView!=EnableDynamic))
            StopDynamicView();
        
        DynamicView=EnableDynamic;
    }
}
