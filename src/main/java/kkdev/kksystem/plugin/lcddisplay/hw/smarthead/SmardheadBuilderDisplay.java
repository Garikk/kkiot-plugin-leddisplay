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
    final static String SH_Display_Text1_PFX="$E_1";
    
    public static String BuildSmartheadDisplayString(int DisplayID,boolean RefreshFlag,boolean ClearFlag,boolean InvertFlag, int FontNum, int PosX, int PosY, String Text)
    {
         String Ret;
        byte FlClear=0;
        byte FlInver=0;
        byte FlRefr=0;
        
        if (ClearFlag)
            FlClear=1;
        
        if (InvertFlag)
            FlInver=1;
        
        if (RefreshFlag)
            FlRefr=1;
         
         
        Ret=SH_Display_Text1_PFX+";"+DisplayID+";"+FlRefr+";"+FlClear + ";" + FlInver+";"+FontNum+";"+PosX+";"+PosY+";"+Text+";";
        
        return Ret;
    
    }
}
