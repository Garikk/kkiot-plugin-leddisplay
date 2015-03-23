/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager.configuration;

import java.io.Serializable;

/**
 *
 * @author blinov_is
 */
public class DisplayHW  {
    public static enum HWHostTypes
    {
        RaspberryPI_B
    }
    public static enum HWDisplayTypes
    {
        MIELT_4bit
    }
    
    public String HWDisplayName;
    public HWHostTypes HWBoard;
    public HWDisplayTypes HWDisplay;
    public int[] HWBoardPins;
    
            
    
}
