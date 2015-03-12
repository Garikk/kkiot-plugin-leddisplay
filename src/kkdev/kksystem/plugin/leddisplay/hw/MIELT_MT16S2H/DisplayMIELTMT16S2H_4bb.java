/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.leddisplay.hw.MIELT_MT16S2H;

import kkdev.kksystem.base.classes.display.DisplayInfo;
import kkdev.kksystem.base.classes.display.DisplayInfo.UIDisplayType;
import kkdev.kksystem.plugin.leddisplay.IDisplayConnector;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author blinov_is
 */
public class DisplayMIELTMT16S2H_4bb implements IDisplayConnector{
   final int B_0 = 0;
   final int B_1_A0 = 1;
   final int B_2_RW = 2;
   final int B_3_D4 = 4;
   final int B_4_D5 = 8;
   final int B_5_D6 = 16;
   final int B_6_D7 = 32;

    final GpioController gpio = GpioFactory.getInstance();
       GpioPinDigitalOutput A0 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_15,   // PIN NUMBER
                                                               "A0 Addr",           // PIN FRIENDLY NAME (optional)
                                                               PinState.LOW);      // PIN STARTUP STATE (optional)

        GpioPinDigitalOutput RW = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_16,   // PIN NUMBER
                                                               "RW Selector",           // PIN FRIENDLY NAME (optional)
                                                               PinState.LOW);      // PIN STARTUP STATE (optional)

        GpioPinDigitalOutput DD4 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05,   // PIN NUMBER
                                                               "DD4",           // PIN FRIENDLY NAME (optional)
                                                               PinState.LOW);      // PIN STARTUP STATE (optional)
        GpioPinDigitalOutput DD5 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06,   // PIN NUMBER
                                                               "DD5",           // PIN FRIENDLY NAME (optional)
                                                               PinState.LOW);      // PIN STARTUP STATE (optional)
        GpioPinDigitalOutput DD6 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_10,   // PIN NUMBER
                                                               "DD6",           // PIN FRIENDLY NAME (optional)
                                                               PinState.LOW);      // PIN STARTUP STATE (optional)
        GpioPinDigitalOutput DD7 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_11,   // PIN NUMBER
                                                               "DD7",           // PIN FRIENDLY NAME (optional)
                                                               PinState.LOW);      // PIN STARTUP STATE (optional)
   
   
   
   
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
    
    private DisplayInfo GetMyInfo()
    {
        DisplayInfo Ret=new DisplayInfo();
        Ret.DisplayType=UIDisplayType.DISPLAY_TEXT;
        Ret.MaxBackLight=255;
        Ret.MaxContrast=255;
        Ret.Text_Width_chars=25;
        Ret.Text_Height_chars=2;
        Ret.Graphic_Height_px=0;
        Ret.Graphic_Width_px=0;
        
        return Ret;
    }

    @Override
    public void InitDisplayHW() {
     
        try {
            //Try init
            //Thread.sleep(20);
 
            //Set BUS
            Thread.sleep(200);
            SetBusState(B_3_D4 | B_4_D5);
            //A0.setState(PinState.LOW);
            //RW.setState(PinState.LOW);
            //DD4.setState(PinState.HIGH);
            //DD5.setState(PinState.HIGH);
            //DD6.setState(PinState.LOW);
            //DD7.setState(PinState.LOW);
            //Set BUS
             Thread.sleep(400);
            SetBusState(B_3_D4 | B_4_D5);
            //A0.setState(PinState.LOW);
            //RW.setState(PinState.LOW);
            //DD4.setState(PinState.HIGH);
            //DD5.setState(PinState.HIGH);
            //DD6.setState(PinState.LOW);
            //DD7.setState(PinState.LOW);
            //Set BUS
            Thread.sleep(400);
            SetBusState(B_3_D4 | B_4_D5);
            //A0.setState(PinState.LOW);
            //RW.setState(PinState.LOW);
            //DD4.setState(PinState.HIGH);
            //DD5.setState(PinState.HIGH);
            //DD6.setState(PinState.LOW);
            //DD7.setState(PinState.LOW);
            //Set BUS
            Thread.sleep(400);
            SetBusState(B_4_D5);
            //A0.setState(PinState.LOW);
            //RW.setState(PinState.LOW);
            //DD4.setState(PinState.LOW);
            //DD5.setState(PinState.HIGH);
            //DD6.setState(PinState.LOW);
            //DD7.setState(PinState.LOW);
            //Set Params
            //Step 1
            Thread.sleep(400);
            SetBusState(B_4_D5);
            ////A0.setState(PinState.LOW);
           // RW.setState(PinState.LOW);
            //DD4.setState(PinState.LOW);
           // DD5.setState(PinState.HIGH);
           //DD6.setState(PinState.LOW);
           // DD7.setState(PinState.LOW);
            //Step2
            SetBusState(B_6_D7);
           // A0.setState(PinState.LOW);
           // RW.setState(PinState.LOW);
           // DD4.setState(PinState.LOW);
           // DD5.setState(PinState.LOW);
            //DD6.setState(PinState.LOW);
            //DD7.setState(PinState.HIGH);
            //Step3
            SetBusState(B_0);
           // A0.setState(PinState.LOW);
           // RW.setState(PinState.LOW);
            //DD4.setState(PinState.LOW);
           // DD5.setState(PinState.LOW);
           // DD6.setState(PinState.LOW);
           // DD7.setState(PinState.LOW);
            //Step4
            SetBusState(B_6_D7);
           // A0.setState(PinState.LOW);
            //RW.setState(PinState.LOW);
           // DD4.setState(PinState.LOW);
            //DD5.setState(PinState.LOW);
           // DD6.setState(PinState.LOW);
           // DD7.setState(PinState.HIGH);
            //Step5
            SetBusState(B_0);
           // A0.setState(PinState.LOW);
           // RW.setState(PinState.LOW);
           // DD4.setState(PinState.LOW);
           // DD5.setState(PinState.LOW);
           // DD6.setState(PinState.LOW);
           // DD7.setState(PinState.LOW);
            //Step6
            SetBusState(B_3_D4);
            //A0.setState(PinState.LOW);
            //RW.setState(PinState.LOW);
            //DD4.setState(PinState.HIGH);
            //DD5.setState(PinState.LOW);
            //DD6.setState(PinState.LOW);
            //DD7.setState(PinState.LOW);
            //Step7
            SetBusState(B_0);
            //A0.setState(PinState.LOW);
            //RW.setState(PinState.LOW);
            //DD4.setState(PinState.LOW);
            //DD5.setState(PinState.LOW);
            //DD6.setState(PinState.LOW);
            //DD7.setState(PinState.LOW);
            //Step8
            SetBusState(B_4_D5 | B_5_D6);
            //A0.setState(PinState.LOW);
            //RW.setState(PinState.LOW);
            //DD4.setState(PinState.LOW);
            //DD5.setState(PinState.HIGH);
            //DD6.setState(PinState.HIGH);
            //DD7.setState(PinState.LOW);
            
            Thread.sleep(400);
            SetBusState(B_3_D4 | B_5_D6 | B_6_D7);
           // A0.setState(PinState.LOW);
           // RW.setState(PinState.LOW);
           // DD4.setState(PinState.HIGH);
           // DD5.setState(PinState.LOW);
            //DD6.setState(PinState.HIGH);
           // DD7.setState(PinState.HIGH);
            Thread.sleep(400);
            SetBusState(B_4_D5 | B_3_D4 | B_6_D7);
            Thread.sleep(400);
            SetBusState(B_1_A0 | B_4_D5 | B_3_D4 );
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(DisplayMIELTMT16S2H_4bb.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    private void SetBusState(int value)
    {
      A0.setState((value & B_1_A0)==B_1_A0);
      A0.setState((value & B_2_RW)==B_2_RW);
      A0.setState((value & B_3_D4)==B_3_D4);
      A0.setState((value & B_4_D5)==B_4_D5);
      A0.setState((value & B_5_D6)==B_5_D6);
      A0.setState((value & B_6_D7)==B_6_D7);
    }
    
    
    private void WriteText(String TXT)

    {
    }
}
