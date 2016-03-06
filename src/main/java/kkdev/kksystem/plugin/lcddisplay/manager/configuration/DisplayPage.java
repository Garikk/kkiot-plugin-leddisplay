/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager.configuration;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import kkdev.kksystem.base.classes.display.UIFramePack;
import kkdev.kksystem.base.classes.display.UIFrameData;
import kkdev.kksystem.base.classes.display.UIFramesKeySet;



/**
 *
 * @author blinov_is
 */
public class DisplayPage {
    public String PageName;             //ID
    public boolean HaveDynamicElements; //want to exec anmimation by thread
    public boolean IsDefaultPage;
    //
    public String[] Features;
    //
    public String[] HWDisplays;         //links to HWDisplays
    //
    public UIFramePack UIFramesPack;
    //
    public  String[] UIFrames;
    public  UIFramesKeySet UIFramesValues;
    
     public void InitUIFrames() {
        int i=0;
        UIFrames = new String[UIFramesPack.Data.length];
        for (UIFrameData FrameData : UIFramesPack.Data) {
           UIFrames[i]=FrameData.FrameData;
           i++;
        }

    }
}

