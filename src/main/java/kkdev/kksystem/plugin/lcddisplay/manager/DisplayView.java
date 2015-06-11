/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager;

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
   public boolean Active;
   public boolean ErrorState;
   String[] UIFrames;
   
   public DisplayView(IDisplayConnectorHW InitConn)
   {
       Connector=InitConn;
       //
       DisplayID=Connector.GetDisplayInfo().DisplayID;
       ((Thread)Connector).start();
   }
   
   public void SetDisplayState(boolean State)
   {
       //Clear display if page set in hide
       if (Active!=State & State==false)
          Connector.ClearDisplay();
       //
       Active=State;
   }
   
   
   public void PowerOn()
   {
       Enabled=true;
       if (Active)
        Connector.SetPower(Enabled);
               
   }
   public void PowerOff()
   {
       Enabled=false;
       if (Active)
        Connector.SetPower(Enabled);
   }
   public void SendText(String Text)
   {
       if (Active)
        Connector.DisplayText(Text);
   }
   public void UpdateText(String Text, int Col, int Row)
   {
       if (Active)
        Connector.DisplayTextUpdate(Text, Col, Row);
   }
   public void UpdateFrameVariables(String[] Keys, String[] Values)
   {
       if (UIFrames==null)
       {
            Logger.getLogger("lcddisplay").log(Level.WARNING, "[KKCar][PLUGIN][LCDDisplay][DisplayView]Not UIFrames [" + DisplayID +"]");
            return;
       }
       
       if (Keys.length!=Values.length)
           return;
       //
       for (int i=0;i<Keys.length;i++)
       {
            for (String Frame:UIFrames)
            {
                if (Frame!=null)
                    Frame=Frame.replace(Keys[i], Values[i]);
            }
       }
       //
       Connector.DisplayTextSetUIFrames(UIFrames);
   }
   public void SetUIFrames(String[] Frames)
   {
       UIFrames=Frames;
   }
}
