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
    public transient String[] UIFrames;
    public transient String[] UIFramesKeys;
    public transient String[] UIFramesData;
    
    public void InitUIFrames()
    {
        StringArray FramesToLoad = new StringArray();
        for (String FrameFile : UIFrameFiles) {
            try {
                FileReader fr;
                fr = new FileReader(SystemConsts.KK_BASE_CONFPATH + PluginSettings.DISPLAY_CONF_FRAMES_DIR + "//" + FrameFile);
                BufferedReader br = new BufferedReader(fr);
                String line;

                while ((line = br.readLine()) != null) {
                    FramesToLoad.add(line);
                }
                br.close();
                fr.close();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(LcdDisplayManager.class.getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(LcdDisplayManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        UIFrames=new String[FramesToLoad.getSize()];
        for (int i=0;i<FramesToLoad.getSize();i++)
        {
            UIFrames[i]=FramesToLoad.get(i);
        }
        
        UIFrames = FramesToLoad.getArray();
    }
}

