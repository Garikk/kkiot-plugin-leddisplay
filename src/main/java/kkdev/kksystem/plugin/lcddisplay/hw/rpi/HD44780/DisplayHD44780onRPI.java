/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.hw.rpi.HD44780;

import com.pi4j.component.lcd.impl.GpioLcdDisplay;
import kkdev.kksystem.base.classes.display.DisplayInfo;
import kkdev.kksystem.base.classes.display.DisplayInfo.UIDisplayType;
import kkdev.kksystem.plugin.lcddisplay.hw.IDisplayConnectorHW;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiPin;

/**
 *
 * @author blinov_is
 */
public class DisplayHD44780onRPI implements IDisplayConnectorHW {

    boolean NotWork;
    boolean NotWork2;

    final int LCD_ROWS = 2;
    final int LCD_ROW_1 = 0;
    final int LCD_ROW_2 = 1;
    final int LCD_COLUMNS = 16;
    final int LCD_BITS = 4;

    GpioLcdDisplay lcd;
    GpioController gpio;
    boolean CmdStopDisplay = false;
    String[] UIFrames;
    private static String OS = System.getProperty("os.name").toLowerCase();
    private static String ARCH = System.getProperty("os.arch").toLowerCase();

    public DisplayHD44780onRPI() {
        //
        InitDisplayHW();
        //
         if (NotWork || NotWork2) {
            return;
        }
        
        lcd.clear();

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
        CmdStopDisplay = true;
    }

    @Override
    public void DisplayText(boolean ClearDisplay,String Text) {
        if (lcd==null)
            return;
        
        String L1 = "";
        String L2 = "";

        L1 = Text.substring(0, LCD_COLUMNS);
        if (Text.length() > LCD_COLUMNS) {
            L2 = Text.substring(LCD_COLUMNS, Text.length() - LCD_COLUMNS);
        }

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
        Ret.Text_COLS = LCD_COLUMNS;
        Ret.Text_ROWS = LCD_ROWS;
        Ret.Graphic_Height_px = 0;
        Ret.Graphic_Width_px = 0;

        return Ret;
    }

    @Override
    public void InitDisplayHW() {
        NotWork = (OS.contains("win"));
        NotWork2 = (!ARCH.contains("ARM"));

        if (NotWork || NotWork2) {
            return;
        }
        
        gpio= GpioFactory.getInstance();
        lcd = new GpioLcdDisplay(LCD_ROWS, LCD_COLUMNS, RaspiPin.GPIO_15, RaspiPin.GPIO_16, RaspiPin.GPIO_05, RaspiPin.GPIO_06, RaspiPin.GPIO_10, RaspiPin.GPIO_11);

    }

    @Override
    public void ClearDisplay() {
        lcd.clear();
    }

    @Override
    public void DisplayTextSetUIFrames(String[] Frames, int Offset, int Font) {
        if (lcd==null)
            return;
        

        String[] ShowFrame = Frames[Offset].split("\r\n");
        int i = 0;
        for (String L : ShowFrame) {
            //System.out.println(L);
            if (i <= LCD_ROWS) {
                //System.out.println(L);
                lcd.writeln(i, L);
                i++;
            }
        }
    }

}
