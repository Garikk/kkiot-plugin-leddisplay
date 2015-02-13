/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.leddisplay;

import kkdev.kksystem.base.classes.led.DisplayInfo;

/**
 *
 * @author blinov_is
 */
public interface IDisplayConnector {
    void SetContrast(int Contrast);
    void SetLight(int Light);
    void SetPower(boolean Power);
    //
    void DisplayText(String Text);
    void DisplayTextUpdate(String Text, int Column, int Line);
    //
    DisplayInfo GetDisplayInfo();
            
}
