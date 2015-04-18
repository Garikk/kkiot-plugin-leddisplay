/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.hw.rpi.MIELT_MT16S2H;

import com.pi4j.component.lcd.impl.GpioLcdDisplay;
import kkdev.kksystem.base.classes.display.DisplayInfo;
import kkdev.kksystem.base.classes.display.DisplayInfo.UIDisplayType;
import kkdev.kksystem.plugin.lcddisplay.hw.IDisplayConnectorHW;
//import com.pi4j.io.gpio.GpioController;
//import com.pi4j.io.gpio.GpioFactory;
//import com.pi4j.io.gpio.RaspiPin;

/**
 *
 * @author blinov_is
 */
public class DisplayMIELTMT16S2H_4bb extends Thread implements IDisplayConnectorHW {
        final int LCD_ROWS = 2;
        final int LCD_ROW_1 = 0;
        final int LCD_ROW_2 = 1;
        final int LCD_COLUMNS = 16;
        final int LCD_BITS = 4;

    GpioLcdDisplay lcd;
    //final GpioController gpio = GpioFactory.getInstance();
    boolean CmdStopDisplay=false;

    @Override
    public void run() {
        
     //  InitDisplayHW();
     //  while (!CmdStopDisplay)
     //  {
      //     try {
       //        Thread.sleep(5000);
     //      } catch (InterruptedException ex) {
      //         Logger.getLogger(DisplayMIELTMT16S2H_4bb.class.getName()).log(Level.SEVERE, null, ex);
      //     }
      // }
       //lcd.clear();
        super.run(); //To change body of generated methods, choose Tools | Templates.
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
        CmdStopDisplay=true;
    }

    @Override
    public void DisplayText(String Text) {
        String L1="";
        String L2="";
        
        L1=Text.substring(0, LCD_COLUMNS);
        if (Text.length()>LCD_COLUMNS)
            L2=Text.substring(LCD_COLUMNS,Text.length()-LCD_COLUMNS);
        
      lcd.writeln(0, L1);
      lcd.writeln(0, L2);
    }

    @Override
    public void DisplayTextUpdate(String Text, int Column, int Line) {
        lcd.write(Line, Column, Text);
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


}

