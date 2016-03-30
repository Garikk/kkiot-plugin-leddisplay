/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.hw;

import java.io.Serializable;

/**
 *
 * @author blinov_is
 */
public class DisplayHW  {
    public static enum HWHostTypes
    {
        RaspberryPI_B,
        I2C,
        I2C_Over_Arduino,
        DisplayDebug
    }
    public static enum HWDisplayTypes
    {
        HD44780_4bit,
        OLED_I2C_128x64,
        HostDebug
    }
    
    public String HWDisplayName;
    public HWHostTypes HWBoard;
    public HWDisplayTypes HWDisplay;
    public int[] HWBoardPins;
    
            
    
}
