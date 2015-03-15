/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.hw.MIELT_MT16S2H;

import com.pi4j.component.lcd.LCDTextAlignment;
import com.pi4j.component.lcd.impl.GpioLcdDisplay;
import kkdev.kksystem.base.classes.display.DisplayInfo;
import kkdev.kksystem.base.classes.display.DisplayInfo.UIDisplayType;
import kkdev.kksystem.plugin.lcddisplay.IDisplayConnector;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author blinov_is
 */
public class DisplayMIELTMT16S2H_4bb implements IDisplayConnector {

    final int B_0 = 0;
    final int B_1_A0 = 1;
    final int B_2_RW = 2;
    final int B_3_D4 = 4;
    final int B_4_D5 = 8;
    final int B_5_D6 = 16;
    final int B_6_D7 = 32;

    final GpioController gpio = GpioFactory.getInstance();
     //  GpioPinDigitalOutput A0 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_15,   // PIN NUMBER
    //                                                         "A0 Addr",           // PIN FRIENDLY NAME (optional)
    //                                                          PinState.LOW);      // PIN STARTUP STATE (optional)

     //   GpioPinDigitalOutput RW = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_16,   // PIN NUMBER
    //                                                         "RW Selector",           // PIN FRIENDLY NAME (optional)
    //                                                         PinState.LOW);      // PIN STARTUP STATE (optional)
     //   GpioPinDigitalOutput DD4 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05,   // PIN NUMBER
    //                                                          "DD4",           // PIN FRIENDLY NAME (optional)
    //                                                          PinState.LOW);      // PIN STARTUP STATE (optional)
    //   GpioPinDigitalOutput DD5 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06,   // PIN NUMBER
    //                                                         "DD5",           // PIN FRIENDLY NAME (optional)
    //                                                          PinState.LOW);      // PIN STARTUP STATE (optional)
    //  GpioPinDigitalOutput DD6 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_10,   // PIN NUMBER
    //                                                        "DD6",           // PIN FRIENDLY NAME (optional)
    //                                                         PinState.LOW);      // PIN STARTUP STATE (optional)
    //  GpioPinDigitalOutput DD7 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_11,   // PIN NUMBER
    //                                                         "DD7",           // PIN FRIENDLY NAME (optional)
    //                                                        PinState.LOW);      // PIN STARTUP STATE (optional)
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
    public void DisplayText(String Text) {
        System.out.println(Text);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void DisplayTextUpdate(String Text, int Column, int Line) {
        System.out.println(Text);
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
        Ret.Text_Width_chars = 25;
        Ret.Text_Height_chars = 2;
        Ret.Graphic_Height_px = 0;
        Ret.Graphic_Width_px = 0;

        return Ret;
    }

    @Override
    public void InitDisplayHW() {
        final int LCD_ROWS = 2;
        final int LCD_ROW_1 = 0;
        final int LCD_ROW_2 = 1;
        final int LCD_COLUMNS = 16;
        final int LCD_BITS = 4;

        GpioLcdDisplay lcd = new GpioLcdDisplay(LCD_ROWS, // number of row supported by LCD
                LCD_COLUMNS, // number of columns supported by LCD
                RaspiPin.GPIO_15, // LCD RS pin
                RaspiPin.GPIO_16, // LCD strobe pin
                RaspiPin.GPIO_05, // LCD data bit 1
                RaspiPin.GPIO_06, // LCD data bit 2
                RaspiPin.GPIO_10, // LCD data bit 3
                RaspiPin.GPIO_11); // LCD data bit 4

        lcd.clear();
        try {
            Thread.sleep(1000);

            // write line 1 to LCD
            lcd.write(LCD_ROW_1, "The Pi4J Project");

            // write line 2 to LCD
            lcd.write(LCD_ROW_2, "----------------");

            // line data replacement
            for (int index = 0; index < 5; index++) {
                lcd.write(LCD_ROW_2, "----------------");
                Thread.sleep(500);
                lcd.write(LCD_ROW_2, "****************");
                Thread.sleep(500);
            }
            lcd.write(LCD_ROW_2, "----------------");

            // single character data replacement
            for (int index = 0; index < lcd.getColumnCount(); index++) {
                lcd.write(LCD_ROW_2, index, ">");
                if (index > 0) {
                    lcd.write(LCD_ROW_2, index - 1, "-");
                }
                Thread.sleep(300);
            }
            for (int index = lcd.getColumnCount() - 1; index >= 0; index--) {
                lcd.write(LCD_ROW_2, index, "<");
                if (index < lcd.getColumnCount() - 1) {
                    lcd.write(LCD_ROW_2, index + 1, "-");
                }
                Thread.sleep(300);
            }

            // left alignment, full line data
            lcd.write(LCD_ROW_2, "----------------");
            Thread.sleep(500);
            lcd.writeln(LCD_ROW_2, "<< LEFT");
            Thread.sleep(1000);

            // right alignment, full line data
            lcd.write(LCD_ROW_2, "----------------");
            Thread.sleep(500);
            lcd.writeln(LCD_ROW_2, "RIGHT >>", LCDTextAlignment.ALIGN_RIGHT);
            Thread.sleep(1000);

            // center alignment, full line data
            lcd.write(LCD_ROW_2, "----------------");
            Thread.sleep(500);
            lcd.writeln(LCD_ROW_2, "<< CENTER >>", LCDTextAlignment.ALIGN_CENTER);
            Thread.sleep(1000);

            // mixed alignments, partial line data
            Thread.sleep(500);
            lcd.write(LCD_ROW_2, "<L>", LCDTextAlignment.ALIGN_LEFT);
            lcd.write(LCD_ROW_2, "<R>", LCDTextAlignment.ALIGN_RIGHT);
            lcd.write(LCD_ROW_2, "CC", LCDTextAlignment.ALIGN_CENTER);
            Thread.sleep(3000);

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

            // update time
            while (true) {
            // write time to line 2 on LCD
                //  if(gpio.isHigh(myButtons)) {
                lcd.writeln(LCD_ROW_2, formatter.format(new Date()), LCDTextAlignment.ALIGN_CENTER);
                //}
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(DisplayMIELTMT16S2H_4bb.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void WriteText(String TXT) {
    }
}
