/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.hw.debug;


import kkdev.kksystem.base.classes.display.DisplayInfo;
import kkdev.kksystem.base.classes.display.DisplayInfo.UIDisplayType;
import kkdev.kksystem.plugin.lcddisplay.hw.IDisplayConnectorHW;


/**
 *
 * @author blinov_is
 */
public class DisplayDebug extends Thread implements IDisplayConnectorHW {
        final int LCD_ROWS = 2;
        final int LCD_ROW_1 = 0;
        final int LCD_ROW_2 = 1;
        final int LCD_COLUMNS = 16;
        final int LCD_BITS = 4;

    boolean CmdStopDisplay=false;

    @Override
    public void run() {
        
     //  
        super.run(); //To change body of generated methods, choose Tools | Templates.
    }   

    
 
    @Override
    public void SetContrast(int Contrast) {
        System.out.println("[LCDDisplay][DEBUG] Set Contrast " + Contrast);
    }

    @Override
    public void SetLight(int Light) {
        System.out.println("[LCDDisplay][DEBUG] Set Light " + Light);
    }

    @Override
    public void SetPower(boolean Power) {
         System.out.println("[LCDDisplay][DEBUG] Set Light " + Power);
    }

    @Override
    public void ShutDown() {
        CmdStopDisplay=true;
    }

    @Override
    public void DisplayText(String Text) {
       System.out.println("[LCDDisplay][DEBUG] " + Text);
    }

    @Override
    public void DisplayTextUpdate(String Text, int Column, int Line) {
         System.out.println("[LCDDisplay][DEBUG] ["+Column+"][" +Line +"]" + Text);
    }

    @Override
    public DisplayInfo GetDisplayInfo() {
        return GetMyInfo();
    }

    private DisplayInfo GetMyInfo() {
        DisplayInfo Ret = new DisplayInfo();
        Ret.DisplayType = UIDisplayType.DISPLAY_TEXT;
        Ret.MaxBackLight = 255;
        Ret.MaxContrast = 255;
        Ret.Text_Width_chars = LCD_COLUMNS;
        Ret.Text_Height_chars = LCD_ROWS;
        Ret.Graphic_Height_px = 0;
        Ret.Graphic_Width_px = 0;

        return Ret;
    }

    @Override
    public void InitDisplayHW() {

        

    }

    @Override
    public void ClearDisplay() {
              System.out.println("[LCDDisplay][DEBUG] Received CLEAR command");
    }


}

