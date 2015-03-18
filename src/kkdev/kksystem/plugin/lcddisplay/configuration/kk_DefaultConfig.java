/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.configuration;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileWriter;
import java.io.IOException;
import kkdev.kksystem.base.constants.SystemConsts;
import static kkdev.kksystem.plugin.lcddisplay.configuration.DisplayHW.HWDisplayTypes.MIELT_4bit;
import static kkdev.kksystem.plugin.lcddisplay.configuration.DisplayHW.HWHostTypes.RaspberryPI_B;

/**
 *
 * @author blinov_is
 *
 * Creating default config
 *
 * RaspberryPI_B and MIELT Display
 *
 */
public abstract class kk_DefaultConfig {

    public static void MakeDefaultConfig() {
        DisplayConfiguration DP;
        DP = GetDefaultconfig();
        //
        try {
            XStream xstream = new XStream(new DomDriver());
            FileWriter fw;
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + "/" + SettingsManager.DISPLAY_CONF);
            xstream.toXML(DP, fw);
            fw.close();
        } catch (IOException Ex) {
            System.out.println("[LCDPlugin][DefConfig] error on create def config");
        }
        //

    }

    private static DisplayConfiguration GetDefaultconfig() {
        final int PAGE_MAIN = 0;
        final int PAGE_DETAIL = 1;

        DisplayConfiguration DefConf = new DisplayConfiguration();

        DisplayPage[] DP = new DisplayPage[2];
        //
        DisplayHW DHW = new DisplayHW();
        DHW.HWDisplayName = "MIELT";
        DHW.HWBoard = RaspberryPI_B;
        DHW.HWDisplay = MIELT_4bit;
        //DHW.;
        int PINS[] = new int[6];
        PINS[0] = 15; //RS
        PINS[1] = 16; //Strobe
        PINS[2] = 5;  //Bit1
        PINS[3] = 6;  //Bit2
        PINS[4] = 10; //Bit3
        PINS[5] = 11; //Bit4
        
        DP[PAGE_MAIN]=new DisplayPage();
        DP[PAGE_MAIN].HaveDynamicElements = true;
        DP[PAGE_MAIN].PageName = "MAIN";
        DP[PAGE_MAIN].HWDisplays = new String[1];
        DP[PAGE_MAIN].HWDisplays[0] = DHW.HWDisplayName;

        DP[PAGE_DETAIL]=new DisplayPage();
        DP[PAGE_DETAIL].HaveDynamicElements = false;
        DP[PAGE_DETAIL].PageName = "DETAIL";
        DP[PAGE_DETAIL].HWDisplays = new String[1];
        DP[PAGE_DETAIL].HWDisplays[0] = DHW.HWDisplayName;
        //
        DefConf.Name="Default config";
        DefConf.DisplayPages = DP;
        DefConf.HWDisplays=new DisplayHW[1];
        DefConf.HWDisplays[0]=DHW;

        return DefConf;
    }
}
