/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.leddisplay;

/**
 *
 * @author blinov_is
 */
public class KKDisplayView {
   public IDisplayConnector Connector;
   public String DipslayID;
   public KKDisplayTypeHW DisplayType;
   public boolean Enabled;
   public boolean ErrorState;
   
   public void SendText(String Text)
   {
       Connector.DisplayText(Text);
   
   }
}
