/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.hw.i2c.oled;

import kkdev.kksystem.base.classes.display.DisplayInfo;
import kkdev.kksystem.base.classes.display.DisplayInfo.UIDisplayType;
import kkdev.kksystem.plugin.lcddisplay.hw.IDisplayConnectorHW;

/**
 *
 * @author blinov_is
 */
public class DisplayOLEDOnExConn implements IDisplayConnectorHW {

    private boolean NotWork;
    private boolean NotWork2;
    
    private static String OS = System.getProperty("os.name").toLowerCase();
    private static String ARCH = System.getProperty("os.arch").toLowerCase();

    public DisplayOLEDOnExConn() {

    

    }

    @Override
    public void SetContrast(int Contrast) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SetLight(int Light) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SetPower(boolean Power) {
        System.out.println("ME POWER " + Power);
    }

    @Override
    public void ShutDown() {
      
    }

    @Override
    public void DisplayText(boolean ClearDisplay,String Text) {
     
    }

    @Override
    public void DisplayTextUpdate(String Text, int Column, int Line) {
       
    }

    @Override
    public DisplayInfo GetDisplayInfo() {
        return GetMyInfo();
    }

    private DisplayInfo GetMyInfo() {
        DisplayInfo Ret = new DisplayInfo();
        Ret.displayType = UIDisplayType.DISPLAY_TEXT;
        Ret.maxBackLight = 255;
        Ret.maxContrast = 255;
        Ret.gfxMode_Height = 64;
        Ret.gfxMode_Width = 128;

        return Ret;
    }

    @Override
    public void InitDisplayHW() {
        NotWork = (OS.contains("win"));
        NotWork2 = (!ARCH.contains("ARM"));

        if (NotWork || NotWork2) {
            return;
        }

      
    }

    @Override
    public void ClearDisplay() {
        
    }

    @Override
    public void DisplayTextSetUIFrames(String[] Frames, int Offset, int Font) {
      
    }

}
