/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.leddisplay.manager;


import java.util.HashMap;
import java.util.Map;
import kkdev.kksystem.plugin.leddisplay.KKDisplayTypeHW;
import kkdev.kksystem.plugin.leddisplay.KKDisplayView;
import kkdev.kksystem.plugin.leddisplay.hw.MIELT_MT16S2H.DisplayMIELTMT16S2H_4bb;

/**
 *
 * @author blinov_is
 */
public abstract class kk_defaultConfig {
    public static Map<String,KKDisplayView> GetDefConfig()
    {
       Map<String,KKDisplayView> Ret = new HashMap<>();
       
       Ret.put("MAIN", GetDefMielt());
    
       return Ret;
    }
    
    private static KKDisplayView GetDefMielt()
    {
        KKDisplayView Ret;
        Ret = new KKDisplayView();
        
        Ret.DipslayID="MAIN";
        Ret.DisplayType=KKDisplayTypeHW.DISPLAY_MIELT_MT16S2H;
        Ret.Connector=new DisplayMIELTMT16S2H_4bb();
        Ret.Enabled=true;
        Ret.ErrorState=true;
        
        return Ret;
        
        
    }
}
