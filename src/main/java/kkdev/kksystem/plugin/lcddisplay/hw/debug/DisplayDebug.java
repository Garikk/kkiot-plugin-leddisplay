/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.hw.debug;


import kkdev.kksystem.base.classes.display.DisplayInfo;
import kkdev.kksystem.base.classes.display.DisplayInfo.UIDisplayType;
import kkdev.kksystem.plugin.lcddisplay.hw.IDisplayConnectorHW;


/**
 *
 * @author blinov_is
 */
public class DisplayDebug implements IDisplayConnectorHW {
        final int LCD_ROWS = 5;
        final int LCD_ROW_1 = 0;
        final int LCD_ROW_2 = 1;
        final int LCD_COLUMNS = 16;
        final int LCD_BITS = 4;

    boolean CmdStopDisplay=false;

 
    @Override
    public void SetContrast(int Contrast) {
        System.out.println("[LCDDisplay][DEBUG] Set Contrast " + Contrast);
    }

    @Override
    public void SetLight(int Light) {
        System.out.println("[LCDDisplay][DEBUG] Set Light " + Light);
    }

    @Override
    public void SetPower(boolean Power) {
         System.out.println("[LCDDisplay][DEBUG] Set Light " + Power);
    }

    @Override
    public void ShutDown() {
        CmdStopDisplay=true;
    }

    @Override
    public void DisplayText(boolean ClearDisplay, String Text) {
       System.out.println("[LCDDisplay][DEBUG] " + Text);
    }

    @Override
    public void DisplayTextUpdate(String Text, int Column, int Line) {
         System.out.println("[LCDDisplay][DEBUG] ["+Column+"][" +Line +"]" + Text);
    }

    @Override
    public DisplayInfo GetDisplayInfo() {
        return GetMyInfo();
    }

    private String cropText(String Text, int font) {
        switch (font) {
            case 1:
                if (Text.length() > LCD_COLUMNS) {
                    Text = Text.substring(0, LCD_COLUMNS);
                }
                break;
            case 2:
                if (Text.length() > LCD_COLUMNS) {
                    Text = Text.substring(0, LCD_COLUMNS);
                }
                break;
            case 3:
                if (Text.length() > LCD_COLUMNS) {
                    Text = Text.substring(0, LCD_COLUMNS);
                }
                break;
            default:
                break;
        }
        return Text;
    }
    private DisplayInfo GetMyInfo() {
        DisplayInfo Ret = new DisplayInfo();
        Ret.displayType = UIDisplayType.DISPLAY_TEXT;
        Ret.maxBackLight = 255;
        Ret.maxContrast = 255;
        Ret.textMode_Columns = LCD_COLUMNS;
        Ret.textMode_Rows = LCD_ROWS;
        Ret.gfxMode_Height = 0;
        Ret.gfxMode_Width = 0;

        return Ret;
    }

    @Override
    public void InitDisplayHW() {

        

    }

    @Override
    public void ClearDisplay() {
              
    }

    @Override
    public void DisplayTextSetUIFrames(String[] Frames, int Offset, int Font) {
        String[] ShowFrame=Frames[Offset].split("\r\n");
        for (String L:ShowFrame)
        {
            System.out.println(cropText(L,Font));
        }
    }



}

