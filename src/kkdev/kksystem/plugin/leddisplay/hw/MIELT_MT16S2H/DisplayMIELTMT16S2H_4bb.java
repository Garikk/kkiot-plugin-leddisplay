/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.leddisplay.hw.MIELT_MT16S2H;

import kkdev.kksystem.base.classes.display.DisplayInfo;
import kkdev.kksystem.base.classes.display.DisplayInfo.UIDisplayType;
import kkdev.kksystem.plugin.leddisplay.IDisplayConnector;

/**
 *
 * @author blinov_is
 */
public class DisplayMIELTMT16S2H_4bb implements IDisplayConnector{

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

    }

}
