/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager.configuration;

import kkdev.kksystem.base.classes.plugins.PluginConfiguration;
import kkdev.kksystem.plugin.lcddisplay.hw.DisplayHW;

/**
 *
 * @author blinov_is
 */
public class LcdDisplayConf  extends PluginConfiguration {
    
    
    
    public String ConfName;
    public DisplayHW[] HWDisplays;
    
    public String DefaultFeature;
    public String[] Features;
}
