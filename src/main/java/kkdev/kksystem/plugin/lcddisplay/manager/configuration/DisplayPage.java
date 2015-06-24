/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager.configuration;

import com.sun.xml.internal.fastinfoset.util.StringArray;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kkdev.kksystem.base.constants.SystemConsts;
import kkdev.kksystem.plugin.lcddisplay.manager.LcdDisplayManager;



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
    public String[] UIFrameFiles;       //UI Frame files list
    //
    public  String[] UIFrames;
    public  String[] UIFramesKeys;
    public  String[] UIFramesData;
    
    public void InitUIFrames()
    {
        int i=0;
       UIFrames=new String[UIFrameFiles.length];
        for (String FrameFile : UIFrameFiles) {
            try {
                FileReader fr;
                fr = new FileReader(SystemConsts.KK_BASE_CONFPATH + PluginSettings.DISPLAY_CONF_FRAMES_DIR + "//" + FrameFile);
                BufferedReader br = new BufferedReader(fr);
                String line;
                String Frame="";
                while ((line = br.readLine()) != null) {
                    Frame=Frame+line+"\r\n";
                }
                Frame=Frame.substring(0, Frame.length()-2); // trim crlf
                
                UIFrames[i]=Frame;
                i++;
                br.close();
                fr.close();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(LcdDisplayManager.class.getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(LcdDisplayManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
      

    }
}

