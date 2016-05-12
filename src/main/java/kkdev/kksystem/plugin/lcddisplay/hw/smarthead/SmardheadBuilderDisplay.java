/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.hw.smarthead;

/**
 *
 * @author blinov_is
 *
 * (byte Display, byte Clear, byte Invert, byte Font, byte PosX, byte PosY, char Text[])
 * 
 */
 
public class SmardheadBuilderDisplay {
    final static String SH_Display_PFX="$E_";
    
    public static String BuildSmartheadDisplayString(int DisplayID,boolean ClearFlag,boolean InvertFlag, int FontNum, int PosX, int PosY, String Text)
    {
         String Ret;
        
        Ret=SH_Display_PFX+DisplayID+";"+ClearFlag + ";" + InvertFlag+";"+FontNum+";"+PosX+";"+PosY+";"+Text;
        
        return Ret;
    
    }
}
