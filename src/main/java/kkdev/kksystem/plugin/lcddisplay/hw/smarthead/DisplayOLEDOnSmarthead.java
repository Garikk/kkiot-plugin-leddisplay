/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.hw.smarthead;

import kkdev.kksystem.base.classes.base.PinDataTaggedObj;
import kkdev.kksystem.base.classes.display.DisplayInfo;
import kkdev.kksystem.base.classes.display.DisplayInfo.UIDisplayType;
import kkdev.kksystem.plugin.lcddisplay.hw.IDisplayConnectorHW;
import kkdev.kksystem.plugin.lcddisplay.manager.IObjPinProcessing;

/**
 *
 * @author blinov_is
 */
public class DisplayOLEDOnSmarthead implements IDisplayConnectorHW {

    final String SmartHeadDisplay_PFX="SMARTHEAD";
    
    private boolean NotWork;
    private boolean NotWork2;
    
    private static String OS = System.getProperty("os.name").toLowerCase();
    private static String ARCH = System.getProperty("os.arch").toLowerCase();

    private IObjPinProcessing ConnManager;
    private int MyDisplayID;
    
    private int ROWS;
    private int COLS;
    private int ROW_Pix_Size=12;
    

    
    public DisplayOLEDOnSmarthead(IObjPinProcessing ObjPinProcessor, int SmartheadDisplayID) {
        ConnManager=ObjPinProcessor;
        MyDisplayID=SmartheadDisplayID;
        //
        ROWS=5;
        COLS=14;
    }

    @Override
    public void SetContrast(int Contrast) {
       
    }

    @Override
    public void SetLight(int Light) {
   
    }

    @Override
    public void SetPower(boolean Power) {
      
    }

    @Override
    public void ShutDown() {
      
    }

    @Override
    public void DisplayText(boolean ClearDisplay,String Text) {
      DisplayText_Internal(ClearDisplay,true,false,2,0,0,Text);
    }

    @Override
    public void DisplayTextUpdate(String Text, int Column, int Line) {
        int LineUpd=Line*ROW_Pix_Size;
         DisplayText_Internal(false,false,false,2, Column, LineUpd," ");  
         DisplayText_Internal(false,true,false,2, Column, LineUpd,Text);  
    }

    @Override
    public DisplayInfo GetDisplayInfo() {
        return GetMyInfo();
    }   

    private void DisplayText_Internal(boolean ClearFlag,boolean RefreshFlag,boolean InvertFlag,int Font, int PosX,int PosY,String Text)
    {
      SendSmartheadPin(SmardheadBuilderDisplay.BuildSmartheadDisplayString(MyDisplayID, RefreshFlag,ClearFlag,InvertFlag,Font, PosX, PosY, Text));
    }
    
    private DisplayInfo GetMyInfo() {
        DisplayInfo Ret = new DisplayInfo();
        Ret.displayType = UIDisplayType.DISPLAY_GRAPHIC;
        Ret.maxBackLight = 255;
        Ret.maxContrast = 255;
        Ret.gfxMode_Height = 64;
        Ret.gfxMode_Width = 128;

        return Ret;
    }

    @Override
    public void InitDisplayHW() {
        NotWork = (OS.contains("win"));
        NotWork2 = (!ARCH.contains("ARM"));

        if (NotWork || NotWork2) {
            return;
        }

      
    }

    @Override
    public void ClearDisplay() {
      DisplayText_Internal(true,true,false,1, 0, 0,"");  
    }

    @Override
    public void DisplayTextSetUIFrames(String[] Frames, int Offset, int Font) {
         String[] ShowFrame = Frames[Offset].split("\r\n");
        int i = 0;
        int ii = 0;
        int RowStep=1;
        boolean FirstClear=true;
        boolean NeedRefresh=false;
        for (String L : ShowFrame) {
            if (i <= ROWS) {
                NeedRefresh=(i==ROWS);
                DisplayText_Internal(FirstClear,(ShowFrame.length-1)==ii,false,Font, 0, RowStep,L);
                FirstClear=false;
                i++;
                ii++;
                RowStep+=ROW_Pix_Size;
            }
        }
    }
    
    private void SendSmartheadPin(String SmartHeadData)
    {
        PinDataTaggedObj Dat;
        //
        ConnManager.SendPIN_ObjPin(SmartHeadDisplay_PFX, SmartHeadData);
    }

}
